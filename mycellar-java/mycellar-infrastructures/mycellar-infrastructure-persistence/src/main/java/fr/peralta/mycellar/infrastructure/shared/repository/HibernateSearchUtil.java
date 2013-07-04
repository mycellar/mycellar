/*
 * Copyright 2011, MyCellar
 *
 * This file is part of MyCellar.
 *
 * MyCellar is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCellar. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Lists.*;
import static org.apache.lucene.queryParser.QueryParser.*;
import static org.apache.lucene.util.Version.*;
import static org.hibernate.search.jpa.Search.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * 
 */
@Named
@Singleton
public class HibernateSearchUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateSearchUtil.class);

    private static final String SPACES_OR_PUNCTUATION = "\\p{Punct}|\\p{Blank}";

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> clazz, SearchParameters sp, List<String> availableProperties) {
        logger.info(
                "Searching {} with terms : onAll = {}, onAny = {}, default = {} with available Properties: {}",
                new Object[] { clazz.getSimpleName(), sp.getTermsOnAll(), sp.getTermsOnAny(),
                        sp.getTermsOnDefault(), availableProperties });
        FullTextQuery ftq = getFullTextEntityManager(entityManager).createFullTextQuery(
                query(sp.getTermsOnAll(), sp.getTermsOnAny(), sp.getTermsOnDefault(),
                        availableProperties), clazz);
        if (sp.getFirstResult() >= 0) {
            ftq.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            ftq.setMaxResults(sp.getMaxResults());
        }
        return ftq.getResultList();
    }

    /**
     * Same as {@link #find(Class, SearchParameters, String[])} but will return
     * only the id
     */
    @SuppressWarnings("unchecked")
    public <T> List<Serializable> findId(Class<T> clazz, SearchParameters sp,
            List<String> availableProperties) {
        logger.info(
                "Searching ids {} with terms : onAll = {}, onAny = {}, default = {} with available properties : {}",
                new Object[] { clazz.getSimpleName(), sp.getTermsOnAll(), sp.getTermsOnAny(),
                        sp.getTermsOnDefault(), availableProperties });
        FullTextQuery ftq = getFullTextEntityManager(entityManager).createFullTextQuery(
                query(sp.getTermsOnAll(), sp.getTermsOnAny(), sp.getTermsOnDefault(),
                        availableProperties), clazz);
        ftq.setProjection("id");
        if (sp.getFirstResult() >= 0) {
            ftq.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            ftq.setMaxResults(sp.getMaxResults());
        }
        List<Serializable> ids = Lists.newArrayList();
        List<Object[]> resultList = ftq.getResultList();
        for (Object[] result : resultList) {
            ids.add((Serializable) result[0]);
        }
        return ids;
    }

    private Query query(ArrayListMultimap<String, String> termsOnAll,
            ArrayListMultimap<String, String> termsOnAny, List<String> termsOnDefault,
            List<String> availableProperties) {
        List<String> clauses = newArrayList();
        addOnAllClauses(termsOnAll, clauses, availableProperties);
        addOnAnyClauses(termsOnAny, clauses, availableProperties);
        addOnDefaultClauses(termsOnDefault, availableProperties, clauses);

        StringBuilder query = new StringBuilder();
        query.append("+(");
        for (String clause : clauses) {
            if (query.length() > 2) {
                query.append(" AND ");
            }
            query.append(clause);
        }
        query.append(")");

        try {
            return new QueryParser(LUCENE_36, availableProperties.get(0), new StopAnalyzer(
                    LUCENE_36)).parse(query.toString());
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    private void addOnAllClauses(ArrayListMultimap<String, String> termsOnAll,
            List<String> clauses, List<String> availableProperties) {
        for (Entry<String, String> term : termsOnAll.entries()) {
            addClause(term.getKey(), term.getValue(), clauses, availableProperties);
        }
    }

    private void addOnDefaultClauses(List<String> termsOnDefault, List<String> availableProperties,
            List<String> clauses) {
        for (String term : termsOnDefault) {
            addOnAnyClause(term, availableProperties, clauses, availableProperties);
        }
    }

    private void addOnAnyClauses(ArrayListMultimap<String, String> termsOnAny,
            List<String> clauses, List<String> availableProperties) {
        for (String term : termsOnAny.keySet()) {
            addOnAnyClause(term, termsOnAny.get(term), clauses, availableProperties);
        }
    }

    private void addOnAnyClause(String term, List<String> properties, List<String> clauses,
            List<String> availableProperties) {
        List<String> subClauses = newArrayList();
        for (String property : properties) {
            addClause(term, property, subClauses, availableProperties);
        }
        if (subClauses.isEmpty()) {
            return;
        }
        if (subClauses.size() > 1) {
            StringBuilder subQuery = new StringBuilder();
            subQuery.append("(");
            for (String subClause : subClauses) {
                if (subQuery.length() > 1) {
                    subQuery.append(" OR ");
                }
                subQuery.append(subClause);
            }
            subQuery.append(")");
            clauses.add(subQuery.toString());
        } else {
            clauses.add(subClauses.get(0));
        }
    }

    private void addClause(String term, String property, List<String> clauses,
            List<String> availableProperties) {
        checkArgument(availableProperties.contains(property), property + " is not indexed");
        String[] words = term.split(SPACES_OR_PUNCTUATION);
        StringBuilder subQuery = new StringBuilder();
        subQuery.append("(");
        for (String word : words) {
            if (subQuery.length() > 1) {
                subQuery.append(" AND ");
            }
            subQuery.append(property + ":" + escape(word) + "~0.4");
        }
        subQuery.append(")");
        clauses.add(subQuery.toString());
    }
}
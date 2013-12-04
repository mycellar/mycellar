/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.lowerCase;
import static org.apache.lucene.queryParser.QueryParser.escape;
import static org.apache.lucene.util.Version.LUCENE_36;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class DefaultLuceneQueryBuilder implements LuceneQueryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLuceneQueryBuilder.class);

    private static final String SPACES_OR_PUNCTUATION = "\\p{Punct}|\\p{Blank}";

    @Override
    public Query build(FullTextEntityManager fullTextEntityManager, SearchParameters searchParameters, Class<?> type) {
        List<String> clauses = getAllClauses(searchParameters, searchParameters.getTerms());

        StringBuilder query = new StringBuilder();
        query.append("+(");
        for (String clause : clauses) {
            if (query.length() > 2) {
                query.append(" AND ");
            }
            query.append(clause);
        }
        query.append(")");

        if (query.length() == 3) {
            return null;
        }
        logger.debug("Lucene query: {}", query);
        try {
            return new QueryParser(LUCENE_36, null, fullTextEntityManager.getSearchFactory().getAnalyzer(type)).parse(query.toString());
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    private List<String> getAllClauses(SearchParameters sp, List<TermSelector> terms) {
        List<String> clauses = newArrayList();
        for (TermSelector term : terms) {
            if (term.isNotEmpty()) {
                String clause = getClause(sp, term.getSelected(), term.getAttribute(), term.isOrMode());
                if (isNotBlank(clause)) {
                    clauses.add(clause);
                }
            }
        }
        return clauses;
    }

    private String getClause(SearchParameters sp, List<String> terms, SingularAttribute<?, ?> property, boolean orMode) {
        StringBuilder subQuery = new StringBuilder();
        if (terms != null) {
            subQuery.append("(");
            for (String wordWithSpacesOrPunctuation : terms) {
                if (isBlank(wordWithSpacesOrPunctuation)) {
                    continue;
                }
                List<String> wordElements = newArrayList();
                for (String str : wordWithSpacesOrPunctuation.split(SPACES_OR_PUNCTUATION)) {
                    if (isNotBlank(str)) {
                        wordElements.add(str);
                    }
                }
                if (!wordElements.isEmpty()) {
                    if (subQuery.length() > 1) {
                        subQuery.append(" ").append(orMode ? "OR" : "AND").append(" ");
                    }
                    subQuery.append(buildSubQuery(property, wordElements, sp));
                }
            }
            subQuery.append(")");
        }
        if (subQuery.length() > 2) {
            return subQuery.toString();
        }
        return null;
    }

    private String buildSubQuery(SingularAttribute<?, ?> property, List<String> terms, SearchParameters sp) {
        StringBuilder subQuery = new StringBuilder();
        if (terms.size() > 1) {
            subQuery.append("(");
        }
        for (String term : terms) {
            if (subQuery.length() > 1) {
                subQuery.append(" AND ");
            }
            if (sp.getSearchSimilarity() != null) {
                subQuery.append(property.getName() + ":" + escapeForFuzzy(lowerCase(term)) + "~" + sp.getSearchSimilarity());
            } else {
                subQuery.append(property.getName() + ":" + escape(lowerCase(term)));
            }
        }
        if (terms.size() > 1) {
            subQuery.append(")");
        }
        return subQuery.toString();
    }

    /**
     * Apply same filtering as "custom" analyzer. Lowercase is done by
     * QueryParser for fuzzy search.
     * 
     * @param word
     * @return
     */
    private String escapeForFuzzy(String word) {
        int length = word.length();
        char[] tmp = new char[length * 4];
        length = ASCIIFoldingFilter.foldToASCII(word.toCharArray(), 0, tmp, 0, length);
        return new String(tmp, 0, length);
    }

}

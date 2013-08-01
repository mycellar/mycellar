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
package fr.peralta.mycellar.domain.shared.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.lucene.queryParser.QueryParser.escape;
import static org.apache.lucene.util.Version.LUCENE_36;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;

public class DefaultLuceneQueryBuilder implements LuceneQueryBuilder {

    @Override
    public Query build(FullTextEntityManager fullTextEntityManager, SearchParameters searchParameters, List<SingularAttribute<?, ?>> availableProperties) {
        List<String> clauses = new ArrayList<>();
        addAllClauses(searchParameters, searchParameters.getTerms(), clauses, availableProperties);

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

        try {
            return new QueryParser(LUCENE_36, availableProperties.get(0).getName(), fullTextEntityManager.getSearchFactory().getAnalyzer("custom")).parse(query.toString());
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    private void addAllClauses(SearchParameters sp, List<TermSelector> terms, List<String> clauses, List<SingularAttribute<?, ?>> availableProperties) {
        for (TermSelector term : terms) {
            addClause(sp, term.getTerms(), term.getAttribute(), clauses, availableProperties);
        }
    }

    private void addClause(SearchParameters sp, List<String> words, SingularAttribute<?, ?> property, List<String> clauses, List<SingularAttribute<?, ?>> availableProperties) {
        if (property != null) {
            checkArgument(availableProperties.contains(property), property + " is not indexed");
            StringBuilder subQuery = new StringBuilder();
            subQuery.append("(");
            if (words != null) {
                for (String word : words) {
                    if (StringUtils.isNotBlank(word)) {
                        if (subQuery.length() > 1) {
                            subQuery.append(" OR ");
                        }
                        if (sp.getSearchSimilarity() != null) {
                            subQuery.append(property.getName() + ":" + escapeForFuzzy(word) + "~" + sp.getSearchSimilarity());
                        } else {
                            subQuery.append(property.getName() + ":" + escape(word));
                        }
                    }
                }
            }
            subQuery.append(")");
            if (subQuery.length() > 2) {
                clauses.add(subQuery.toString());
            }
        } else {
            addOnAnyClause(sp, words, availableProperties, clauses, availableProperties);
        }
    }

    private void addOnAnyClause(SearchParameters sp, List<String> term, List<SingularAttribute<?, ?>> properties, List<String> clauses, List<SingularAttribute<?, ?>> availableProperties) {
        List<String> subClauses = newArrayList();
        for (SingularAttribute<?, ?> property : properties) {
            addClause(sp, term, property, subClauses, availableProperties);
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

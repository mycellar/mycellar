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
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermMatchingContext;

@Named
@Singleton
public class DefaultLuceneQueryBuilder implements LuceneQueryBuilder {

    private static final String SPACES_OR_PUNCTUATION = "\\p{Punct}|\\p{Blank}";

    @Override
    public Query build(FullTextEntityManager fullTextEntityManager, SearchParameters searchParameters, Class<?> type) {
        QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();

        BooleanJunction<?> context = builder.bool();
        boolean valid = false;
        for (TermSelector term : searchParameters.getTerms()) {
            if (term.isNotEmpty()) {
                boolean hasTerms = false;
                BooleanJunction<?> termContext = builder.bool();
                for (String selected : term.getSelected()) {
                    if (isNotBlank(selected)) {
                        BooleanJunction<?> splitContext = builder.bool();
                        for (String value : selected.split(SPACES_OR_PUNCTUATION)) {
                            if (isNotBlank(value)) {
                                TermMatchingContext selectedContext;
                                if (searchParameters.getSearchSimilarity() != null) {
                                    selectedContext = builder.keyword().fuzzy().withEditDistanceUpTo(searchParameters.getSearchSimilarity()).onField(term.getAttribute().getName());
                                } else {
                                    selectedContext = builder.keyword().onField(term.getAttribute().getName());
                                }
                                Query selectedQuery = selectedContext.matching(value).createQuery();
                                if (term.isOrMode()) {
                                    splitContext.should(selectedQuery);
                                } else {
                                    splitContext.must(selectedQuery);
                                }
                                hasTerms = true;
                            }
                        }
                        if (hasTerms) {
                            if (term.isOrMode()) {
                                termContext.should(splitContext.createQuery());
                            } else {
                                termContext.must(splitContext.createQuery());
                            }
                        }
                    }
                }
                if (hasTerms) {
                    context.must(termContext.createQuery());
                    valid = true;
                }
            }
        }
        try {
            if (valid) {
                return context.createQuery();
            } else {
                return builder.all().except(builder.all().createQuery()).createQuery();
            }
        } catch (Exception e) {
            throw propagate(e);
        }
    }

}

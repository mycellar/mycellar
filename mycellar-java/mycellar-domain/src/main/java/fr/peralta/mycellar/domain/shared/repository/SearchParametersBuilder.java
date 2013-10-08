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

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * @author speralta
 */
public class SearchParametersBuilder {

    private final SearchParameters searchParameters = new SearchParameters();

    /**
     * @return the searchParameters
     */
    public SearchParameters toSearchParameters() {
        return searchParameters;
    }

    // -----------------------------------
    // SearchMode
    // -----------------------------------

    public SearchParametersBuilder searchMode(SearchMode searchMode) {
        searchParameters.setSearchMode(searchMode);
        return this;
    }

    // -----------------------------------
    // Named query support
    // -----------------------------------

    public SearchParametersBuilder namedQuery(String namedQuery) {
        searchParameters.setNamedQuery(namedQuery);
        return this;
    }

    public SearchParametersBuilder addNamedQueryParameters(String key, Object value) {
        searchParameters.getNamedQueryParameters().put(key, value);
        return this;
    }

    // -----------------------------------
    // Terms support (hibernate search)
    // -----------------------------------

    public SearchParametersBuilder term(TermSelector... termSelectors) {
        for (TermSelector termSelector : termSelectors) {
            searchParameters.getTerms().add(termSelector);
        }
        return this;
    }

    public SearchParametersBuilder term(String... terms) {
        return addTermsToTermSelector(new TermSelector(), terms);
    }

    public SearchParametersBuilder term(SingularAttribute<?, ?> attribute, String... terms) {
        return addTermsToTermSelector(new TermSelector(attribute), terms);
    }

    private SearchParametersBuilder addTermsToTermSelector(TermSelector termSelector, String... terms) {
        for (String term : terms) {
            termSelector.getSelected().add(term);
        }
        return term(termSelector);
    }

    public SearchParametersBuilder searchSimilarity(float searchSimilarity) {
        searchParameters.setSearchSimilarity(searchSimilarity);
        return this;
    }

    public SearchParametersBuilder luceneQueryBuilder(LuceneQueryBuilder luceneQueryBuilder) {
        searchParameters.setLuceneQueryBuilder(luceneQueryBuilder);
        return this;
    }

    // -----------------------------------
    // Case sensitiveness support
    // -----------------------------------

    public SearchParametersBuilder caseSensitive() {
        return caseSensitive(true);
    }

    public SearchParametersBuilder caseInsensitive() {
        return caseSensitive(false);
    }

    private SearchParametersBuilder caseSensitive(boolean caseSensitive) {
        searchParameters.setCaseSensitive(caseSensitive);
        return this;
    }

    // -----------------------------------
    // Order by support
    // -----------------------------------

    public SearchParametersBuilder orderBy(OrderBy... ordersBy) {
        for (OrderBy orderBy : ordersBy) {
            searchParameters.getOrders().add(orderBy);
        }
        return this;
    }

    public SearchParametersBuilder orderBy(OrderByDirection direction, Attribute<?, ?>... attributes) {
        return orderBy(new OrderBy(direction, attributes));
    }

    public SearchParametersBuilder orderBy(OrderByDirection direction, Class<?> from, String path) {
        return orderBy(new OrderBy(direction, from, path));
    }

    // -----------------------------------
    // Search by range support
    // -----------------------------------

    public SearchParametersBuilder range(Range<?, ?>... ranges) {
        for (Range<?, ?> range : ranges) {
            searchParameters.getRanges().add(range);
        }
        return this;
    }

    public <E, D extends Comparable<D>> SearchParametersBuilder range(D from, D to, Attribute<?, ?> attributes) {
        return range(new Range<E, D>(from, to, attributes));
    }

    public <E, D extends Comparable<D>> SearchParametersBuilder range(D from, D to, boolean includeNull, Attribute<?, ?>... attributes) {
        return range(new Range<E, D>(from, to, includeNull, attributes));
    }

    // -----------------------------------
    // Search by property selector support
    // -----------------------------------

    public SearchParametersBuilder property(PropertySelector<?, ?>... propertySelectors) {
        for (PropertySelector<?, ?> propertySelector : propertySelectors) {
            searchParameters.getProperties().add(propertySelector);
        }
        return this;
    }

    public <E> SearchParametersBuilder propertyWithValue(E value, Attribute<?, ?>... attributes) {
        PropertySelector<?, E> propertySelector = new PropertySelector<>(attributes);
        propertySelector.getSelected().add(value);
        return property(propertySelector);
    }

    public <E> SearchParametersBuilder propertyWithValue(E value, Class<?> from, String path) {
        PropertySelector<?, E> propertySelector = new PropertySelector<>(from, path);
        propertySelector.getSelected().add(value);
        return property(propertySelector);
    }

    // -----------------------------------
    // Pagination support
    // -----------------------------------

    public SearchParametersBuilder maxResults(int maxResults) {
        searchParameters.setMaxResults(maxResults);
        return this;
    }

    public SearchParametersBuilder firstResult(int firstResult) {
        searchParameters.setFirstResult(firstResult);
        return this;
    }

    // -----------------------------------------
    // Fetch associated entity using a LEFT Join
    // -----------------------------------------

    public SearchParametersBuilder fetch(Attribute<?, ?>... attributes) {
        searchParameters.getFetches().add(new Path(attributes));
        return this;
    }

    // -----------------------------------
    // Caching support
    // -----------------------------------

    public SearchParametersBuilder cacheable() {
        return cacheable(true);
    }

    public SearchParametersBuilder notCacheable() {
        return cacheable(false);
    }

    private SearchParametersBuilder cacheable(boolean cacheable) {
        searchParameters.setCacheable(cacheable);
        return this;
    }

    public SearchParametersBuilder cacheRegion(String cacheRegion) {
        searchParameters.setCacheRegion(cacheRegion);
        return this;
    }

    // -----------------------------------
    // Extra parameters
    // -----------------------------------

    public SearchParametersBuilder addExtraParameters(String key, Object value) {
        searchParameters.getExtraParameters().put(key, value);
        return this;
    }

    // -----------------------------------
    // Use and in NN Search
    // -----------------------------------

    public SearchParametersBuilder useAndInXToMany() {
        return useAndInXToMany(true);
    }

    public SearchParametersBuilder useOrInXToMany() {
        return useAndInXToMany(false);
    }

    private SearchParametersBuilder useAndInXToMany(boolean useAndInXToMany) {
        searchParameters.setUseAndInXToMany(useAndInXToMany);
        return this;
    }

    // -----------------------------------
    // Distinct
    // -----------------------------------

    public SearchParametersBuilder distinct() {
        return distinct(true);
    }

    public SearchParametersBuilder notDistinct() {
        return distinct(false);
    }

    private SearchParametersBuilder distinct(boolean distinct) {
        searchParameters.setDistinct(distinct);
        return this;
    }

}

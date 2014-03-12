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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.transform;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Function;

/**
 * The SearchParameters is used to pass search parameters to the Repository
 * layer.
 * 
 * Its usage keeps 'find' method signatures in the Repository/Service layer
 * simple.
 * 
 * A SearchParameters helps you drive your search in the following areas:
 * <ul>
 * <li>Configure the search mode (EQUALS, LIKE, ...)</li>
 * <li>Pagination: it allows you to limit your search results to a specific
 * range.</li>
 * <li>Allow you to specify ORDER BY and ASC/DESC</li>
 * <li>Enable/disable case sensitivity</li>
 * <li>Enable/disable 2d level cache</li>
 * <li>Named query: if you set a named query it will be executed. Named queries
 * can be defined in annotation or src/main/resources/META-INF/orm.xml</li>
 * <li>FullTextSearch: simply set the term property (requires Hibernate Search)</li>
 * </ul>
 * 
 * Note : All requests are limited to a maximum number of elements to prevent
 * resource exhaustion.
 * 
 * @see JpaGenericRepository
 * @see SearchMode
 * @see OrderBy
 * @see Range
 * @see NamedQueryUtil
 * @see PropertySelector
 * @see TermSelector
 */
public class SearchParameters implements Serializable {
    private static final long serialVersionUID = 201308010800L;

    private SearchMode searchMode = SearchMode.EQUALS;
    private boolean andMode = true;

    // named query related
    private String namedQuery;
    private Map<String, Object> parameters = new HashMap<>();

    private final Set<OrderBy> orders = new LinkedHashSet<>();

    // technical parameters
    private boolean caseSensitive = false;

    // pagination
    private int maxResults = -1;
    private int firstResult = 0;

    // fetches
    private final Set<Path> fetches = new HashSet<>();

    // ranges
    private final List<Range<?, ?>> ranges = new ArrayList<>();

    // property selectors
    private final List<PropertySelector<?, ?>> properties = new ArrayList<>();

    // hibernate search terms
    private final List<TermSelector> terms = new ArrayList<>();
    private Integer searchSimilarity = 2;

    // Warn: before enabling cache for queries,
    // check this: https://hibernate.atlassian.net/browse/HHH-1523
    private Boolean cacheable = false;
    private String cacheRegion;

    // extra parameters
    private final Map<String, Object> extraParameters = new HashMap<>();

    private boolean useAndInXToMany = true;

    private boolean useDistinct = false;

    // -----------------------------------
    // SearchMode
    // -----------------------------------

    /**
     * Fluently set the @{link SearchMode}. It defaults to EQUALS.
     * 
     * @see SearchMode#EQUALS
     */
    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = checkNotNull(searchMode);
    }

    /**
     * Return the @{link SearchMode}. It defaults to EQUALS.
     * 
     * @see SearchMode#EQUALS
     */
    public SearchMode getSearchMode() {
        return searchMode;
    }

    public boolean is(SearchMode searchMode) {
        return getSearchMode() == searchMode;
    }

    /**
     * Fluently set the @{link SearchMode}. It defaults to EQUALS.
     * 
     * @see SearchMode#EQUALS
     */
    public SearchParameters searchMode(SearchMode searchMode) {
        setSearchMode(searchMode);
        return this;
    }

    /**
     * Use the EQUALS @{link SearchMode}.
     * 
     * @see SearchMode#EQUALS
     */
    public SearchParameters equals() {
        return searchMode(SearchMode.EQUALS);
    }

    /**
     * Use the ANYWHERE @{link SearchMode}.
     * 
     * @see SearchMode#ANYWHERE
     */
    public SearchParameters anywhere() {
        return searchMode(SearchMode.ANYWHERE);
    }

    /**
     * Use the STARTING_LIKE @{link SearchMode}.
     * 
     * @see SearchMode#STARTING_LIKE
     */
    public SearchParameters startingLike() {
        return searchMode(SearchMode.STARTING_LIKE);
    }

    /**
     * Use the LIKE @{link SearchMode}.
     * 
     * @see SearchMode#LIKE
     */
    public SearchParameters like() {
        return searchMode(SearchMode.LIKE);
    }

    /**
     * Use the ENDING_LIKE @{link SearchMode}.
     * 
     * @see SearchMode#ENDING_LIKE
     */
    public SearchParameters endingLike() {
        return searchMode(SearchMode.ENDING_LIKE);
    }

    // -----------------------------------
    // Predicate mode
    // -----------------------------------

    public void setAndMode(boolean andMode) {
        this.andMode = andMode;
    }

    public boolean isAndMode() {
        return andMode;
    }

    /**
     * use <code>and</code> to build the final predicate
     */
    public SearchParameters andMode() {
        setAndMode(true);
        return this;
    }

    /**
     * use <code>or</code> to build the final predicate
     */
    public SearchParameters orMode() {
        setAndMode(false);
        return this;
    }

    // -----------------------------------
    // Named query support
    // -----------------------------------

    /**
     * Returns true if a named query has been set, false otherwise. When it
     * returns true, the DAO layer will call the namedQuery.
     */
    public boolean hasNamedQuery() {
        return isNotBlank(namedQuery);
    }

    /**
     * Set the named query to be used by the DAO layer. Null by default.
     */
    public void setNamedQuery(String namedQuery) {
        this.namedQuery = namedQuery;
    }

    /**
     * Return the name of the named query to be used by the DAO layer.
     */
    public String getNamedQuery() {
        return namedQuery;
    }

    /**
     * Set the parameters for the named query.
     */
    public void setNamedQueryParameters(Map<String, Object> parameters) {
        this.parameters = checkNotNull(parameters);
    }

    /**
     * Set the parameters for the named query.
     */
    public void addNamedQueryParameter(String name, Object value) {
        parameters.put(checkNotNull(name), checkNotNull(value));
    }

    /**
     * The parameters associated with the named query, if any.
     */
    public Map<String, Object> getNamedQueryParameters() {
        return parameters;
    }

    /**
     * Return the value of the given parameter name.
     */
    public Object getNamedQueryParameter(String parameterName) {
        return parameters.get(checkNotNull(parameterName));
    }

    /**
     * Fluently set the named query to be used by the DAO layer. Null by
     * default.
     */
    public SearchParameters namedQuery(String namedQuery) {
        setNamedQuery(namedQuery);
        return this;
    }

    /**
     * Fluently set the parameters for the named query.
     */
    public SearchParameters namedQueryParameters(Map<String, Object> parameters) {
        setNamedQueryParameters(parameters);
        return this;
    }

    /**
     * Fluently set the parameter for the named query.
     */
    public SearchParameters namedQueryParameter(String name, Object value) {
        addNamedQueryParameter(name, value);
        return this;
    }

    // -----------------------------------
    // Terms support (hibernate search)
    // -----------------------------------

    public void addTerm(TermSelector term) {
        terms.add(checkNotNull(term));
    }

    public List<TermSelector> getTerms() {
        return terms;
    }

    public boolean hasTerms() {
        return !terms.isEmpty();
    }

    public Integer getSearchSimilarity() {
        return searchSimilarity;
    }

    public void setSearchSimilarity(Integer searchSimilarity) {
        this.searchSimilarity = searchSimilarity;
    }

    public SearchParameters term(TermSelector... terms) {
        for (TermSelector term : checkNotNull(terms)) {
            addTerm(term);
        }
        return this;
    }

    public SearchParameters term(String... selected) {
        return term(new TermSelector().selected(selected));
    }

    public SearchParameters term(SingularAttribute<?, ?> attribute, String... selected) {
        return term(new TermSelector(attribute).selected(selected));
    }

    /**
     * Specify the similarity for the indexed properties,
     * {@link #searchSimilarity} is between 0 and 2
     */
    public SearchParameters searchSimilarity(Integer searchSimilarity) {
        setSearchSimilarity(searchSimilarity);
        return this;
    }

    // -----------------------------------
    // Case sensitiveness support
    // -----------------------------------

    /**
     * Set the case sensitiveness. Defaults to false.
     * 
     * @param caseSensitive
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isCaseInsensitive() {
        return !caseSensitive;
    }

    /**
     * Fluently set the case sensitiveness. Defaults to false.
     * 
     * @param caseSensitive
     */
    public SearchParameters caseSensitive(boolean caseSensitive) {
        setCaseSensitive(caseSensitive);
        return this;
    }

    /**
     * Fluently set the case sensitiveness to true.
     */
    public SearchParameters caseSensitive() {
        return caseSensitive(true);
    }

    /**
     * Fluently set the case sensitiveness to false.
     */
    public SearchParameters caseInsensitive() {
        return caseSensitive(false);
    }

    // -----------------------------------
    // Order by support
    // -----------------------------------

    public List<OrderBy> getOrders() {
        return new ArrayList<>(orders);
    }

    public void addOrderBy(OrderBy orderBy) {
        if (!orders.add(checkNotNull(orderBy))) {
            throw new IllegalArgumentException("Duplicate orderBy: '" + orderBy + "'.");
        }
    }

    public boolean hasOrders() {
        return !orders.isEmpty();
    }

    public SearchParameters orderBy(OrderBy... orderBys) {
        for (OrderBy orderBy : checkNotNull(orderBys)) {
            addOrderBy(orderBy);
        }
        return this;
    }

    public SearchParameters asc(Attribute<?, ?>... attributes) {
        return orderBy(new OrderBy(OrderByDirection.ASC, attributes));
    }

    public SearchParameters desc(Attribute<?, ?>... attributes) {
        return orderBy(new OrderBy(OrderByDirection.DESC, attributes));
    }

    public SearchParameters orderBy(OrderByDirection orderByDirection, Attribute<?, ?>... attributes) {
        return orderBy(new OrderBy(orderByDirection, attributes));
    }

    public SearchParameters asc(String property, Class<?> from) {
        return orderBy(new OrderBy(OrderByDirection.ASC, property, from));
    }

    public SearchParameters desc(String property, Class<?> from) {
        return orderBy(new OrderBy(OrderByDirection.DESC, property, from));
    }

    public SearchParameters orderBy(OrderByDirection orderByDirection, String property, Class<?> from) {
        return orderBy(new OrderBy(orderByDirection, property, from));
    }

    // -----------------------------------
    // Search by range support
    // -----------------------------------

    public List<Range<?, ?>> getRanges() {
        return ranges;
    }

    public void addRange(Range<?, ?> range) {
        ranges.add(checkNotNull(range));
    }

    public boolean hasRanges() {
        return !ranges.isEmpty();
    }

    public SearchParameters range(Range<?, ?>... ranges) {
        for (Range<?, ?> range : checkNotNull(ranges)) {
            addRange(range);
        }
        return this;
    }

    public <D extends Comparable<? super D>> SearchParameters range(D from, D to, Attribute<?, ?>... attributes) {
        return range(new Range<D, D>(from, to, attributes));
    }

    // -----------------------------------
    // Search by property selector support
    // -----------------------------------

    public List<PropertySelector<?, ?>> getProperties() {
        return properties;
    }

    public void addProperty(PropertySelector<?, ?> propertySelector) {
        properties.add(checkNotNull(propertySelector));
    }

    public boolean hasProperties() {
        return !properties.isEmpty();
    }

    public SearchParameters property(PropertySelector<?, ?>... propertySelectors) {
        for (PropertySelector<?, ?> propertySelector : checkNotNull(propertySelectors)) {
            addProperty(propertySelector);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <F> SearchParameters property(Attribute<?, ?> attribute, F... selected) {
        return property(new PropertySelector<>(attribute).selected(selected));
    }

    @SuppressWarnings("unchecked")
    public <F> SearchParameters property(Attribute<?, ?> attribute1, Attribute<?, ?> attribute2, F... selected) {
        return property(new PropertySelector<>(attribute1, attribute2).selected(selected));
    }

    // -----------------------------------
    // Pagination support
    // -----------------------------------

    /**
     * Set the maximum number of results to retrieve. Pass -1 for no limits.
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Set the position of the first result to retrieve.
     * 
     * @param firstResult
     *            position of the first result, numbered from 0
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public SearchParameters maxResults(int maxResults) {
        setMaxResults(maxResults);
        return this;
    }

    public SearchParameters noLimit() {
        setMaxResults(-1);
        return this;
    }

    public SearchParameters limitBroadSearch() {
        setMaxResults(500);
        return this;
    }

    public SearchParameters firstResult(int firstResult) {
        setFirstResult(firstResult);
        return this;
    }

    // -----------------------------------------
    // Fetch associated entity using a LEFT Join
    // -----------------------------------------

    /**
     * Returns the attributes (x-to-one association) which must be fetched with
     * a left join.
     */
    public List<List<Attribute<?, ?>>> getFetches() {
        return transform(new ArrayList<>(fetches), new Function<Path, List<Attribute<?, ?>>>() {
            @Override
            public List<Attribute<?, ?>> apply(Path input) {
                return input.getAttributes();
            }
        });
    }

    public boolean hasFetches() {
        return !fetches.isEmpty();
    }

    /**
     * The given attribute (x-to-one association) will be fetched with a left
     * join.
     */
    public void addFetch(Attribute<?, ?>... attributes) {
        addFetch(Arrays.asList(attributes));
    }

    public void addFetch(List<Attribute<?, ?>> attributes) {
        fetches.add(new Path(attributes));
    }

    /**
     * Fluently set the fetch attribute
     */
    public SearchParameters fetch(Attribute<?, ?>... attributes) {
        fetch(Arrays.asList(attributes));
        return this;
    }

    /**
     * Fluently set the fetch attribute
     */
    public SearchParameters fetch(List<Attribute<?, ?>> attributes) {
        addFetch(attributes);
        return this;
    }

    // -----------------------------------
    // Caching support
    // -----------------------------------

    /**
     * Default to false. Please read
     * https://hibernate.atlassian.net/browse/HHH-1523 before using cache.
     */
    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public boolean hasCacheRegion() {
        return isNotBlank(cacheRegion);
    }

    public void setCacheRegion(String cacheRegion) {
        this.cacheRegion = cacheRegion;
    }

    public String getCacheRegion() {
        return cacheRegion;
    }

    public SearchParameters cacheable(boolean cacheable) {
        setCacheable(cacheable);
        return this;
    }

    public SearchParameters enableCache() {
        setCacheable(true);
        return this;
    }

    public SearchParameters disableCache() {
        setCacheable(false);
        return this;
    }

    public SearchParameters cacheRegion(String cacheRegion) {
        setCacheRegion(checkNotNull(cacheRegion));
        return this;
    }

    // -----------------------------------
    // Extra parameters
    // -----------------------------------

    public Map<String, Object> getExtraParameters() {
        return extraParameters;
    }

    /**
     * add additionnal parameter.
     */
    public SearchParameters addExtraParameter(String key, Object o) {
        extraParameters.put(checkNotNull(key), o);
        return this;
    }

    /**
     * get additionnal parameter.
     */
    @SuppressWarnings("unchecked")
    public <T> T getExtraParameter(String key) {
        return (T) extraParameters.get(key);
    }

    // -----------------------------------
    // Use and in XToMany Search
    // -----------------------------------

    public void setUseAndInXToMany(boolean useAndInXToMany) {
        this.useAndInXToMany = useAndInXToMany;
    }

    public boolean getUseAndInXToMany() {
        return useAndInXToMany;
    }

    public SearchParameters useOrInXToMany() {
        return useAndInXToMany(false);
    }

    public SearchParameters useAndInXToMany() {
        return useAndInXToMany(true);
    }

    public SearchParameters useAndInXToMany(boolean xToManyAndMode) {
        setUseAndInXToMany(xToManyAndMode);
        return this;
    }

    // -----------------------------------
    // Distinct
    // -----------------------------------

    public void setDistinct(boolean useDistinct) {
        this.useDistinct = useDistinct;
    }

    public boolean getDistinct() {
        return useDistinct;
    }

    public SearchParameters distinct(boolean useDistinct) {
        setDistinct(useDistinct);
        return this;
    }

    public SearchParameters distinct() {
        return distinct(true);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
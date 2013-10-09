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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    private final Map<String, Object> parameters = new HashMap<>();

    private final Set<OrderBy> orders = new HashSet<>();

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
    private Float searchSimilarity = 0.5f;

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

    // -----------------------------------
    // Predicate mode
    // -----------------------------------

    public void setAndMode(boolean andMode) {
        this.andMode = andMode;
    }

    public boolean isAndMode() {
        return andMode;
    }

    // -----------------------------------
    // Named query support
    // -----------------------------------

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
     * The parameters associated with the named query, if any.
     */
    public Map<String, Object> getNamedQueryParameters() {
        return parameters;
    }

    // -----------------------------------
    // Terms support (hibernate search)
    // -----------------------------------

    public List<TermSelector> getTerms() {
        return terms;
    }

    public Float getSearchSimilarity() {
        return searchSimilarity;
    }

    public void setSearchSimilarity(Float searchSimilarity) {
        this.searchSimilarity = searchSimilarity;
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

    // -----------------------------------
    // Order by support
    // -----------------------------------

    public List<OrderBy> getOrders() {
        return new ArrayList<OrderBy>(orders);
    }

    // -----------------------------------
    // Search by range support
    // -----------------------------------

    public List<Range<?, ?>> getRanges() {
        return ranges;
    }

    // -----------------------------------
    // Search by property selector support
    // -----------------------------------

    public List<PropertySelector<?, ?>> getProperties() {
        return properties;
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

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

    // -----------------------------------------
    // Fetch associated entity using a LEFT Join
    // -----------------------------------------

    /**
     * Returns the attributes (x-to-one association) which must be fetched with
     * a left join.
     */
    public List<Path> getFetches() {
        return new ArrayList<Path>(fetches);
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

    public void setCacheRegion(String cacheRegion) {
        this.cacheRegion = cacheRegion;
    }

    public String getCacheRegion() {
        return cacheRegion;
    }

    // -----------------------------------
    // Extra parameters
    // -----------------------------------

    public Map<String, Object> getExtraParameters() {
        return extraParameters;
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

    // -----------------------------------
    // Distinct
    // -----------------------------------

    public void setDistinct(boolean useDistinct) {
        this.useDistinct = useDistinct;
    }

    public boolean getDistinct() {
        return useDistinct;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
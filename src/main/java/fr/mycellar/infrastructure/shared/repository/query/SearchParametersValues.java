/*
 * Copyright 2014, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author speralta
 */
public class SearchParametersValues<FROM> implements Serializable {

    private static final long serialVersionUID = 201403271745L;

    private Set<OrderBy<FROM, ?>> orders;

    // pagination
    private int maxResults;
    private int firstResult;

    // fetches
    private Set<Path<FROM, ?>> fetches;

    // ranges
    private List<Range<FROM, ?>> ranges;

    // property selectors
    private PropertySelectors<FROM> propertySelectors;

    // hibernate search terms
    private List<TermSelector> terms;
    private Integer searchSimilarity;

    // extra namedQueryParameters
    private Map<String, Object> extraParameters;

    private boolean useDistinct;

    public Set<OrderBy<FROM, ?>> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderBy<FROM, ?>> orders) {
        this.orders = orders;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public Set<Path<FROM, ?>> getFetches() {
        return fetches;
    }

    public void setFetches(Set<Path<FROM, ?>> fetches) {
        this.fetches = fetches;
    }

    public List<Range<FROM, ?>> getRanges() {
        return ranges;
    }

    public void setRanges(List<Range<FROM, ?>> ranges) {
        this.ranges = ranges;
    }

    public PropertySelectors<FROM> getPropertySelectors() {
        return propertySelectors;
    }

    public void setPropertySelectors(PropertySelectors<FROM> propertySelectors) {
        this.propertySelectors = propertySelectors;
    }

    public List<TermSelector> getTerms() {
        return terms;
    }

    public void setTerms(List<TermSelector> terms) {
        this.terms = terms;
    }

    public Integer getSearchSimilarity() {
        return searchSimilarity;
    }

    public void setSearchSimilarity(Integer searchSimilarity) {
        this.searchSimilarity = searchSimilarity;
    }

    public Map<String, Object> getExtraParameters() {
        return extraParameters;
    }

    public void setExtraParameters(Map<String, Object> extraParameters) {
        this.extraParameters = extraParameters;
    }

    public boolean isUseDistinct() {
        return useDistinct;
    }

    public void setUseDistinct(boolean useDistinct) {
        this.useDistinct = useDistinct;
    }

}

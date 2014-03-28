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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author speralta
 */
public class SearchParameters<FROM> implements Serializable {

    private static final long serialVersionUID = 201403271745L;

    private final Set<OrderBy<FROM, ?>> orders;

    // pagination
    private final int maxResults;
    private final int firstResult;

    // fetches
    private final Set<Path<FROM, ?>> fetches;

    // ranges
    private final List<Range<FROM, ?>> ranges;

    // property selectors
    private final PropertySelectors<FROM> propertySelectors;

    // hibernate search terms
    private final List<TermSelector<FROM>> terms;

    // extra namedQueryParameters
    private final Map<String, Object> extraParameters;

    private final boolean useDistinct;

    SearchParameters(SearchBuilder<FROM> builder) {
        extraParameters = Collections.unmodifiableMap(builder.getExtraParameters());
        fetches = Collections.unmodifiableSet(builder.getFetches());
        firstResult = builder.getFirstResult();
        maxResults = builder.getMaxResults();
        orders = Collections.unmodifiableSet(builder.getOrders());
        propertySelectors = builder.getPropertySelectors();
        ranges = Collections.unmodifiableList(builder.getRanges());
        terms = Collections.unmodifiableList(builder.getTerms());
        useDistinct = builder.isUseDistinct();
    }

    public Set<OrderBy<FROM, ?>> getOrders() {
        return orders;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public Set<Path<FROM, ?>> getFetches() {
        return fetches;
    }

    public List<Range<FROM, ?>> getRanges() {
        return ranges;
    }

    public PropertySelectors<FROM> getPropertySelectors() {
        return propertySelectors;
    }

    public List<TermSelector<FROM>> getTerms() {
        return terms;
    }

    public Map<String, Object> getExtraParameters() {
        return extraParameters;
    }

    public boolean isUseDistinct() {
        return useDistinct;
    }

}

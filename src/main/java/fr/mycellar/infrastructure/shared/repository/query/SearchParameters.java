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
package fr.mycellar.infrastructure.shared.repository.query;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.mycellar.infrastructure.shared.repository.query.builder.FetchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.FetchesBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.FullTextBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.OrderByBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.OrdersByBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.PaginationBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.RangeBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.RangesBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.property.DisjunctionPropertySelectorsBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.property.PropertySelectorBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.property.PropertySelectorsBuilder;

/**
 * @author speralta
 */
public class SearchParameters<FROM> implements Serializable {
    private static final long serialVersionUID = 201403271745L;

    private final PaginationBuilder<FROM> paginationBuilder;
    private final OrdersByBuilder<FROM> ordersByBuilder;
    private final FetchesBuilder<FROM> fetchesBuilder;
    private final FullTextBuilder<FROM> fullTextBuilder;
    private final RangesBuilder<FROM> rangesBuilder;
    private final PropertySelectorsBuilder<FROM> propertySelectorsBuilder;

    // extra parameters
    private final Map<String, Object> extraParameters = new HashMap<>();

    private boolean useDistinct = false;

    public SearchParameters() {
        this.paginationBuilder = new PaginationBuilder<>(this);
        this.ordersByBuilder = new OrdersByBuilder<>(this);
        this.fetchesBuilder = new FetchesBuilder<>(this);
        this.fullTextBuilder = new FullTextBuilder<>(this);
        this.rangesBuilder = new RangesBuilder<>(this);
        this.propertySelectorsBuilder = new PropertySelectorsBuilder<>(this);
    }

    // -----------------------------------
    // Terms support (hibernate search)
    // -----------------------------------

    public <TO> SearchParameters<FROM> fullText(SingularAttribute<? super FROM, TO> attribute, String... selected) {
        return fullTextBuilder.on(attribute).search(selected);
    }

    public SearchParameters<FROM> searchSimilarity(Integer searchSimilarity) {
        fullTextBuilder.searchSimilarity(searchSimilarity);
        return this;
    }

    // -----------------------------------
    // Order by support
    // -----------------------------------

    public <TO> OrderByBuilder<FROM, FROM, TO> orderBy(SingularAttribute<? super FROM, TO> attribute) {
        return ordersByBuilder.by(attribute);
    }

    public <TO> OrderByBuilder<FROM, FROM, TO> orderBy(PluralAttribute<? super FROM, ?, TO> attribute) {
        return ordersByBuilder.by(attribute);
    }

    public SearchParameters<FROM> orderBy(OrderByDirection direction, String path, Class<FROM> from) {
        ordersByBuilder.orderBy(direction, path, from);
        return this;
    }

    // -----------------------------------
    // Search by range support
    // -----------------------------------

    public <TO> RangeBuilder<FROM, FROM, TO> range(SingularAttribute<? super FROM, TO> attribute) {
        return rangesBuilder.on(attribute);
    }

    public <TO> RangeBuilder<FROM, FROM, TO> range(PluralAttribute<? super FROM, ?, TO> attribute) {
        return rangesBuilder.on(attribute);
    }

    public <TO extends Comparable<? super TO>> SearchParameters<FROM> rangeBetween(TO from, TO to, SingularAttribute<? super FROM, TO> attribute) {
        rangesBuilder.between(from, to, attribute);
        return this;
    }

    // -----------------------------------
    // Search by property selector support
    // -----------------------------------

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(SingularAttribute<? super FROM, TO> attribute) {
        return propertySelectorsBuilder.property(attribute);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(PluralAttribute<? super FROM, ?, TO> attribute) {
        return propertySelectorsBuilder.property(attribute);
    }

    public <TO> SearchParameters<FROM> property(PropertySelector<FROM, TO> propertySelector) {
        propertySelectorsBuilder.property(propertySelector);
        return this;
    }

    public <TO> DisjunctionPropertySelectorsBuilder<FROM, SearchParameters<FROM>> disjunction() {
        return propertySelectorsBuilder.disjunction();
    }

    // -----------------------------------
    // Pagination support
    // -----------------------------------

    public SearchParameters<FROM> paginate(int first, int count) {
        paginationBuilder.firstResult(first).maxResult(count);
        return this;
    }

    // -----------------------------------------
    // Fetch associated entity using a LEFT Join
    // -----------------------------------------

    public <TO> FetchBuilder<FROM, FROM, TO> fetch(SingularAttribute<? super FROM, TO> attribute) {
        return fetchesBuilder.fetch(attribute);
    }

    public <TO> FetchBuilder<FROM, FROM, TO> fetch(PluralAttribute<? super FROM, ?, TO> attribute) {
        return fetchesBuilder.fetch(attribute);
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
    public SearchParameters<FROM> addExtraParameter(String key, Object o) {
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
    // Distinct
    // -----------------------------------

    public SearchParameters<FROM> distinct() {
        useDistinct = true;
        return this;
    }

    public SearchParametersValues<FROM> build() {
        SearchParametersValues<FROM> searchValues = new SearchParametersValues<>();
        searchValues.setExtraParameters(Collections.unmodifiableMap(extraParameters));
        searchValues.setFetches(Collections.unmodifiableSet(fetchesBuilder.getFetches()));
        searchValues.setFirstResult(paginationBuilder.getFirstResult());
        searchValues.setMaxResults(paginationBuilder.getMaxResults());
        searchValues.setOrders(Collections.unmodifiableSet(ordersByBuilder.getOrders()));
        searchValues.setPropertySelectors(propertySelectorsBuilder.getPropertySelectors());
        searchValues.setRanges(Collections.unmodifiableList(rangesBuilder.getRanges()));
        searchValues.setSearchSimilarity(fullTextBuilder.getSearchSimilarity());
        searchValues.setTerms(Collections.unmodifiableList(fullTextBuilder.getTerms()));
        searchValues.setUseDistinct(useDistinct);
        return searchValues;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
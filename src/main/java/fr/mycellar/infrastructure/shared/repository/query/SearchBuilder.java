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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.mycellar.infrastructure.shared.repository.query.builder.DisjunctionSelectorsBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.FetchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.FetchesBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.OrderByBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.OrdersByBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.PaginationBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.RootSelectorsBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.SelectorBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.TermSelectorBuilder;
import fr.mycellar.infrastructure.shared.repository.query.selector.Range;
import fr.mycellar.infrastructure.shared.repository.query.selector.Selectors;

/**
 * @author speralta
 */
public class SearchBuilder<FROM> implements Serializable {
    private static final long serialVersionUID = 201403271745L;

    private final PaginationBuilder<FROM> paginationBuilder;
    private final OrdersByBuilder<FROM> ordersByBuilder;
    private final FetchesBuilder<FROM> fetchesBuilder;
    private final RootSelectorsBuilder<FROM> rootSelectorsBuilder;

    // extra parameters
    private final Map<String, Object> extraParameters;

    private boolean useDistinct;

    public SearchBuilder() {
        paginationBuilder = new PaginationBuilder<>(this);
        ordersByBuilder = new OrdersByBuilder<>(this);
        fetchesBuilder = new FetchesBuilder<>(this);
        rootSelectorsBuilder = new RootSelectorsBuilder<>(this);
        extraParameters = new HashMap<>();
        useDistinct = false;
    }

    public SearchBuilder(SearchParameters<FROM> searchParameters) {
        paginationBuilder = new PaginationBuilder<>(this, searchParameters.getFirstResult(), searchParameters.getMaxResults());
        ordersByBuilder = new OrdersByBuilder<>(this, searchParameters.getOrders());
        fetchesBuilder = new FetchesBuilder<>(this, searchParameters.getFetches());
        rootSelectorsBuilder = new RootSelectorsBuilder<>(this, searchParameters.getSelectors());
        extraParameters = new HashMap<>(searchParameters.getExtraParameters());
        useDistinct = searchParameters.isUseDistinct();
    }

    public SearchParameters<FROM> build() {
        return new SearchParameters<FROM>(this);
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

    public SearchBuilder<FROM> orderBy(OrderByDirection direction, String path, Class<FROM> from) {
        ordersByBuilder.orderBy(direction, path, from);
        return this;
    }

    // -------------------------------------
    // SearchParameters by selectors support
    // -------------------------------------

    public <E extends Comparable<? super E>> SearchBuilder<FROM> between(E from, E to, SingularAttribute<? super FROM, E> attribute) {
        rootSelectorsBuilder.add(new Range<FROM, E>(from, to, new Path<>(attribute)));
        return this;
    }

    public <E extends Comparable<? super E>> SearchBuilder<FROM> between(E from, E to, PluralAttribute<? super FROM, ?, E> attribute) {
        rootSelectorsBuilder.add(new Range<FROM, E>(from, to, new Path<>(attribute)));
        return this;
    }

    public TermSelectorBuilder<FROM, SearchBuilder<FROM>, RootSelectorsBuilder<FROM>> fullText(SingularAttribute<? super FROM, String> attribute) {
        return new TermSelectorBuilder<>(rootSelectorsBuilder, new Path<>(attribute));
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(Path<FROM, TO> path) {
        return rootSelectorsBuilder.on(path);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(SingularAttribute<? super FROM, TO> attribute) {
        return rootSelectorsBuilder.on(attribute);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(PluralAttribute<? super FROM, ?, TO> attribute) {
        return rootSelectorsBuilder.on(attribute);
    }

    public DisjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> disjunction() {
        return rootSelectorsBuilder.disjunction();
    }

    // -----------------------------------
    // Pagination support
    // -----------------------------------

    public SearchBuilder<FROM> paginate(int first, int count) {
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

    /**
     * add additionnal parameter.
     */
    public SearchBuilder<FROM> addExtraParameter(String key, Object o) {
        extraParameters.put(checkNotNull(key), o);
        return this;
    }

    // -----------------------------------
    // Distinct
    // -----------------------------------

    public SearchBuilder<FROM> distinct() {
        useDistinct = true;
        return this;
    }

    // BUILDING

    Map<String, Object> getExtraParameters() {
        return extraParameters;
    }

    boolean isUseDistinct() {
        return useDistinct;
    }

    int getMaxResults() {
        return paginationBuilder.getMaxResults();
    }

    int getFirstResult() {
        return paginationBuilder.getFirstResult();
    }

    Set<OrderBy<FROM, ?>> getOrders() {
        return ordersByBuilder.getOrders();
    }

    Set<Path<FROM, ?>> getFetches() {
        return fetchesBuilder.getFetches();
    }

    Selectors<FROM> getSelectors() {
        return rootSelectorsBuilder.getSelectors();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
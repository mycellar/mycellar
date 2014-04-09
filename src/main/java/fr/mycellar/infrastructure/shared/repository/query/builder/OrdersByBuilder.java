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
package fr.mycellar.infrastructure.shared.repository.query.builder;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.OrderBy;
import fr.mycellar.infrastructure.shared.repository.query.OrderByDirection;
import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;

/**
 * @author speralta
 */
public class OrdersByBuilder<FROM> extends AbstractBuilder<SearchBuilder<FROM>> {

    private final Set<OrderBy<FROM, ?>> orders;

    public OrdersByBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        orders = new LinkedHashSet<>();
    }

    public OrdersByBuilder(SearchBuilder<FROM> searchParameters, Set<OrderBy<FROM, ?>> orders) {
        super(searchParameters);
        this.orders = new LinkedHashSet<>(orders);
    }

    public <TO> OrderByBuilder<FROM, FROM, TO> by(SingularAttribute<? super FROM, TO> attribute) {
        return new OrderByBuilder<>(this, attribute);
    }

    public <TO> OrderByBuilder<FROM, FROM, TO> by(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new OrderByBuilder<>(this, attribute);
    }

    public OrdersByBuilder<FROM> orderBy(OrderByDirection direction, String path, Class<FROM> from) {
        orders.add(new OrderBy<>(direction, path, from));
        return this;
    }

    public Set<OrderBy<FROM, ?>> getOrders() {
        return Collections.unmodifiableSet(orders);
    }

    <TO> OrdersByBuilder<FROM> asc(Path<FROM, TO> path) {
        orders.add(new OrderBy<FROM, TO>(OrderByDirection.ASC, path));
        return this;
    }

    <TO> OrdersByBuilder<FROM> desc(Path<FROM, TO> path) {
        orders.add(new OrderBy<FROM, TO>(OrderByDirection.DESC, path));
        return this;
    }
}

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

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;

/**
 * @author speralta
 */
public class OrderByBuilder<F, FROM, TO> {

    private final OrdersByBuilder<F> builder;

    private final Path<F, TO> path;

    public OrderByBuilder(OrdersByBuilder<F> ordersByBuilder, SingularAttribute<? super F, TO> attribute) {
        this.builder = ordersByBuilder;
        this.path = new Path<F, TO>(attribute);
    }

    public OrderByBuilder(OrdersByBuilder<F> ordersByBuilder, PluralAttribute<? super F, ?, TO> attribute) {
        this.builder = ordersByBuilder;
        this.path = new Path<F, TO>(attribute);
    }

    private <E> OrderByBuilder(OrdersByBuilder<F> builder, OrderByBuilder<F, E, FROM> orderByBuilder, SingularAttribute<? super FROM, TO> attribute) {
        this.builder = builder;
        this.path = orderByBuilder.path.add(attribute);
    }

    private <E> OrderByBuilder(OrdersByBuilder<F> builder, OrderByBuilder<F, E, FROM> orderByBuilder, PluralAttribute<? super FROM, ?, TO> attribute) {
        this.builder = builder;
        this.path = orderByBuilder.path.add(attribute);
    }

    public SearchBuilder<F> asc() {
        return builder.asc(path).toSearchParameters();
    }

    public SearchBuilder<F> desc() {
        return builder.desc(path).toSearchParameters();
    }

    public <E> OrderByBuilder<F, TO, E> and(SingularAttribute<? super TO, E> attribute) {
        return new OrderByBuilder<F, TO, E>(builder, this, attribute);
    }

    public <E> OrderByBuilder<F, TO, E> and(PluralAttribute<? super TO, ?, E> attribute) {
        return new OrderByBuilder<F, TO, E>(builder, this, attribute);
    }

}

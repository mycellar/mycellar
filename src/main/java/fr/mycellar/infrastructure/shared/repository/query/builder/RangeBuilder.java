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
public class RangeBuilder<F, FROM, TO> {

    private final RangesBuilder<F> builder;

    private final Path<F, TO> path;

    public RangeBuilder(RangesBuilder<F> rangesBuilder, SingularAttribute<? super F, TO> attribute) {
        this.builder = rangesBuilder;
        this.path = new Path<F, TO>(attribute);
    }

    public RangeBuilder(RangesBuilder<F> rangesBuilder, PluralAttribute<? super F, ?, TO> attribute) {
        this.builder = rangesBuilder;
        this.path = new Path<F, TO>(attribute);
    }

    private <E> RangeBuilder(RangesBuilder<F> builder, RangeBuilder<F, E, FROM> orderByBuilder, SingularAttribute<? super FROM, TO> attribute) {
        this.builder = builder;
        this.path = orderByBuilder.path.add(attribute);
    }

    private <E> RangeBuilder(RangesBuilder<F> builder, RangeBuilder<F, E, FROM> orderByBuilder, PluralAttribute<? super FROM, ?, TO> attribute) {
        this.builder = builder;
        this.path = orderByBuilder.path.add(attribute);
    }

    public <E> RangeBuilder<F, TO, E> and(SingularAttribute<? super TO, E> attribute) {
        return new RangeBuilder<F, TO, E>(builder, this, attribute);
    }

    public <E> RangeBuilder<F, TO, E> and(PluralAttribute<? super TO, ?, E> attribute) {
        return new RangeBuilder<F, TO, E>(builder, this, attribute);
    }

    public <E extends Comparable<E>> SearchBuilder<F> between(E from, E to, SingularAttribute<? super TO, E> attribute) {
        return builder.between(from, to, path.add(attribute));
    }

    public <E extends Comparable<E>> SearchBuilder<F> between(E from, E to, PluralAttribute<? super TO, ?, E> attribute) {
        return builder.between(from, to, path.add(attribute));
    }

}

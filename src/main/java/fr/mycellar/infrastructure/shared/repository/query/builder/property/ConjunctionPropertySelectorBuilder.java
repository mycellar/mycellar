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
package fr.mycellar.infrastructure.shared.repository.query.builder.property;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;

/**
 * @author speralta
 */
public class ConjunctionPropertySelectorBuilder<F, FROM, TO, PARENT> {

    private final ConjunctionPropertySelectorsBuilder<F, PARENT> builder;

    private final Path<F, TO> path;

    public ConjunctionPropertySelectorBuilder(ConjunctionPropertySelectorsBuilder<F, PARENT> builder, SingularAttribute<? super F, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    public ConjunctionPropertySelectorBuilder(ConjunctionPropertySelectorsBuilder<F, PARENT> builder, PluralAttribute<? super F, ?, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    private <E> ConjunctionPropertySelectorBuilder(ConjunctionPropertySelectorsBuilder<F, PARENT> builder, ConjunctionPropertySelectorBuilder<F, E, FROM, PARENT> propertySelectorBuilder,
            SingularAttribute<? super FROM, TO> attribute) {
        this.builder = builder;
        this.path = propertySelectorBuilder.path.add(attribute);
    }

    private <E> ConjunctionPropertySelectorBuilder(ConjunctionPropertySelectorsBuilder<F, PARENT> builder, ConjunctionPropertySelectorBuilder<F, E, FROM, PARENT> propertySelectorBuilder,
            PluralAttribute<? super FROM, ?, TO> attribute) {
        this.builder = builder;
        this.path = propertySelectorBuilder.path.add(attribute);
    }

    @SuppressWarnings("unchecked")
    public ConjunctionPropertySelectorsBuilder<F, PARENT> equalsTo(TO... values) {
        return builder.add(path, values);
    }

    public <E> ConjunctionPropertySelectorBuilder<F, TO, E, PARENT> to(SingularAttribute<? super TO, E> attribute) {
        return new ConjunctionPropertySelectorBuilder<F, TO, E, PARENT>(builder, this, attribute);
    }

    public <E> ConjunctionPropertySelectorBuilder<F, TO, E, PARENT> to(PluralAttribute<? super TO, ?, E> attribute) {
        return new ConjunctionPropertySelectorBuilder<F, TO, E, PARENT>(builder, this, attribute);
    }

}

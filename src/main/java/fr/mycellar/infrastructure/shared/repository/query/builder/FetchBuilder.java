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
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public class FetchBuilder<F, FROM, TO> {

    private final FetchesBuilder<F> builder;

    private final Path<F, TO> path;

    public FetchBuilder(FetchesBuilder<F> builder, SingularAttribute<? super F, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    public FetchBuilder(FetchesBuilder<F> builder, PluralAttribute<? super F, ?, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    private <E> FetchBuilder(FetchesBuilder<F> builder, FetchBuilder<F, E, FROM> fetchBuilder, SingularAttribute<? super FROM, TO> attribute) {
        this.builder = builder;
        this.path = fetchBuilder.path.add(attribute);
    }

    private <E> FetchBuilder(FetchesBuilder<F> builder, FetchBuilder<F, E, FROM> fetchBuilder, PluralAttribute<? super FROM, ?, TO> attribute) {
        this.builder = builder;
        this.path = fetchBuilder.path.add(attribute);
    }

    public SearchParameters<F> and() {
        return builder.add(path).toSearchParameters();
    }

    public <E> FetchBuilder<F, TO, E> to(SingularAttribute<? super TO, E> attribute) {
        return new FetchBuilder<F, TO, E>(builder, this, attribute);
    }

    public <E> FetchBuilder<F, TO, E> to(PluralAttribute<? super TO, ?, E> attribute) {
        return new FetchBuilder<F, TO, E>(builder, this, attribute);
    }

}

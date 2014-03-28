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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;

/**
 * @author speralta
 */
public class FetchesBuilder<FROM> extends AbstractBuilder<FROM> {

    private final Set<Path<FROM, ?>> fetches;

    public FetchesBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        fetches = new HashSet<>();
    }

    public FetchesBuilder(SearchBuilder<FROM> searchParameters, Set<Path<FROM, ?>> fetches) {
        super(searchParameters);
        this.fetches = new HashSet<>(fetches);
    }

    public <TO> FetchBuilder<FROM, FROM, TO> fetch(SingularAttribute<? super FROM, TO> attribute) {
        return new FetchBuilder<>(this, attribute);
    }

    public <TO> FetchBuilder<FROM, FROM, TO> fetch(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new FetchBuilder<>(this, attribute);
    }

    public Set<Path<FROM, ?>> getFetches() {
        return Collections.unmodifiableSet(fetches);
    }

    <TO> FetchesBuilder<FROM> add(Path<FROM, TO> path) {
        fetches.add(path);
        return this;
    }

}

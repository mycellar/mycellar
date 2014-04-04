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
import fr.mycellar.infrastructure.shared.repository.query.SearchMode;
import fr.mycellar.infrastructure.shared.repository.query.selector.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.selector.Range;

/**
 * @author speralta
 */
public class SelectorBuilder<F, FROM, TO, B extends SelectorsBuilder<F, ?, B>> {

    private final B builder;

    private final Path<F, TO> path;

    public SelectorBuilder(B builder, Path<F, TO> path) {
        this.builder = builder;
        this.path = path;
    }

    public SelectorBuilder(B builder, SingularAttribute<? super F, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    public SelectorBuilder(B builder, PluralAttribute<? super F, ?, TO> attribute) {
        this.builder = builder;
        this.path = new Path<F, TO>(attribute);
    }

    private <E> SelectorBuilder(B builder, SelectorBuilder<F, E, FROM, B> propertySelectorBuilder, SingularAttribute<? super FROM, TO> attribute) {
        this.builder = builder;
        this.path = propertySelectorBuilder.path.add(attribute);
    }

    private <E> SelectorBuilder(B builder, SelectorBuilder<F, E, FROM, B> propertySelectorBuilder, PluralAttribute<? super FROM, ?, TO> attribute) {
        this.builder = builder;
        this.path = propertySelectorBuilder.path.add(attribute);
    }

    @SuppressWarnings("unchecked")
    public B equalsTo(TO... values) {
        return builder.add(new PropertySelector<>(path, values).searchMode(SearchMode.EQUALS));
    }

    @SuppressWarnings("unchecked")
    public B anywhere(TO... values) {
        return builder.add(new PropertySelector<>(path, values).searchMode(SearchMode.ANYWHERE));
    }

    @SuppressWarnings("unchecked")
    public B endingLike(TO... values) {
        return builder.add(new PropertySelector<>(path, values).searchMode(SearchMode.ENDING_LIKE));
    }

    @SuppressWarnings("unchecked")
    public B startingLike(TO... values) {
        return builder.add(new PropertySelector<>(path, values).searchMode(SearchMode.STARTING_LIKE));
    }

    @SuppressWarnings("unchecked")
    public B like(TO... values) {
        return builder.add(new PropertySelector<>(path, values).searchMode(SearchMode.LIKE));
    }

    public <E extends Comparable<E>> B between(E from, E to, SingularAttribute<? super TO, E> attribute) {
        return builder.add(new Range<F, E>(from, to, path.add(attribute)));
    }

    public <E extends Comparable<E>> B between(E from, E to, PluralAttribute<? super TO, ?, E> attribute) {
        return builder.add(new Range<F, E>(from, to, path.add(attribute)));
    }

    public <E> SelectorBuilder<F, TO, E, B> to(SingularAttribute<? super TO, E> attribute) {
        return new SelectorBuilder<F, TO, E, B>(builder, this, attribute);
    }

    public <E> SelectorBuilder<F, TO, E, B> to(PluralAttribute<? super TO, ?, E> attribute) {
        return new SelectorBuilder<F, TO, E, B>(builder, this, attribute);
    }

}

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
import fr.mycellar.infrastructure.shared.repository.query.selector.Selector;
import fr.mycellar.infrastructure.shared.repository.query.selector.Selectors;

/**
 * @author speralta
 */
public class RootSelectorsBuilder<FROM> extends AbstractBuilder<SearchBuilder<FROM>> implements SelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> {

    private final Selectors<FROM> selectors;

    public RootSelectorsBuilder(SearchBuilder<FROM> parent) {
        super(parent);
        selectors = new Selectors<>();
    }

    public RootSelectorsBuilder(SearchBuilder<FROM> parent, Selectors<FROM> propertySelectors) {
        super(parent);
        this.selectors = propertySelectors.copy();
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(Path<FROM, TO> path) {
        return new SelectorBuilder<>(this, path);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(SingularAttribute<? super FROM, TO> attribute) {
        return new SelectorBuilder<>(this, attribute);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, RootSelectorsBuilder<FROM>> on(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new SelectorBuilder<>(this, attribute);
    }

    public DisjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> disjunction() {
        DisjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> disjunction = new DisjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>>(this);
        selectors.add(disjunction.getSelectors().or());
        return disjunction;
    }

    public ConjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> conjunction() {
        ConjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>> conjunction = new ConjunctionSelectorsBuilder<FROM, RootSelectorsBuilder<FROM>>(this);
        selectors.add(conjunction.getSelectors().or());
        return conjunction;
    }

    public Selectors<FROM> getSelectors() {
        return selectors;
    }

    @Override
    public <TO> RootSelectorsBuilder<FROM> add(Selector<FROM, ?> selector) {
        selectors.add(selector);
        return this;
    }
}

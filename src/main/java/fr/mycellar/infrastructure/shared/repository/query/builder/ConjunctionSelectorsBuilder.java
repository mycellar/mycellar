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
import fr.mycellar.infrastructure.shared.repository.query.selector.Selector;
import fr.mycellar.infrastructure.shared.repository.query.selector.Selectors;

/**
 * @author speralta
 */
public class ConjunctionSelectorsBuilder<FROM, PARENT> extends AbstractBuilder<PARENT> {

    private final Selectors<FROM> selectors;

    public ConjunctionSelectorsBuilder(PARENT parent) {
        super(parent);
        selectors = new Selectors<>();
    }

    public ConjunctionSelectorsBuilder(PARENT parent, Selectors<FROM> propertySelectors) {
        super(parent);
        this.selectors = propertySelectors.copy();
    }

    public <TO> SelectorBuilder<FROM, PARENT, FROM, TO> on(Path<FROM, TO> path) {
        return new SelectorBuilder<>(this, path);
    }

    public <TO> SelectorBuilder<FROM, PARENT, FROM, TO> on(SingularAttribute<? super FROM, TO> attribute) {
        return new SelectorBuilder<>(this, attribute);
    }

    public <TO> SelectorBuilder<FROM, PARENT, FROM, TO> on(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new SelectorBuilder<>(this, attribute);
    }

    public ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> disjunction() {
        ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> disjunction = new ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>>(this);
        selectors.add(disjunction.getSelectors().or());
        return disjunction;
    }

    public ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> conjunction() {
        ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> conjunction = new ConjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>>(this);
        selectors.add(conjunction.getSelectors().or());
        return conjunction;
    }

    public Selectors<FROM> getSelectors() {
        return selectors;
    }

    public <TO> ConjunctionSelectorsBuilder<FROM, PARENT> add(Selector<FROM, ?> selector) {
        selectors.add(selector);
        return this;
    }
    
    public PARENT and() {
        return toParent();
    }
}

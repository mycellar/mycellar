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
public abstract class SelectorsBuilder<FROM, PARENT, CURRENT extends SelectorsBuilder<FROM, PARENT, CURRENT>> extends AbstractBuilder<PARENT> {

    protected final Selectors<FROM> selectors;

    public SelectorsBuilder(PARENT parent) {
        super(parent);
        selectors = new Selectors<>();
    }

    public SelectorsBuilder(PARENT parent, Selectors<FROM> propertySelectors) {
        super(parent);
        this.selectors = propertySelectors.copy();
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, CURRENT> on(Path<FROM, TO> path) {
        return new SelectorBuilder<>(getThis(), path);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, CURRENT> on(SingularAttribute<? super FROM, TO> attribute) {
        return new SelectorBuilder<>(getThis(), attribute);
    }

    public <TO> SelectorBuilder<FROM, FROM, TO, CURRENT> on(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new SelectorBuilder<>(getThis(), attribute);
    }

    public TermSelectorBuilder<FROM, PARENT, CURRENT> fullText(SingularAttribute<? super FROM, String> attribute) {
        return new TermSelectorBuilder<>(getThis(), new Path<>(attribute));
    }

    public Selectors<FROM> getSelectors() {
        return selectors;
    }

    public CURRENT add(Selector<FROM, ?> selector) {
        selectors.add(selector);
        return getThis();
    }

    protected abstract CURRENT getThis();

}

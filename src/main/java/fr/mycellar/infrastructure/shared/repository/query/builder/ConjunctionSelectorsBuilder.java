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

import fr.mycellar.infrastructure.shared.repository.query.selector.Selectors;

/**
 * @author speralta
 */
public class ConjunctionSelectorsBuilder<FROM, PARENT> extends SelectorsBuilder<FROM, PARENT, ConjunctionSelectorsBuilder<FROM, PARENT>> {

    public ConjunctionSelectorsBuilder(PARENT parent, Selectors<FROM> propertySelectors) {
        super(parent, propertySelectors);
    }

    public ConjunctionSelectorsBuilder(PARENT parent) {
        super(parent);
    }

    @Override
    protected ConjunctionSelectorsBuilder<FROM, PARENT> getThis() {
        return this;
    }

    public DisjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> disjunction() {
        DisjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>> disjunction = new DisjunctionSelectorsBuilder<FROM, ConjunctionSelectorsBuilder<FROM, PARENT>>(this);
        selectors.add(disjunction.getSelectors().or());
        return disjunction;
    }

    public PARENT or() {
        return toParent();
    }
}

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
package fr.mycellar.infrastructure.shared.repository.query.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author speralta
 */
public class Selectors<FROM> implements GroupSelector<FROM, Selectors<FROM>> {
    private static final long serialVersionUID = 201403271745L;

    private boolean andMode;

    private final List<Selector<FROM, ?>> selectors;

    public Selectors() {
        andMode = true;
        selectors = new ArrayList<>();
    }

    private Selectors(Selectors<FROM> toCopy) {
        andMode = toCopy.andMode;
        selectors = new ArrayList<>();
        for (Selector<FROM, ?> selector : toCopy.selectors) {
            selectors.add(selector.copy());
        }
    }

    @Override
    public Selectors<FROM> copy() {
        return new Selectors<FROM>(this);
    }

    public Selectors<FROM> or() {
        andMode = false;
        return this;
    }

    public Selectors<FROM> add(Selector<FROM, ?> selector) {
        selectors.add(selector);
        return this;
    }

    public boolean isAndMode() {
        return andMode;
    }

    public List<Selector<FROM, ?>> getSelectors() {
        return selectors;
    }

}

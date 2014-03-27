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
package fr.mycellar.infrastructure.shared.repository.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author speralta
 */
public class PropertySelectors<FROM> implements Serializable {
    private static final long serialVersionUID = 201403271745L;

    private boolean andMode = true;

    private final List<PropertySelector<FROM, ?>> properties = new ArrayList<>();

    private final List<PropertySelectors<FROM>> propertySelectors = new ArrayList<>();

    public PropertySelectors<FROM> or() {
        andMode = false;
        return this;
    }

    public PropertySelectors<FROM> add(PropertySelectors<FROM> selectors) {
        this.propertySelectors.add(selectors);
        return this;
    }

    public PropertySelectors<FROM> property(PropertySelector<FROM, ?> property) {
        this.properties.add(property);
        return this;
    }

    public boolean isAndMode() {
        return andMode;
    }

    public List<PropertySelector<FROM, ?>> getProperties() {
        return properties;
    }

    public List<PropertySelectors<FROM>> getSelectors() {
        return propertySelectors;
    }

}

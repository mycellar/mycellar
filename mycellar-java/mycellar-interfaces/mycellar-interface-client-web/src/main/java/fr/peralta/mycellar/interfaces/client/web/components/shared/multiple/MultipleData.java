/*
 * Copyright 2011, MyCellar
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
package fr.peralta.mycellar.interfaces.client.web.components.shared.multiple;

import java.io.Serializable;

/**
 * @author speralta
 */
class MultipleData<O> implements Serializable {

    private static final long serialVersionUID = 201108162925L;

    private final O object;

    private final String label;

    /**
     * @param object
     * @param count
     * @param label
     */
    public MultipleData(O object, long count, String label) {
        this.object = object;
        this.label = label + " (" + count + ")";
    }

    /**
     * @return the object
     */
    public O getObject() {
        return object;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

}

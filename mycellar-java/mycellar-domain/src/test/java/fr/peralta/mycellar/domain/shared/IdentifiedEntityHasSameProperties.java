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
package fr.peralta.mycellar.domain.shared;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import fr.peralta.mycellar.test.matchers.PropertiesMatcher;

/**
 * @author speralta
 */
public abstract class IdentifiedEntityHasSameProperties<E extends IdentifiedEntity<E>> extends
        PropertiesMatcher<E> {

    /**
     * @param object
     */
    public IdentifiedEntityHasSameProperties(E object) {
        addProperty("id", is(equalTo(object.getId())));
        addProperty("version", is(equalTo(object.getVersion())));
    }

}

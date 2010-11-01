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

import javax.persistence.MappedSuperclass;

/**
 * @author speralta
 */
@MappedSuperclass
public abstract class NamedEntity<E extends NamedEntity<E>> extends
        IdentifiedEntity<E> {

    private static final long serialVersionUID = 201010311742L;

    private String name;

    /**
     * Default Constructor.
     */
    protected NamedEntity() {
    }

    /**
     * @param name
     */
    protected NamedEntity(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + "[name=" + name + "]";
    }

}

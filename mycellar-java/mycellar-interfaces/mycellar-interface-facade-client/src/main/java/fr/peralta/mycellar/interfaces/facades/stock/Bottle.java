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
package fr.peralta.mycellar.interfaces.facades.stock;

import java.io.Serializable;

import fr.peralta.mycellar.interfaces.facades.wine.Format;
import fr.peralta.mycellar.interfaces.facades.wine.Wine;

/**
 * @author speralta
 */
public class Bottle implements Serializable {

    private static final long serialVersionUID = 201010311742L;

    private Wine wine;
    private Format format;
    private Integer id;

    /**
     * Initialise par defaut les objets Wine et Format.
     */
    public Bottle() {
        wine = new Wine();
        format = new Format();
    }

    /**
     * @return the wine
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * @param wine
     *            the wine to set
     */
    public void setWine(Wine wine) {
        this.wine = wine;
    }

    /**
     * @return the format
     */
    public Format getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}

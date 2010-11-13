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

import org.joda.time.LocalDate;

/**
 * @author bperalta
 * 
 */
public class Input implements Serializable {
    private static final long serialVersionUID = 201011130032L;

    private LocalDate arrival;
    private Cellar cellar;
    private Bottle bottle;
    private int number;
    private float price;
    private String source;
    private float charges;
    private Integer id;

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

    /**
     * @return the arrival
     */
    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * @return the cellar
     */
    public Cellar getCellar() {
        return cellar;
    }

    /**
     * @return the bottle
     */
    public Bottle getBottle() {
        return bottle;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the charges
     */
    public float getCharges() {
        return charges;
    }

}

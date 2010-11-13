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
public class Output implements Serializable {
    private static final long serialVersionUID = 201011111800L;

    private LocalDate output;
    private Cellar cellar;
    private Bottle bottle;
    private int number;
    private float price;
    private String destination;
    private Integer id;

    /**
     * @return the output
     */
    public LocalDate getOutput() {
        return output;
    }

    /**
     * @param output
     *            the output to set
     */
    public void setOutput(LocalDate output) {
        this.output = output;
    }

    /**
     * @return the cellar
     */
    public Cellar getCellar() {
        return cellar;
    }

    /**
     * @param cellar
     *            the cellar to set
     */
    public void setCellar(Cellar cellar) {
        this.cellar = cellar;
    }

    /**
     * @return the bottle
     */
    public Bottle getBottle() {
        return bottle;
    }

    /**
     * @param bottle
     *            the bottle to set
     */
    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
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

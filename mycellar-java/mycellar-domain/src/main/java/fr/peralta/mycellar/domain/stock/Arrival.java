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
package fr.peralta.mycellar.domain.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * @author speralta
 */

public class Arrival implements Serializable {

    private static final long serialVersionUID = 201011071626L;

    private List<ArrivalBottle> arrivalBottles = new ArrayList<ArrivalBottle>();

    private LocalDate date;

    private float otherCharges;

    private String source;

    public void addArrivalBottle(Bottle bottle, Integer quantity, float price) {
        ArrivalBottle arrivalBottle = new ArrivalBottle();
        arrivalBottle.setBottle(bottle);
        arrivalBottle.setPrice(price);
        arrivalBottle.setQuantity(quantity);
        arrivalBottles.add(arrivalBottle);
    }

    /**
     * @return the arrivalBottles
     */
    public List<ArrivalBottle> getArrivalBottles() {
        return arrivalBottles;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the otherCharges
     */
    public float getOtherCharges() {
        return otherCharges;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @param otherCharges
     *            the otherCharges to set
     */
    public void setOtherCharges(float otherCharges) {
        this.otherCharges = otherCharges;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
}

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
public class Drink implements Serializable {

    private static final long serialVersionUID = 201109282233L;

    private final List<DrinkBottle> drinkBottles = new ArrayList<DrinkBottle>();

    private LocalDate date;

    private String drinkWith;

    public void addDrinkBottle(Bottle bottle, Integer quantity) {
        DrinkBottle drinkBottle = new DrinkBottle();
        drinkBottle.setBottle(bottle);
        drinkBottle.setQuantity(quantity);
        drinkBottles.add(drinkBottle);
    }

    /**
     * @return the drinkWith
     */
    public String getDrinkWith() {
        return drinkWith;
    }

    /**
     * @param drinkWith
     *            the drinkWith to set
     */
    public void setDrinkWith(String drinkWith) {
        this.drinkWith = drinkWith;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the drinkBottles
     */
    public List<DrinkBottle> getDrinkBottles() {
        return drinkBottles;
    }

}

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
package fr.mycellar.application.stock;

import org.joda.time.LocalDate;

import fr.mycellar.application.shared.SimpleService;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.Stock;

/**
 * @author speralta
 */
public interface StockService extends SimpleService<Stock> {

    /**
     * @param cellar
     * @param bottle
     * @param quantity
     * @param date
     * @param charges
     * @param price
     * @param source
     */
    void addToStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, float charges, float price, String source);

    /**
     * @param cellar
     * @param bottle
     * @param quantity
     * @param date
     * @param destination
     * @param price
     */
    void removeFromStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, String destination, float price);

    /**
     * @param bottle
     * @param cellar
     * @return
     */
    Stock findStock(Bottle bottle, Cellar cellar);

    /**
     * @param drink
     */
    void drink(Drink drink);

    /**
     * @param arrival
     */
    void stock(Arrival arrival);

}

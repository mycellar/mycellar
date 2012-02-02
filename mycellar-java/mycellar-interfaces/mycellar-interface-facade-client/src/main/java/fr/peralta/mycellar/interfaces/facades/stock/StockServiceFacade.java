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

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Arrival;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.Drink;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.StockOrder;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
public interface StockServiceFacade {

    /**
     * @param arrival
     */
    void arrival(Arrival arrival);

    /**
     * @param drink
     */
    void drink(Drink drink);

    /**
     * @param cellarShare
     */
    void saveCellarShare(CellarShare cellarShare);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<Cellar, Long> getCellars(SearchForm searchForm, CountEnum count);

    /**
     * @param wine
     * @param format
     * @return
     */
    Bottle findBottle(Wine wine, Format format);

    /**
     * @param searchForm
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Movement<?>> getMovements(SearchForm searchForm, MovementOrder orders, int first, int count);

    /**
     * @param searchForm
     * @return
     */
    long countMovements(SearchForm searchForm);

    /**
     * @param searchForm
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Stock> getStocks(SearchForm searchForm, StockOrder orders, int first, int count);

    /**
     * @param searchForm
     * @return
     */
    long countStocks(SearchForm searchForm);

    /**
     * @param searchForm
     * @param orders
     * @param count
     * @param first
     * @return
     */
    List<CellarShare> getCellarShares(SearchForm searchForm, CellarShareOrder orders, int first,
            int count);

    /**
     * @param searchForm
     * @return
     */
    long countCellarShares(SearchForm searchForm);

    /**
     * @param cellarShare
     */
    void deleteCellarShare(CellarShare cellarShare);

}

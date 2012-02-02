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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.stock.BottleService;
import fr.peralta.mycellar.application.stock.CellarService;
import fr.peralta.mycellar.application.stock.StockService;
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
@Service
public class StockServiceFacadeImpl implements StockServiceFacade {

    private StockService stockService;

    private CellarService cellarService;

    private BottleService bottleService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void arrival(Arrival arrival) {
        stockService.stock(arrival);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void drink(Drink drink) {
        stockService.drink(drink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveCellarShare(CellarShare cellarShare) {
        cellarService.saveCellarShare(cellarShare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteCellarShare(CellarShare cellarShare) {
        cellarService.deleteCellarShare(cellarShare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Cellar, Long> getCellars(SearchForm searchForm, CountEnum count) {
        return cellarService.getAll(searchForm, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CellarShare> getCellarShares(SearchForm searchForm, CellarShareOrder orders,
            int first, int count) {
        return cellarService.getShares(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countCellarShares(SearchForm searchForm) {
        return cellarService.countShares(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement<?>> getMovements(SearchForm searchForm, MovementOrder orders, int first,
            int count) {
        return stockService.getMovements(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countMovements(SearchForm searchForm) {
        return stockService.countMovements(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(SearchForm searchForm, StockOrder orders, int first, int count) {
        return stockService.getStocks(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countStocks(SearchForm searchForm) {
        return stockService.countStocks(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Bottle findBottle(Wine wine, Format format) {
        return bottleService.findBottle(wine, format);
    }

    /**
     * @param stockService
     *            the stockService to set
     */
    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * @param cellarService
     *            the cellarService to set
     */
    @Autowired
    public void setCellarService(CellarService cellarService) {
        this.cellarService = cellarService;
    }

    /**
     * @param bottleService
     *            the bottleService to set
     */
    @Autowired
    public void setBottleService(BottleService bottleService) {
        this.bottleService = bottleService;
    }

}

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
package fr.peralta.mycellar.application.stock.impl;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.stock.StockService;
import fr.peralta.mycellar.domain.stock.Arrival;
import fr.peralta.mycellar.domain.stock.ArrivalBottle;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementSearchForm;
import fr.peralta.mycellar.domain.stock.repository.StockRepository;

/**
 * @author speralta
 */
@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void stock(Arrival arrival) {
        Cellar cellar = arrival.getCellar();
        float unitCharges = arrival.getOtherCharges() / arrival.getArrivalBottles().size();
        for (ArrivalBottle arrivalBottle : arrival.getArrivalBottles()) {
            Bottle bottle = arrivalBottle.getBottle();
            addToStock(cellar, bottle, arrivalBottle.getQuantity(), arrival.getDate(), unitCharges,
                    arrivalBottle.getPrice(), arrival.getSource());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date,
            float charges, float price, String source) {
        Stock stock = updateStock(cellar, bottle, quantity);
        // Use cellar and bottle from stock, they could have been merged.
        createInput(stock.getCellar(), stock.getBottle(), quantity, date, charges, price, source);
    }

    /**
     * @param cellar
     * @param bottle
     * @param quantity
     * @param date
     * @param charges
     * @param price
     * @param source
     */
    private void createInput(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date,
            float charges, float price, String source) {
        Input input = new Input();
        input.setDate(date);
        input.setBottle(bottle);
        input.setCellar(cellar);
        input.setCharges(charges);
        input.setNumber(quantity);
        input.setPrice(price);
        input.setSource(source);
        stockRepository.save(input);
    }

    /**
     * @param cellar
     * @param bottle
     * @param quantity
     * @return
     */
    private Stock updateStock(Cellar cellar, Bottle bottle, Integer quantity) {
        Stock stock = findStock(bottle, cellar);
        if (stock == null) {
            stock = new Stock();
            stock.setBottle(bottle);
            stock.setCellar(cellar);
            stock.setQuantity(0);
        }
        stock.setQuantity(stock.getQuantity() + quantity);
        return stockRepository.save(stock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock findStock(Bottle bottle, Cellar cellar) {
        return stockRepository.findStock(bottle, cellar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countMovements(MovementSearchForm searchForm) {
        return stockRepository.countMovements(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movement<?>> getMovements(MovementSearchForm searchForm, MovementOrder orders,
            int first, int count) {
        return stockRepository.getMovements(searchForm, orders, first, count);
    }

    /**
     * @param stockRepository
     *            the stockRepository to set
     */
    @Autowired
    @Qualifier("hibernate")
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

}

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
package fr.mycellar.application.stock.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.joda.time.LocalDate;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.MovementService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.ArrivalBottle;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.DrinkBottle;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.stock.repository.StockRepository;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class StockServiceImpl extends AbstractSimpleService<Stock, StockRepository> implements StockService {

    private StockRepository stockRepository;

    private MovementService movementService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void drink(Drink drink) {
        for (DrinkBottle drinkBottle : drink.getDrinkBottles()) {
            removeFromStock(drinkBottle.getCellar(), drinkBottle.getBottle(), drinkBottle.getQuantity(), drink.getDate(), drink.getDrinkWith(), 0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stock(Arrival arrival) {
        Cellar cellar = arrival.getCellar();
        float unitCharges = arrival.getOtherCharges() / arrival.getArrivalBottles().size();
        for (ArrivalBottle arrivalBottle : arrival.getArrivalBottles()) {
            Bottle bottle = arrivalBottle.getBottle();
            addToStock(cellar, bottle, arrivalBottle.getQuantity(), arrival.getDate(), unitCharges, arrivalBottle.getPrice(), arrival.getSource());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, float charges, float price, String source) {
        Stock stock = updateStock(cellar, bottle, quantity);
        // Use cellar and bottle from stock, they could have been merged.
        movementService.createInput(stock.getCellar(), stock.getBottle(), quantity, date, charges, price, source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFromStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, String destination, float price) {
        Stock stock = updateStock(cellar, bottle, -quantity);
        // Use cellar and bottle from stock, they could have been merged.
        movementService.createOutput(stock.getCellar(), stock.getBottle(), quantity, date, destination, price);
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
        return stockRepository.findUniqueOrNone(new SearchParameters() //
                .property(Stock_.bottle, bottle) //
                .property(Stock_.cellar, cellar));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Stock entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StockRepository getRepository() {
        return stockRepository;
    }

    /**
     * @param stockRepository
     *            the stockRepository to set
     */
    @Inject
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * @param movementService
     *            the movementService to set
     */
    @Inject
    public void setMovementService(MovementService movementService) {
        this.movementService = movementService;
    }

}

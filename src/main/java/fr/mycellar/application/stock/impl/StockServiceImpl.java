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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.SearchBuilder;

import org.joda.time.LocalDate;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.MovementService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.ArrivalBottle;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.DrinkBottle;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.infrastructure.stock.repository.StockRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class StockServiceImpl extends AbstractSimpleService<Stock, StockRepository> implements StockService {

    private StockRepository stockRepository;

    private MovementService movementService;

    @Override
    public void drink(Drink drink) throws BusinessException {
        for (DrinkBottle drinkBottle : drink.getDrinkBottles()) {
            removeFromStock(drinkBottle.getCellar(), drinkBottle.getBottle(), drinkBottle.getQuantity(), drink.getDate(), drink.getDrinkWith(), 0);
        }
    }

    @Override
    public void stock(Arrival arrival) throws BusinessException {
        Cellar cellar = arrival.getCellar();
        float unitCharges = arrival.getCharges() / arrival.getArrivalBottles().size();
        for (ArrivalBottle arrivalBottle : arrival.getArrivalBottles()) {
            Bottle bottle = arrivalBottle.getBottle();
            addToStock(cellar, bottle, arrivalBottle.getQuantity(), arrival.getDate(), unitCharges, arrivalBottle.getPrice(), arrival.getSource());
        }
    }

    @Override
    public Stock addToStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, float charges, float price, String source) throws BusinessException {
        Stock stock = updateStock(cellar, bottle, quantity);
        // Use cellar and bottle from stock, they could have been merged.
        movementService.createInput(stock.getCellar(), stock.getBottle(), quantity, date, charges, price, source);
        return stock;
    }

    @Override
    public Stock removeFromStock(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, String destination, float price) throws BusinessException {
        Stock stock = updateStock(cellar, bottle, -quantity);
        // Use cellar and bottle from stock, they could have been merged.
        movementService.createOutput(stock.getCellar(), stock.getBottle(), quantity, date, destination, price);
        return stock;
    }

    @Override
    public Stock updateStock(Cellar cellar, Bottle bottle, Integer quantity) throws BusinessException {
        Stock stock = findStock(bottle, cellar);
        if (stock == null) {
            stock = new Stock();
            stock.setBottle(bottle);
            stock.setCellar(cellar);
            stock.setQuantity(0);
        }
        stock.setQuantity(stock.getQuantity() + quantity);
        validate(stock);
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> getAllForCellar(Cellar cellar) {
        return find(new SearchBuilder<Stock>().on(Stock_.cellar).equalsTo(cellar).build());
    }

    @Override
    public Stock findStock(Bottle bottle, Cellar cellar) {
        return stockRepository.findUniqueOrNone(new SearchBuilder<Stock>() //
                .on(Stock_.bottle).equalsTo(bottle) //
                .on(Stock_.cellar).equalsTo(cellar).build());
    }

    @Override
    public void validate(Stock entity) throws BusinessException {
        Stock existing = findStock(entity.getBottle(), entity.getCellar());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.STOCK_00001);
        }
        if (entity.getQuantity() < 0) {
            throw new BusinessException(BusinessError.STOCK_00002);
        }
    }

    @Override
    protected void deleteInternal(Stock entity) throws BusinessException {
        if (entity.getQuantity() > 0) {
            Stock removed = removeFromStock(entity.getCellar(), entity.getBottle(), entity.getQuantity(), new LocalDate(), "Régularisation", 0);
            super.deleteInternal(removed);
        }
        super.deleteInternal(entity);
    }

    @Override
    protected Stock saveInternal(Stock entity) throws BusinessException {
        Stock existing = findStock(entity.getBottle(), entity.getCellar());
        if (existing != null) {
            if (existing.getQuantity() > entity.getQuantity()) {
                return removeFromStock(entity.getCellar(), entity.getBottle(), existing.getQuantity() - entity.getQuantity(), new LocalDate(), "Régularisation", 0);
            } else if (existing.getQuantity() < entity.getQuantity()) {
                return addToStock(entity.getCellar(), entity.getBottle(), entity.getQuantity() - existing.getQuantity(), new LocalDate(), 0, 0, "Régularisation");
            }
        } else if (entity.getQuantity() >= 0) {
            return addToStock(entity.getCellar(), entity.getBottle(), entity.getQuantity(), new LocalDate(), 0, 0, "Régularisation");
        }
        return super.saveInternal(entity);
    }

    @Override
    protected StockRepository getRepository() {
        return stockRepository;
    }

    @Inject
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Inject
    public void setMovementService(MovementService movementService) {
        this.movementService = movementService;
    }

}

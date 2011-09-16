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
package fr.peralta.mycellar.infrastructure.stock.persistence;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.StockRepository;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Repository
@Qualifier("mock")
public class MockStockRepository implements StockRepository {

    private static final Logger logger = LoggerFactory.getLogger(MockStockRepository.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Input save(Input input) {
        logger.info("Stocks " + input.getNumber() + " " + input.getBottle());
        return input;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock save(Stock stock) {
        logger.info("Stocks " + stock.getQuantity() + " " + stock.getBottle() + " in "
                + stock.getCellar());
        return stock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Cellar, Long> getAllCellarsWithCountsFromUser(User user) {
        return new HashMap<Cellar, Long>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cellar save(Cellar cellar) {
        logger.info("New cellar " + cellar.getName() + " for " + cellar.getOwner().getEmail() + ".");
        return cellar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock findStock(Bottle bottle, Cellar cellar) {
        return null;
    }

}

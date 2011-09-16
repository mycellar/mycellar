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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.stock.CellarService;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.StockRepository;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Service
public class CellarServiceImpl implements CellarService {

    private StockRepository stockRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void newCellar(Cellar cellar) {
        stockRepository.save(cellar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Cellar, Long> getAllWithCountsFromUser(User user) {
        return stockRepository.getAllCellarsWithCountsFromUser(user);
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

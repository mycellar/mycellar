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

import fr.peralta.mycellar.application.stock.StockService;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.interfaces.facades.stock.dto.Arrival;
import fr.peralta.mycellar.interfaces.facades.stock.internal.ArrivalMapper;

/**
 * @author speralta
 */
public class StockServiceFacadeImpl implements StockServiceFacade {

    private StockService stockService;

    private final ArrivalMapper arrivalMapper = new ArrivalMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public void arrival(Arrival arrival) {
        List<Input> inputs = arrivalMapper.map(arrival, stockService);
        stockService.stock(inputs);
    }

    /**
     * @param stockService
     *            the stockService to set
     */
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}

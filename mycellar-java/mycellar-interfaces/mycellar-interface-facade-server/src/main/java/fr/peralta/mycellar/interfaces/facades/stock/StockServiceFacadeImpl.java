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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.stock.StockService;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;

/**
 * @author speralta
 */
@Service
public class StockServiceFacadeImpl implements StockServiceFacade {

    private StockService stockService;

    private MapperServiceFacade mapperServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void arrival(Arrival arrival) {
        List<Input> inputs = new ArrayList<Input>();
        float unitCharges = arrival.getOtherCharges() / arrival.getArrivalBottles().size();
        for (ArrivalBottle arrivalBottle : arrival.getArrivalBottles()) {
            inputs.add(new Input(arrival.getDate(), (Cellar) null, mapperServiceFacade.map(
                    arrivalBottle, fr.peralta.mycellar.domain.stock.Bottle.class), arrivalBottle
                    .getQuantity(), arrivalBottle.getPrice(), arrival.getSource(), unitCharges));
        }
        stockService.stock(inputs);
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
     * @param mapperServiceFacade
     *            the mapperServiceFacade to set
     */
    @Autowired
    public void setMapperServiceFacade(MapperServiceFacade mapperServiceFacade) {
        this.mapperServiceFacade = mapperServiceFacade;
    }

}

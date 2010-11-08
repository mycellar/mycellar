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

import org.hibernate.property.DirectPropertyAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.StockRepository;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
public class MockStockRepository implements StockRepository {

    private static final Logger logger = LoggerFactory.getLogger(MockStockRepository.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void stockInput(Input input) {
        logger.info("Stocks " + input.getNumber() + " " + input.getBottle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bottle findBottle(int id) {
        Bottle bottle = new Bottle(new Wine("Vin", "", WineColorEnum.RED, WineTypeEnum.STILL, "",
                2000, null, null, null), new Format("Bouteille", 1.5f));
        new DirectPropertyAccessor().getSetter(Bottle.class, "id").set(bottle, id, null);
        return bottle;
    }

}

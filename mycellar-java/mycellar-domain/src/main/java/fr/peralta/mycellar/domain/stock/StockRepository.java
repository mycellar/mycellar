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
package fr.peralta.mycellar.domain.stock;

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
public interface StockRepository {

    /**
     * @param wine
     * @param format
     * @return
     */
    Bottle findBottle(Wine wine, Format format);

    /**
     * @param bottle
     * @param cellar
     * @return
     */
    Stock findStock(Bottle bottle, Cellar cellar);

    /**
     * @param user
     * @return
     */
    Map<Cellar, Long> getAllCellarsWithCountsFromUser(User user);

    /**
     * @param cellar
     */
    Cellar save(Cellar cellar);

    /**
     * @param input
     */
    Input save(Input input);

    /**
     * @param stock
     */
    Stock save(Stock stock);

    /**
     * @param cellars
     * @return
     */
    List<Movement<?>> getAllMovementsFromCellars(Cellar... cellars);

}

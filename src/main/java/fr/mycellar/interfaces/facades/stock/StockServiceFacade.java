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
package fr.mycellar.interfaces.facades.stock;

import java.util.List;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
public interface StockServiceFacade {

    /**
     * @param arrival
     */
    void arrival(Arrival arrival);

    /**
     * @param searchParameters
     * @return
     */
    long countCellars(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countCellarShares(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countMovements(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countStocks(SearchParameters searchParameters);

    /**
     * @param cellar
     * @throws BusinessException
     */
    void deleteCellar(Cellar cellar) throws BusinessException;

    /**
     * @param cellarShare
     * @throws BusinessException
     */
    void deleteCellarShare(CellarShare cellarShare) throws BusinessException;

    /**
     * @param drink
     */
    void drink(Drink drink);

    /**
     * @param cellarId
     * @return
     */
    Cellar getCellarById(Integer cellarId);

    /**
     * @param cellarShareId
     * @return
     */
    CellarShare getCellarShareById(Integer cellarShareId);

    /**
     * @param searchParameters
     * @return
     */
    List<Cellar> getCellars(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    List<CellarShare> getCellarShares(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    List<Movement> getMovements(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    List<Stock> getStocks(SearchParameters searchParameters);

    /**
     * @param cellar
     * @throws BusinessException
     */
    void saveCellar(Cellar cellar) throws BusinessException;

    /**
     * @param cellarShare
     * @throws BusinessException
     */
    void saveCellarShare(CellarShare cellarShare) throws BusinessException;

}

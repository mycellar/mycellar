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
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
public interface StockServiceFacade {

    void arrival(Arrival arrival);

    long countCellars(SearchParameters searchParameters);

    long countCellarShares(SearchParameters searchParameters);

    long countMovements(SearchParameters searchParameters);

    long countStocks(SearchParameters searchParameters);

    void deleteCellar(Cellar cellar) throws BusinessException;

    void deleteCellarShare(CellarShare cellarShare) throws BusinessException;

    void deleteStock(Stock stock) throws BusinessException;

    void deleteMovement(Movement movement) throws BusinessException;

    void drink(Drink drink);

    Cellar getCellarById(Integer cellarId);

    CellarShare getCellarShareById(Integer cellarShareId);

    Movement getMovementById(Integer movementId);

    Stock getStockById(Integer stockId);

    List<Cellar> getCellars(SearchParameters searchParameters);

    List<Cellar> getCellars(User user);

    List<CellarShare> getCellarShares(SearchParameters searchParameters);

    List<Movement> getMovements(SearchParameters searchParameters);

    List<Stock> getStocks(SearchParameters searchParameters);

    List<Stock> getStocks(Cellar cellar);

    Cellar saveCellar(Cellar cellar) throws BusinessException;

    CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException;

    Movement saveMovement(Movement movement) throws BusinessException;

    Stock saveStock(Stock stock) throws BusinessException;

    void validateCellar(Cellar cellar) throws BusinessException;

    void validateCellarShare(CellarShare cellarShare) throws BusinessException;

    void validateMovement(Movement movement) throws BusinessException;

    void validateStock(Stock stock) throws BusinessException;

    boolean hasReadRight(Integer cellarId, String userEmail);

    boolean hasModifyRight(Integer cellarId, String userEmail);

    boolean isOwner(Integer cellarId, String userEmail);

}

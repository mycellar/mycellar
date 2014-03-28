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
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public interface StockServiceFacade {

    void arrival(Arrival arrival) throws BusinessException;

    long countCellars(SearchParameters<Cellar> search);

    long countCellarShares(SearchParameters<CellarShare> search);

    long countCellarsLike(String term, SearchParameters<Cellar> search);

    long countCellarsLike(String term, User user, SearchParameters<Cellar> search);

    long countMovements(SearchParameters<Movement> search);

    long countStocks(SearchParameters<Stock> search);

    void deleteCellar(Cellar cellar) throws BusinessException;

    void deleteCellarShare(CellarShare cellarShare) throws BusinessException;

    void deleteMovement(Movement movement) throws BusinessException;

    void deleteStock(Stock stock) throws BusinessException;

    void drink(Drink drink) throws BusinessException;

    Cellar getCellarById(Integer cellarId);

    List<Cellar> getCellars(SearchParameters<Cellar> search);

    List<Cellar> getCellars(User user);

    CellarShare getCellarShareById(Integer cellarShareId);

    List<CellarShare> getCellarShares(SearchParameters<CellarShare> search);

    List<Cellar> getCellarsLike(String term, SearchParameters<Cellar> search);

    List<Cellar> getCellarsLike(String term, User user, SearchParameters<Cellar> search);

    Movement getMovementById(Integer movementId);

    List<Movement> getMovements(SearchParameters<Movement> search);

    Stock getStockById(Integer stockId);

    List<Stock> getStocks(Cellar cellar);

    List<Stock> getStocks(SearchParameters<Stock> search);

    boolean hasModifyRight(Integer cellarId, String userEmail);

    boolean hasReadRight(Integer cellarId, String userEmail);

    boolean isOwner(Integer cellarId, String userEmail);

    Cellar saveCellar(Cellar cellar) throws BusinessException;

    CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException;

    Movement saveMovement(Movement movement) throws BusinessException;

    Stock saveStock(Stock stock) throws BusinessException;

    void validateCellar(Cellar cellar) throws BusinessException;

    void validateCellarShare(CellarShare cellarShare) throws BusinessException;

    void validateMovement(Movement movement) throws BusinessException;

    void validateStock(Stock stock) throws BusinessException;

}

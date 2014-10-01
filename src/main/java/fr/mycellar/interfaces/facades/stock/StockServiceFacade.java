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

import jpasearch.repository.query.ResultParameters;
import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Stock;

/**
 * @author speralta
 */
public interface StockServiceFacade {

    void arrival(Arrival arrival) throws BusinessException;

    long countCellars(SearchParameters<Cellar> searchParameters);

    long countFromCellars(SearchParameters<Cellar> searchParameters, ResultParameters<Cellar, ?> resultParameters);

    long countCellarsLike(String term, SearchParameters<Cellar> searchParameters);

    long countCellarShares(SearchParameters<CellarShare> searchParameters);

    long countFromCellarShares(SearchParameters<CellarShare> searchParameters, ResultParameters<CellarShare, ?> resultParameters);

    long countMovements(SearchParameters<Movement> searchParameters);

    long countFromMovements(SearchParameters<Movement> searchParameters, ResultParameters<Movement, ?> resultParameters);

    long countStocks(SearchParameters<Stock> searchParameters);

    long countFromStocks(SearchParameters<Stock> searchParameters, ResultParameters<Stock, ?> resultParameters);

    void deleteCellar(Cellar cellar) throws BusinessException;

    void deleteCellarShare(CellarShare cellarShare) throws BusinessException;

    void deleteMovement(Movement movement) throws BusinessException;

    void deleteStock(Stock stock) throws BusinessException;

    void drink(Drink drink) throws BusinessException;

    Cellar getCellarById(Integer cellarId);

    CellarShare getCellarShareById(Integer cellarShareId);

    Movement getMovementById(Integer movementId);

    Stock getStockById(Integer stockId);

    List<Cellar> getCellars(SearchParameters<Cellar> searchParameters);

    List<Cellar> getCellarsLike(String term, SearchParameters<Cellar> searchParameters);

    <X> List<X> getFromCellars(SearchParameters<Cellar> searchParameters, ResultParameters<Cellar, X> resultParameters);

    List<CellarShare> getCellarShares(SearchParameters<CellarShare> searchParameters);

    <X> List<X> getFromCellarShares(SearchParameters<CellarShare> searchParameters, ResultParameters<CellarShare, X> resultParameters);

    List<Movement> getMovements(SearchParameters<Movement> searchParameters);

    <X> List<X> getFromMovements(SearchParameters<Movement> searchParameters, ResultParameters<Movement, X> resultParameters);

    List<Stock> getStocks(SearchParameters<Stock> searchParameters);

    <X> List<X> getFromStocks(SearchParameters<Stock> searchParameters, ResultParameters<Stock, X> resultParameters);

    Cellar saveCellar(Cellar cellar) throws BusinessException;

    CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException;

    Movement saveMovement(Movement movement) throws BusinessException;

    Stock saveStock(Stock stock) throws BusinessException;

    void validateCellar(Cellar cellar) throws BusinessException;

    void validateCellarShare(CellarShare cellarShare) throws BusinessException;

    void validateMovement(Movement movement) throws BusinessException;

    void validateStock(Stock stock) throws BusinessException;

    boolean hasModifyRight(Integer cellarId, String userEmail);

    boolean hasReadRight(Integer cellarId, String userEmail);

    boolean isOwner(Integer cellarId, String userEmail);

}

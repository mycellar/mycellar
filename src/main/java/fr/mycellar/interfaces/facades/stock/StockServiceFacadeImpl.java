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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.ResultParameters;
import jpasearch.repository.query.SearchParameters;

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.stock.CellarService;
import fr.mycellar.application.stock.CellarShareService;
import fr.mycellar.application.stock.MovementService;
import fr.mycellar.application.stock.StockService;
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
@Named("stockServiceFacade")
@Singleton
public class StockServiceFacadeImpl implements StockServiceFacade {

    private CellarService cellarService;

    private CellarShareService cellarShareService;

    private MovementService movementService;

    private StockService stockService;

    @Override
    @Transactional
    public void arrival(Arrival arrival) throws BusinessException {
        stockService.stock(arrival);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCellars(SearchParameters<Cellar> searchParameters) {
        return cellarService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFromCellars(SearchParameters<Cellar> searchParameters, ResultParameters<Cellar, ?> resultParameters) {
        return cellarService.countProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCellarsLike(String term, SearchParameters<Cellar> searchParameters) {
        return cellarService.countAllLike(term, searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCellarShares(SearchParameters<CellarShare> searchParameters) {
        return cellarShareService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFromCellarShares(SearchParameters<CellarShare> searchParameters, ResultParameters<CellarShare, ?> resultParameters) {
        return cellarShareService.countProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countMovements(SearchParameters<Movement> searchParameters) {
        return movementService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFromMovements(SearchParameters<Movement> searchParameters, ResultParameters<Movement, ?> resultParameters) {
        return movementService.countProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countStocks(SearchParameters<Stock> searchParameters) {
        return stockService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFromStocks(SearchParameters<Stock> searchParameters, ResultParameters<Stock, ?> resultParameters) {
        return stockService.countProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional
    public void deleteCellar(Cellar cellar) throws BusinessException {
        cellarService.delete(cellar);
    }

    @Override
    @Transactional
    public void deleteCellarShare(CellarShare cellarShare) throws BusinessException {
        cellarShareService.delete(cellarShare);
    }

    @Override
    @Transactional
    public void deleteMovement(Movement movement) throws BusinessException {
        movementService.delete(movement);
    }

    @Override
    @Transactional
    public void deleteStock(Stock stock) throws BusinessException {
        stockService.delete(stock);
    }

    @Override
    @Transactional
    public void drink(Drink drink) throws BusinessException {
        stockService.drink(drink);
    }

    @Override
    @Transactional(readOnly = true)
    public Cellar getCellarById(Integer cellarId) {
        return cellarService.getById(cellarId);
    }

    @Override
    @Transactional(readOnly = true)
    public Movement getMovementById(Integer movementId) {
        return movementService.getById(movementId);
    }

    @Override
    @Transactional(readOnly = true)
    public Stock getStockById(Integer stockId) {
        return stockService.getById(stockId);
    }

    @Override
    public CellarShare getCellarShareById(Integer cellarShareId) {
        return cellarShareService.getById(cellarShareId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cellar> getCellars(SearchParameters<Cellar> searchParameters) {
        return cellarService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public <X> List<X> getFromCellars(SearchParameters<Cellar> searchParameters, ResultParameters<Cellar, X> resultParameters) {
        return cellarService.findProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cellar> getCellarsLike(String term, SearchParameters<Cellar> searchParameters) {
        return cellarService.getAllLike(term, searchParameters);
    }

    @Override
    public List<CellarShare> getCellarShares(SearchParameters<CellarShare> searchParameters) {
        return cellarShareService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public <X> List<X> getFromCellarShares(SearchParameters<CellarShare> searchParameters, ResultParameters<CellarShare, X> resultParameters) {
        return cellarShareService.findProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> getMovements(SearchParameters<Movement> searchParameters) {
        return movementService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public <X> List<X> getFromMovements(SearchParameters<Movement> searchParameters, ResultParameters<Movement, X> resultParameters) {
        return movementService.findProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(SearchParameters<Stock> searchParameters) {
        return stockService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public <X> List<X> getFromStocks(SearchParameters<Stock> searchParameters, ResultParameters<Stock, X> resultParameters) {
        return stockService.findProperty(searchParameters, resultParameters);
    }

    @Override
    @Transactional
    public Cellar saveCellar(Cellar cellar) throws BusinessException {
        return cellarService.save(cellar);
    }

    @Override
    @Transactional
    public CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException {
        return cellarShareService.save(cellarShare);
    }

    @Override
    @Transactional
    public Movement saveMovement(Movement movement) throws BusinessException {
        return movementService.save(movement);
    }

    @Override
    @Transactional
    public Stock saveStock(Stock stock) throws BusinessException {
        return stockService.save(stock);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateCellar(Cellar cellar) throws BusinessException {
        cellarService.validate(cellar);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateCellarShare(CellarShare cellarShare) throws BusinessException {
        cellarShareService.validate(cellarShare);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateMovement(Movement movement) throws BusinessException {
        movementService.validate(movement);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateStock(Stock stock) throws BusinessException {
        stockService.validate(stock);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasReadRight(Integer cellarId, String userEmail) {
        return cellarService.hasReadRight(cellarId, userEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasModifyRight(Integer cellarId, String userEmail) {
        return cellarService.hasModifyRight(cellarId, userEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(Integer cellarId, String userEmail) {
        Cellar cellar = getCellarById(cellarId);
        return (cellar != null) && cellar.getOwner().getEmail().equals(userEmail);
    }

    // BEANS METHODS

    @Inject
    public void setCellarService(CellarService cellarService) {
        this.cellarService = cellarService;
    }

    @Inject
    public void setCellarShareService(CellarShareService cellarShareService) {
        this.cellarShareService = cellarShareService;
    }

    @Inject
    public void setMovementService(MovementService movementService) {
        this.movementService = movementService;
    }

    @Inject
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}

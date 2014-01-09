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

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.stock.CellarService;
import fr.mycellar.application.stock.CellarShareService;
import fr.mycellar.application.stock.MovementService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.AccessRightEnum;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

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
    public void arrival(Arrival arrival) {
        stockService.stock(arrival);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCellars(SearchParameters searchParameters) {
        return cellarService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCellarShares(SearchParameters searchParameters) {
        return cellarShareService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countMovements(SearchParameters searchParameters) {
        return movementService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countStocks(SearchParameters searchParameters) {
        return stockService.count(searchParameters);
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
    public void drink(Drink drink) {
        stockService.drink(drink);
    }

    @Override
    @Transactional(readOnly = true)
    public Cellar getCellarById(Integer cellarId) {
        return cellarService.getById(cellarId);
    }

    @Override
    public CellarShare getCellarShareById(Integer cellarShareId) {
        return cellarShareService.getById(cellarShareId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cellar> getCellars(SearchParameters searchParameters) {
        return cellarService.find(searchParameters);
    }

    @Override
    public List<CellarShare> getCellarShares(SearchParameters searchParameters) {
        return cellarShareService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> getMovements(SearchParameters searchParameters) {
        return movementService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(SearchParameters searchParameters) {
        return stockService.find(searchParameters);
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
    public List<Cellar> getCellars(User user) {
        return getCellars(new SearchParameters().orMode().property(Cellar_.owner, user).property(Cellar_.shares, CellarShare_.email, user.getEmail()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(Cellar cellar) {
        return getStocks(new SearchParameters().property(Stock_.cellar, cellar));
    }

    @Override
    public boolean hasReadRight(Integer cellarId, String userEmail) {
        Cellar cellar = getCellarById(cellarId);
        return cellar.getOwner().getEmail().equals(userEmail) //
                || (countCellarShares(new SearchParameters() //
                        .property(CellarShare_.email, userEmail) //
                        .property(CellarShare_.cellar, Cellar_.id, cellarId)) > 0);
    }

    @Override
    public boolean hasModifyRight(Integer cellarId, String userEmail) {
        Cellar cellar = getCellarById(cellarId);
        return cellar.getOwner().getEmail().equals(userEmail) //
                || (countCellarShares(new SearchParameters() //
                        .property(CellarShare_.email, userEmail) //
                        .property(CellarShare_.cellar, Cellar_.id, cellarId) //
                        .property(CellarShare_.accessRight, AccessRightEnum.MODIFY)) > 0);
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

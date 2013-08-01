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
package fr.peralta.mycellar.application.stock.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.stock.CellarService;
import fr.peralta.mycellar.application.stock.CellarShareService;
import fr.peralta.mycellar.application.stock.StockService;
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare_;
import fr.peralta.mycellar.domain.stock.Cellar_;
import fr.peralta.mycellar.domain.stock.Stock_;
import fr.peralta.mycellar.domain.stock.repository.CellarRepository;
import fr.peralta.mycellar.domain.user.User_;

/**
 * @author speralta
 */
@Named
@Singleton
public class CellarServiceImpl extends AbstractSimpleService<Cellar, CellarRepository> implements CellarService {

    private CellarRepository cellarRepository;

    private CellarShareService cellarShareService;

    private StockService stockService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Cellar entity) throws BusinessException {
        Cellar existing = cellarRepository.findUniqueOrNone(new SearchParametersBuilder() //
                .propertyWithValue(entity.getOwner().getId(), Cellar_.owner, User_.id) //
                .propertyWithValue(entity.getName(), NamedEntity_.name) //
                .toSearchParameters());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateDelete(Cellar entity) throws BusinessException {
        if (stockService.count(new SearchParametersBuilder() //
                .propertyWithValue(entity, Stock_.cellar) //
                .toSearchParameters()) > 0) {
            throw new BusinessException(BusinessError.CELLAR_00002);
        }
        if (cellarShareService.count(new SearchParametersBuilder() //
                .propertyWithValue(entity, CellarShare_.cellar) //
                .toSearchParameters()) > 0) {
            throw new BusinessException(BusinessError.CELLAR_00003);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellarRepository getRepository() {
        return cellarRepository;
    }

    /**
     * @param cellarRepository
     *            the cellarRepository to set
     */
    @Inject
    public void setCellarRepository(CellarRepository cellarRepository) {
        this.cellarRepository = cellarRepository;
    }

    /**
     * @param cellarShareService
     *            the cellarShareService to set
     */
    @Inject
    public void setCellarShareService(CellarShareService cellarShareService) {
        this.cellarShareService = cellarShareService;
    }

    /**
     * @param stockService
     *            the stockService to set
     */
    @Inject
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}

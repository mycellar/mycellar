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
package fr.mycellar.application.stock.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.stock.CellarService;
import fr.mycellar.application.stock.CellarShareService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.stock.repository.CellarRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class CellarServiceImpl extends AbstractSearchableService<Cellar, CellarRepository> implements CellarService {

    private CellarRepository cellarRepository;

    private CellarShareService cellarShareService;

    private StockService stockService;

    @Override
    public long countAllForUserLike(String term, User user, SearchParameters<Cellar> search) {
        return count(addUserToSearchParametersParameters(user, addTermToSearchParametersParameters(term, search)));
    }

    @Override
    public List<Cellar> getAllForUserLike(String term, User user, SearchParameters<Cellar> search) {
        return find(addUserToSearchParametersParameters(user, addTermToSearchParametersParameters(term, search)));
    }

    @Override
    public void validate(Cellar entity) throws BusinessException {
        Cellar existing = cellarRepository.findUniqueOrNone(new SearchBuilder<Cellar>() //
                .property(Cellar_.owner).equalsTo(entity.getOwner()) //
                .property(NamedEntity_.name).equalsTo(entity.getName()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_00001);
        }
    }

    @Override
    protected void validateDelete(Cellar entity) throws BusinessException {
        if (stockService.count(new SearchBuilder<Stock>() //
                .property(Stock_.cellar).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.CELLAR_00002);
        }
        if (cellarShareService.count(new SearchBuilder<CellarShare>() //
                .property(CellarShare_.cellar).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.CELLAR_00003);
        }
    }

    @Override
    protected SearchParameters<Cellar> addTermToSearchParametersParameters(String term, SearchParameters<Cellar> search) {
        return new SearchBuilder<Cellar>(search).fullText(NamedEntity_.name, term).build();
    }

    protected SearchParameters<Cellar> addUserToSearchParametersParameters(User user, SearchParameters<Cellar> search) {
        return new SearchBuilder<Cellar>(search).disjunction() //
                .property(Cellar_.owner).equalsTo(user) //
                .property(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail()) //
                .end().build();
    }

    @Override
    protected CellarRepository getRepository() {
        return cellarRepository;
    }

    @Inject
    public void setCellarRepository(CellarRepository cellarRepository) {
        this.cellarRepository = cellarRepository;
    }

    @Inject
    public void setCellarShareService(CellarShareService cellarShareService) {
        this.cellarShareService = cellarShareService;
    }

    @Inject
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}

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
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Cellar_;
import fr.peralta.mycellar.domain.stock.repository.CellarRepository;
import fr.peralta.mycellar.domain.user.User_;

/**
 * @author speralta
 */
@Named
@Singleton
public class CellarServiceImpl extends AbstractSimpleService<Cellar, CellarRepository> implements
        CellarService {

    private CellarRepository cellarRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Cellar entity) throws BusinessException {
        Cellar existing = cellarRepository
                .findUniqueOrNone(new SearchParameters() //
                        .property(
                                PropertySelector.newPropertySelector(entity.getOwner().getId(),
                                        Cellar_.owner, User_.id)) //
                        .property(
                                PropertySelector.newPropertySelector(entity.getName(),
                                        NamedEntity_.name)) //
                );
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_00001);
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

}

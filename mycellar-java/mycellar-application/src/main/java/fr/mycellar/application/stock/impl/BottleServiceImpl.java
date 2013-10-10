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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.BottleService;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Bottle_;
import fr.mycellar.domain.stock.repository.BottleRepository;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Format_;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.Wine_;

/**
 * @author speralta
 */
@Named
@Singleton
public class BottleServiceImpl extends AbstractSimpleService<Bottle, BottleRepository> implements BottleService {

    private BottleRepository bottleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Bottle entity) throws BusinessException {
        Bottle existing = find(entity.getWine(), entity.getFormat());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.BOTTLE_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bottle find(Wine wine, Format format) {
        return bottleRepository.findUniqueOrNone(new SearchParametersBuilder() //
                .propertyWithValue(format.getId(), Bottle_.format, Format_.id) //
                .propertyWithValue(wine.getId(), Bottle_.wine, Wine_.id)//
                .toSearchParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BottleRepository getRepository() {
        return bottleRepository;
    }

    /**
     * @param bottleRepository
     *            the bottleRepository to set
     */
    @Inject
    public void setBottleRepository(BottleRepository bottleRepository) {
        this.bottleRepository = bottleRepository;
    }

}

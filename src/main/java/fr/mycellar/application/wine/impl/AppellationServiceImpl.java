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
package fr.mycellar.application.wine.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.wine.AppellationService;
import fr.mycellar.application.wine.WineService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.domain.wine.Region_;
import fr.mycellar.domain.wine.Wine_;
import fr.mycellar.domain.wine.repository.AppellationRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class AppellationServiceImpl extends AbstractSimpleService<Appellation, AppellationRepository> implements AppellationService {

    private AppellationRepository appellationRepository;

    private WineService wineService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Appellation entity) throws BusinessException {
        if (entity.getRegion() == null) {
            throw new BusinessException(BusinessError.APPELLATION_00001);
        }
        Appellation existing = appellationRepository.findUniqueOrNone(new SearchParametersBuilder() //
                .propertyWithValue(entity.getRegion().getId(), Appellation_.region, Region_.id) //
                .propertyWithValue(entity.getName(), NamedEntity_.name) //
                .toSearchParameters());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.APPELLATION_00002);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateDelete(Appellation entity) throws BusinessException {
        if (wineService.count(new SearchParametersBuilder() //
                .propertyWithValue(entity, Wine_.appellation) //
                .toSearchParameters()) > 0) {
            throw new BusinessException(BusinessError.APPELLATION_00003);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AppellationRepository getRepository() {
        return appellationRepository;
    }

    /**
     * @param appellationRepository
     *            the appellationRepository to set
     */
    @Inject
    public void setAppellationRepository(AppellationRepository appellationRepository) {
        this.appellationRepository = appellationRepository;
    }

    /**
     * @param wineService
     *            the wineService to set
     */
    @Inject
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

}

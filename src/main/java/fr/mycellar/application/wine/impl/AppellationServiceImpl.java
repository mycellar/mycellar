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
import fr.mycellar.application.wine.RegionService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.infrastructure.wine.repository.AppellationRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class AppellationServiceImpl extends AbstractSimpleService<Appellation, AppellationRepository> implements AppellationService {

    private AppellationRepository appellationRepository;

    private RegionService regionService;

    @Override
    public void validate(Appellation entity) throws BusinessException {
        if (entity.getRegion() == null) {
            throw new BusinessException(BusinessError.APPELLATION_00001);
        } else {
            try {
                regionService.validate(entity.getRegion());
            } catch (BusinessException e) {
                throw new BusinessException(BusinessError.APPELLATION_00004, e);
            }
        }
        Appellation existing = appellationRepository.findUniqueOrNone(new SearchParameters() //
                .property(Appellation_.region, entity.getRegion()) //
                .property(NamedEntity_.name, entity.getName()));
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.APPELLATION_00002);
        }
    }

    @Override
    protected void validateDelete(Appellation entity) throws BusinessException {
        if (appellationRepository.findPropertyCount(new SearchParameters().property(Appellation_.id, entity.getId()), Appellation_.wines) > 0) {
            throw new BusinessException(BusinessError.APPELLATION_00003);
        }
    }

    @Override
    protected AppellationRepository getRepository() {
        return appellationRepository;
    }

    @Inject
    public void setAppellationRepository(AppellationRepository appellationRepository) {
        this.appellationRepository = appellationRepository;
    }

    @Inject
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}

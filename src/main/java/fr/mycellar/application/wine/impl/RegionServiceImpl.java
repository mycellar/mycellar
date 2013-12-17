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
import fr.mycellar.application.wine.CountryService;
import fr.mycellar.application.wine.RegionService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Region_;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.infrastructure.wine.repository.RegionRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class RegionServiceImpl extends AbstractSimpleService<Region, RegionRepository> implements RegionService {

    private RegionRepository regionRepository;

    private CountryService countryService;

    @Override
    public void validate(Region entity) throws BusinessException {
        if (entity.getCountry() == null) {
            throw new BusinessException(BusinessError.REGION_00001);
        } else {
            try {
                countryService.validate(entity.getCountry());
            } catch (BusinessException e) {
                throw new BusinessException(BusinessError.REGION_00004, e);
            }
        }
        Region existing = regionRepository.findUniqueOrNone(new SearchParameters() //
                .property(Region_.country, entity.getCountry()) //
                .property(NamedEntity_.name, entity.getName()));
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.REGION_00002);
        }
    }

    @Override
    protected void validateDelete(Region entity) throws BusinessException {
        if (regionRepository.findPropertyCount(new SearchParameters() //
                .property(Region_.id, entity.getId()), Region_.appellations) > 0) {
            throw new BusinessException(BusinessError.REGION_00003);
        }
    }

    @Override
    protected RegionRepository getRepository() {
        return regionRepository;
    }

    @Inject
    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Inject
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

}

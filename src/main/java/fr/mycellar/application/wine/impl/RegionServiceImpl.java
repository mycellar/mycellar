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

import jpasearch.repository.query.SearchBuilder;
import jpasearch.repository.query.SearchParameters;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.wine.CountryService;
import fr.mycellar.application.wine.RegionService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Region_;
import fr.mycellar.infrastructure.wine.repository.RegionRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class RegionServiceImpl extends AbstractSearchableService<Region, RegionRepository> implements RegionService {

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
        Region existing = regionRepository.findUniqueOrNone(new SearchBuilder<Region>() //
                .on(Region_.country).equalsTo(entity.getCountry()) //
                .on(NamedEntity_.name).equalsTo(entity.getName()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.REGION_00002);
        }
    }

    @Override
    protected void validateDelete(Region entity) throws BusinessException {
        if (regionRepository.findPropertyCount(new SearchBuilder<Region>() //
                .on(Region_.id).equalsTo(entity.getId()).build(), Region_.appellations) > 0) {
            throw new BusinessException(BusinessError.REGION_00003);
        }
    }

    @Override
    protected SearchParameters<Region> addTermToSearchParametersParameters(String term, SearchParameters<Region> searchParameters) {
        return new SearchBuilder<>(searchParameters).fullText(NamedEntity_.name).search(term).build();
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

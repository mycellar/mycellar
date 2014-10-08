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

import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.ResultBuilder;
import jpasearch.repository.query.builder.SearchBuilder;
import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.wine.AppellationService;
import fr.mycellar.application.wine.CountryService;
import fr.mycellar.application.wine.RegionService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.infrastructure.wine.repository.AppellationRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class AppellationServiceImpl extends AbstractSearchableService<Appellation, AppellationRepository> implements AppellationService {

    private AppellationRepository appellationRepository;

    private RegionService regionService;
    private CountryService countryService;
    private ConfigurationService configurationService;

    @Override
    public void validate(Appellation entity) throws BusinessException {
        if ((entity.getRegion() == null) && (entity.getCountry() == null)) {
            throw new BusinessException(BusinessError.APPELLATION_00001);
        } else if (entity.getRegion() != null) {
            try {
                regionService.validate(entity.getRegion());
            } catch (BusinessException e) {
                throw new BusinessException(BusinessError.APPELLATION_00004, e);
            }
        } else if (entity.getCountry() != null) {
            try {
                countryService.validate(entity.getCountry());
            } catch (BusinessException e) {
                throw new BusinessException(BusinessError.APPELLATION_00004, e);
            }
        }
        Appellation existing = appellationRepository.findUniqueOrNone(new SearchBuilder<Appellation>() //
                .on(Appellation_.region).equalsTo(entity.getRegion()) //
                .on(Appellation_.country).equalsTo(entity.getCountry()) //
                .on(NamedEntity_.name).equalsTo(entity.getName()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.APPELLATION_00002);
        }
    }

    @Override
    protected void validateDelete(Appellation entity) throws BusinessException {
        if (countProperty(new SearchBuilder<Appellation>().on(Appellation_.id).equalsTo(entity.getId()).build(), new ResultBuilder<>(Appellation_.wines).build()) > 0) {
            throw new BusinessException(BusinessError.APPELLATION_00003);
        }
    }

    @Override
    protected SearchParameters<Appellation> addTermToSearchParametersParameters(String term, SearchParameters<Appellation> searchParameters) {
        return new SearchBuilder<>(searchParameters) //
                .fullText(NamedEntity_.name) //
                .andOn(Appellation_.region).to(NamedEntity_.name) //
                .andOn(Appellation_.country).to(NamedEntity_.name) //
                .searchSimilarity(configurationService.getDefaultSearchSimilarity()) //
                .andMode().search(term).build();
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

    @Inject
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Inject
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}

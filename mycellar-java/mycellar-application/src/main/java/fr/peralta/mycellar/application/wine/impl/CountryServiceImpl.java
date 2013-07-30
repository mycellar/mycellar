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
package fr.peralta.mycellar.application.wine.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.wine.CountryService;
import fr.peralta.mycellar.application.wine.RegionService;
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region_;
import fr.peralta.mycellar.domain.wine.repository.CountryRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class CountryServiceImpl extends AbstractSimpleService<Country, CountryRepository> implements CountryService {

    private CountryRepository countryRepository;

    private RegionService regionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Country find(String name) {
        return countryRepository.findUniqueOrNone(new SearchParameters().property(PropertySelector.newPropertySelector(name, NamedEntity_.name)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Country entity) throws BusinessException {
        Country existing = find(entity.getName());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.COUNTRY_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateDelete(Country entity) throws BusinessException {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.addProperty(PropertySelector.newPropertySelector(entity, Region_.country));
        if (regionService.count(searchParameters) > 0) {
            throw new BusinessException(BusinessError.COUNTRY_00002);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CountryRepository getRepository() {
        return countryRepository;
    }

    /**
     * @param countryRepository
     *            the countryRepository to set
     */
    @Inject
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * @param regionService
     *            the regionService to set
     */
    @Inject
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}

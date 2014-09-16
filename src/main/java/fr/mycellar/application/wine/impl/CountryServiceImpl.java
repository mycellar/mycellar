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
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.wine.CountryService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.Country_;
import fr.mycellar.infrastructure.wine.repository.CountryRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class CountryServiceImpl extends AbstractSearchableService<Country, CountryRepository> implements CountryService {

    private CountryRepository countryRepository;

    @Override
    public Country find(String name) {
        return countryRepository.findUniqueOrNone(new SearchBuilder<Country>().on(NamedEntity_.name).equalsTo(name).build());
    }

    @Override
    public void validate(Country entity) throws BusinessException {
        Country existing = find(entity.getName());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.COUNTRY_00001);
        }
    }

    @Override
    protected void validateDelete(Country entity) throws BusinessException {
        if (countProperty(new SearchBuilder<Country>().on(Country_.id).equalsTo(entity.getId()).build(), new ResultBuilder<>(Country_.regions).build()) > 0) {
            throw new BusinessException(BusinessError.COUNTRY_00002);
        }
    }

    @Override
    protected SearchParameters<Country> addTermToSearchParametersParameters(String term, SearchParameters<Country> searchParameters) {
        return new SearchBuilder<>(searchParameters).fullText(NamedEntity_.name).andMode().search(term).build();
    }

    @Override
    protected CountryRepository getRepository() {
        return countryRepository;
    }

    @Inject
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

}

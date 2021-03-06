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
import jpasearch.repository.query.builder.SearchBuilder;
import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.wine.ProducerService;
import fr.mycellar.application.wine.WineService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.Wine_;
import fr.mycellar.infrastructure.wine.repository.ProducerRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class ProducerServiceImpl extends AbstractSearchableService<Producer, ProducerRepository> implements ProducerService {

    private ProducerRepository producerRepository;

    private WineService wineService;
    private ConfigurationService configurationService;

    @Override
    public void validate(Producer entity) throws BusinessException {
        Producer existing = producerRepository.findUniqueOrNone( //
                new SearchBuilder<Producer>().on(NamedEntity_.name).equalsTo(entity.getName()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.PRODUCER_00001);
        }
    }

    @Override
    protected void validateDelete(Producer entity) throws BusinessException {
        if (wineService.count(new SearchBuilder<Wine>().on(Wine_.producer).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.PRODUCER_00002);
        }
    }

    @Override
    protected SearchParameters<Producer> addTermToSearchParametersParameters(String term, SearchParameters<Producer> searchParameters) {
        return new SearchBuilder<>(searchParameters) //
                .fullText(NamedEntity_.name) //
                .searchSimilarity(configurationService.getDefaultSearchSimilarity()) //
                .andMode().search(term).build();
    }

    @Override
    public ProducerRepository getRepository() {
        return producerRepository;
    }

    @Inject
    public void setProducerRepository(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Inject
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    @Inject
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}

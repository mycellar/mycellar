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
package fr.mycellar.interfaces.facades.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@Named("administrationServiceFacade")
@Singleton
public class AdministrationServiceFacadeImpl implements AdministrationServiceFacade {

    private ConfigurationService configurationService;

    @Override
    @Transactional(readOnly = true)
    public long countConfigurations(SearchParameters<Configuration> searchParameters) {
        return configurationService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationById(Integer configurationId) {
        return configurationService.getById(configurationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Configuration> getConfigurations(SearchParameters<Configuration> searchParameters) {
        return configurationService.find(searchParameters);
    }

    @Override
    @Transactional
    public Configuration saveConfiguration(Configuration configuration) throws BusinessException {
        return configurationService.save(configuration);
    }

    @Inject
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}

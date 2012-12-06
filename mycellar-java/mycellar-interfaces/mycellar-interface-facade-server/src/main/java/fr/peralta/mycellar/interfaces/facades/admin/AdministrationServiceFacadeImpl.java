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
package fr.peralta.mycellar.interfaces.facades.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.admin.ConfigurationService;
import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
@Service
public class AdministrationServiceFacadeImpl implements AdministrationServiceFacade {

    private ConfigurationService configurationService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countConfigurations() {
        return configurationService.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Configuration getConfigurationById(Integer configurationId) {
        return configurationService.getById(configurationId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Configuration> getConfigurations(ConfigurationOrder orders, long first, long count) {
        return configurationService.getAll(orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveConfiguration(Configuration configuration) throws BusinessException {
        configurationService.save(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteConfiguration(Configuration configuration) throws BusinessException {
        configurationService.delete(configuration);
    }

    /**
     * @param configurationService
     *            the configurationService to set
     */
    @Autowired
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}

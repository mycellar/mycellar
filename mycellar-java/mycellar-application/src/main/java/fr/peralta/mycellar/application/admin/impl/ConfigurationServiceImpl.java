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
package fr.peralta.mycellar.application.admin.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.admin.ConfigurationService;
import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.admin.Configuration_;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;

/**
 * @author speralta
 */
@Named
@Singleton
public class ConfigurationServiceImpl extends AbstractSimpleService<Configuration, ConfigurationRepository> implements ConfigurationService {

    private ConfigurationRepository configurationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getReminderAddressReceivers() {
        return find(ConfigurationKeyEnum.REMINDER_ADDRESS_RECEIVERS).getValue().split(",");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMailAddressSender() {
        return find(ConfigurationKeyEnum.MAIL_ADDRESS_SENDER).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration find(ConfigurationKeyEnum key) {
        return configurationRepository.findUniqueOrNone(new SearchParametersBuilder() //
                .propertyWithValue(key, Configuration_.key) //
                .toSearchParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Configuration entity) throws BusinessException {
        Configuration existing = find(entity.getKey());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CONFIGURATION_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConfigurationRepository getRepository() {
        return configurationRepository;
    }

    /**
     * @param configurationRepository
     *            the configurationRepository to set
     */
    @Inject
    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

}

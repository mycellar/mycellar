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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.admin.ConfigurationService;
import fr.peralta.mycellar.application.shared.AbstractEntityService;
import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrderEnum;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
@Service
public class ConfigurationServiceImpl
        extends
        AbstractEntityService<Configuration, ConfigurationOrderEnum, ConfigurationOrder, ConfigurationRepository>
        implements ConfigurationService {

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
        return configurationRepository.find(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Configuration entity) throws BusinessException {
        Configuration existing = find(entity.getKey());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
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
    @Autowired
    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

}

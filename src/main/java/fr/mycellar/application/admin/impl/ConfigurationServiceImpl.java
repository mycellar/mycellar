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
package fr.mycellar.application.admin.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.builder.SearchBuilder;

import org.apache.commons.lang3.StringUtils;

import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.mycellar.domain.admin.Configuration_;
import fr.mycellar.domain.shared.ValidationPattern;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.admin.repository.ConfigurationRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class ConfigurationServiceImpl extends AbstractSimpleService<Configuration, ConfigurationRepository> implements ConfigurationService {

    private ConfigurationRepository configurationRepository;

    @Override
    public String[] getReminderAddressReceivers() {
        String value = find(ConfigurationKeyEnum.REMINDER_ADDRESS_RECEIVERS).getValue();
        return value != null ? value.split(",") : new String[0];
    }

    @Override
    public String getMailAddressSender() {
        String value = getValue(ConfigurationKeyEnum.MAIL_ADDRESS_SENDER);
        return value != null ? value : "donot@reply.com";
    }

    @Override
    public Integer getDefaultSearchSimilarity() {
        String searchSimilarity = getValue(ConfigurationKeyEnum.DEFAULT_SEARCH_SIMILARITY);
        if (StringUtils.isNotBlank(searchSimilarity)) {
            Integer value;
            try {
                value = Integer.parseInt(searchSimilarity);
            } catch (NumberFormatException e) {
                value = null;
            }
            if (value != null) {
                switch (value) {
                case 1:
                    return 1;
                case 2:
                    return 2;
                }
            }
        }
        // by default
        return 1;
    }

    private String getValue(ConfigurationKeyEnum key) {
        Configuration configuration = find(key);
        if (configuration != null) {
            return configuration.getValue();
        } else {
            return null;
        }
    }

    @Override
    public Configuration find(ConfigurationKeyEnum key) {
        return configurationRepository.findUniqueOrNone(new SearchBuilder<Configuration>() //
                .on(Configuration_.key).equalsTo(key).build());
    }

    @Override
    public void validate(Configuration entity) throws BusinessException {
        Configuration existing = find(entity.getKey());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CONFIGURATION_00001);
        }
        switch (entity.getKey()) {
        case MAIL_ADDRESS_SENDER:
        case REMINDER_ADDRESS_RECEIVERS:
            if (!entity.getValue().matches(ValidationPattern.EMAIL_PATTERN)) {
                throw new BusinessException(BusinessError.CONFIGURATION_00002);
            }
            break;
        default:
            break;
        }
    }

    @Override
    protected ConfigurationRepository getRepository() {
        return configurationRepository;
    }

    @Inject
    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

}

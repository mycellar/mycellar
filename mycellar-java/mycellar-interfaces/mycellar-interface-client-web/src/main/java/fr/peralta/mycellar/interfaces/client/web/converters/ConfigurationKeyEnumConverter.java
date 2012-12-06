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
package fr.peralta.mycellar.interfaces.client.web.converters;

import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;

import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.interfaces.client.web.renderers.admin.ConfigurationKeyEnumRenderer;

/**
 * @author speralta
 */
public class ConfigurationKeyEnumConverter extends AbstractConverter<ConfigurationKeyEnum> {

    private static final long serialVersionUID = 201212060952L;

    private final ConfigurationKeyEnumRenderer configurationKeyEnumRenderer;

    /**
     * Default constructor.
     */
    public ConfigurationKeyEnumConverter() {
        configurationKeyEnumRenderer = new ConfigurationKeyEnumRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigurationKeyEnum convertToObject(String value, Locale locale) {
        for (ConfigurationKeyEnum configurationKeyEnum : ConfigurationKeyEnum.values()) {
            if (configurationKeyEnumRenderer.getLabel(configurationKeyEnum).equals(value)) {
                return configurationKeyEnum;
            }
        }
        throw newConversionException("Unknown value.", value, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<ConfigurationKeyEnum> getTargetType() {
        return ConfigurationKeyEnum.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToString(ConfigurationKeyEnum value, Locale locale) {
        return configurationKeyEnumRenderer.getLabel(value);
    }

}

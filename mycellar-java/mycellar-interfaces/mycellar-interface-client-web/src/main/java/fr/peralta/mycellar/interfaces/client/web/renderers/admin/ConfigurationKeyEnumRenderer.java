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
package fr.peralta.mycellar.interfaces.client.web.renderers.admin;

import org.apache.wicket.WicketRuntimeException;
import org.springframework.stereotype.Component;

import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;

/**
 * @author speralta
 */
@Component
public class ConfigurationKeyEnumRenderer extends AbstractRenderer<ConfigurationKeyEnum> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(ConfigurationKeyEnum object) {
        if (object == null) {
            return NULL_OBJECT;
        } else {
            switch (object) {
            case MAIL_ADDRESS_SENDER:
                return "Addresse mail expéditeur";
            case REMINDER_ADDRESS_RECEIVERS:
                return "Addresse mail recevant les rappels de contact";
            default:
                throw new WicketRuntimeException("Unknown "
                        + ConfigurationKeyEnum.class.getSimpleName() + " '" + object + "'.");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<ConfigurationKeyEnum> getRenderedClass() {
        return ConfigurationKeyEnum.class;
    }

}

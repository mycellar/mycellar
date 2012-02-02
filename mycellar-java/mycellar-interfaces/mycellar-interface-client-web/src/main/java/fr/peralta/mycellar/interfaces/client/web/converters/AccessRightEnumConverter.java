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

import fr.peralta.mycellar.domain.stock.AccessRightEnum;
import fr.peralta.mycellar.interfaces.client.web.renderers.stock.AccessRightEnumRenderer;

/**
 * @author speralta
 */
public class AccessRightEnumConverter extends AbstractConverter<AccessRightEnum> {

    private static final long serialVersionUID = 201109101216L;

    private final AccessRightEnumRenderer accessRightEnumRenderer;

    /**
     * Default constructor.
     */
    public AccessRightEnumConverter() {
        accessRightEnumRenderer = new AccessRightEnumRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRightEnum convertToObject(String value, Locale locale) {
        for (AccessRightEnum accessRightEnum : AccessRightEnum.values()) {
            if (accessRightEnumRenderer.getLabel(accessRightEnum).equals(value)) {
                return accessRightEnum;
            }
        }
        throw newConversionException("Unknown value.", value, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<AccessRightEnum> getTargetType() {
        return AccessRightEnum.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToString(AccessRightEnum value, Locale locale) {
        return accessRightEnumRenderer.getLabel(value);
    }

}

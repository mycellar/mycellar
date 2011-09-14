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

import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.renderers.wine.WineTypeEnumRenderer;

/**
 * @author speralta
 */
public class WineTypeEnumConverter extends AbstractConverter<WineTypeEnum> {

    private static final long serialVersionUID = 201109101216L;

    private final WineTypeEnumRenderer wineTypeEnumRenderer;

    /**
     * Default constructor.
     */
    public WineTypeEnumConverter() {
        wineTypeEnumRenderer = new WineTypeEnumRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WineTypeEnum convertToObject(String value, Locale locale) {
        for (WineTypeEnum wineTypeEnum : WineTypeEnum.values()) {
            if (wineTypeEnumRenderer.getLabel(wineTypeEnum).equals(value)) {
                return wineTypeEnum;
            }
        }
        throw newConversionException("Unknown value.", value, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<WineTypeEnum> getTargetType() {
        return WineTypeEnum.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToString(WineTypeEnum value, Locale locale) {
        return wineTypeEnumRenderer.getLabel(value);
    }

}

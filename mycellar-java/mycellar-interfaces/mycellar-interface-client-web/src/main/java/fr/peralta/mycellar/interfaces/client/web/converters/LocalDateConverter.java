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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 * @author speralta
 */
public class LocalDateConverter extends AbstractConverter<LocalDate> {

    private static final long serialVersionUID = 201107191952L;

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate convertToObject(String value, Locale locale) {
        return DateTimeFormat.shortDate().withLocale(locale).parseDateTime(value).toLocalDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToString(LocalDate value, Locale locale) {
        return DateTimeFormat.shortDate().withLocale(locale).print(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<LocalDate> getTargetType() {
        return LocalDate.class;
    }

}

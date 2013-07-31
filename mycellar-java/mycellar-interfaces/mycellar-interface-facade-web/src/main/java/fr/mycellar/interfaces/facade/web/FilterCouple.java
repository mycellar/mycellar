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
package fr.mycellar.interfaces.facade.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;

/**
 * @author speralta
 */
public class FilterCouple {

    private final String property;
    private final Object filter;

    /**
     * Default constructor.
     * 
     * @param couple
     */
    public FilterCouple(String couple) {
        property = couple.substring(0, couple.indexOf(","));
        String value = couple.substring(couple.indexOf(",") + 1);
        Object result = null;
        try {
            result = new Long(value);
        } catch (Exception e) {
        }
        if (result == null) {
            try {
                result = new Double(value);
            } catch (Exception e) {
            }
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value);
            } catch (Exception e) {
            }
        }
        if (result == null) {
            result = value;
        }
        filter = result;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the filter
     */
    public Object getFilter() {
        return filter;
    }

    /**
     * @return
     */
    public boolean isFilterSet() {
        return (filter != null) && (!(filter instanceof String) || StringUtils.isNotEmpty((String) filter));
    }
}

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
package fr.peralta.mycellar.interfaces.client.web.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;

/**
 * @author speralta
 */
public class FilterEnumHelper {

    /**
     * @param filters
     * @param filterToRemove
     * @return
     */
    public static FilterEnum[] removeFilter(FilterEnum[] filters, FilterEnum filterToRemove) {
        FilterEnum[] result;
        if (filters != null) {
            List<FilterEnum> filtersList = new ArrayList<FilterEnum>(Arrays.asList(filters));
            filtersList.remove(filterToRemove);
            result = filtersList.toArray(new FilterEnum[filtersList.size()]);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Refuse instanciation.
     */
    private FilterEnumHelper() {
        throw new IllegalStateException(FilterEnumHelper.class.getSimpleName()
                + " must not be instantiated.");
    }
}

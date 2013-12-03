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
package fr.mycellar.interfaces.web.services;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.infrastructure.shared.repository.MetamodelUtil;
import fr.mycellar.infrastructure.shared.repository.SearchMode;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class SearchParametersUtil {

    private MetamodelUtil metamodelUtil;

    /**
     * @param first
     * @param count
     * @param filters
     * @param orders
     * @return
     */
    public SearchParameters getSearchParametersForListWithCount(int first, int count, List<FilterCouple> filters, List<OrderCouple> orders, Class<?> clazz) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.searchMode(SearchMode.ANYWHERE);
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(metamodelUtil.toAttribute(filter.getProperty(), clazz), filter.getFilter());
            }
        }
        searchParameters.firstResult(first).maxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.orderBy(order.getDirection(), order.getProperty(), clazz);
        }
        return searchParameters;
    }

}

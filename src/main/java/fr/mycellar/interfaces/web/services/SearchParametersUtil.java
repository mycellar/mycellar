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

import fr.mycellar.infrastructure.shared.repository.query.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.SearchMode;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class SearchParametersUtil {

    public <E> SearchParameters<E> getSearchParametersForListWithCount(int first, int count, List<FilterCouple> filters, List<OrderCouple> orders, Class<E> clazz) {
        SearchParameters<E> searchParameters = new SearchParameters<E>();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(new PropertySelector<>(filter.getProperty(), clazz).searchMode(SearchMode.ANYWHERE).selected(filter.getFilter()));
            }
        }
        searchParameters.paginate(first, count);
        for (OrderCouple order : orders) {
            searchParameters.orderBy(order.getDirection(), order.getProperty(), clazz);
        }
        return searchParameters;
    }

}

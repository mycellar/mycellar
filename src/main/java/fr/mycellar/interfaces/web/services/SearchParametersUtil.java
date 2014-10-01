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

import jpasearch.repository.query.Path;
import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.SearchBuilder;

/**
 * @author speralta
 */
@Named
@Singleton
public class SearchParametersUtil {

    public <E> SearchBuilder<E> getSearchBuilder(int first, int count, List<FilterCouple> filters, List<OrderCouple> orders, Class<E> clazz) {
        SearchBuilder<E> searchBuilder = new SearchBuilder<E>();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchBuilder.on(new Path<E, Object>(clazz, filter.getProperty())).anywhere(filter.getFilter());
            }
        }
        searchBuilder.paginate(first, count);
        for (OrderCouple order : orders) {
            searchBuilder.orderBy(order.getDirection(), order.getProperty(), clazz);
        }
        return searchBuilder;
    }

    public <E> SearchParameters<E> getSearchParameters(int first, int count, List<FilterCouple> filters, List<OrderCouple> orders, Class<E> clazz) {
        return getSearchBuilder(first, count, filters, orders, clazz).build();
    }

}

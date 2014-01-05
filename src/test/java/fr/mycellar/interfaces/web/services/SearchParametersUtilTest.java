/*
 * Copyright 2014, MyCellar
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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.domain.wine.Wine;
import fr.mycellar.infrastructure.shared.repository.OrderByDirection;
import fr.mycellar.infrastructure.shared.repository.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context-infrastructure-test.xml" })
@Transactional
public class SearchParametersUtilTest {

    private final SearchParametersUtil searchParametersUtil = new SearchParametersUtil();

    @Test
    public void test() {
        int first = 10;
        int count = 20;
        List<FilterCouple> filters = new ArrayList<>();
        String firstFilter = "appellation.region.name";
        String firstFilterValue = "test";
        filters.add(new FilterCouple(firstFilter + "," + firstFilterValue));
        String secondFilter = "appellation.name";
        String secondFilterValue = "test2";
        filters.add(new FilterCouple(secondFilter + "," + secondFilterValue));
        List<OrderCouple> orders = new ArrayList<>();
        String firstOrder = "producer.name";
        String firstOrderValue = "asc";
        orders.add(new OrderCouple(firstOrder + "," + firstOrderValue));
        String secondOrder = "vintage";
        String secondOrderValue = "desc";
        orders.add(new OrderCouple(secondOrder + "," + secondOrderValue));

        SearchParameters expected = new SearchParameters();
        expected.firstResult(first) //
                .maxResults(count) //
                .property(new PropertySelector<>(firstFilter, Wine.class).selected(firstFilterValue)) //
                .property(new PropertySelector<>(secondFilter, Wine.class).selected(secondFilterValue)) //
                .orderBy(OrderByDirection.ASC, firstOrder, Wine.class) //
                .orderBy(OrderByDirection.DESC, secondOrder, Wine.class);

        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Wine.class);

        assertEquals(expected.getFirstResult(), searchParameters.getFirstResult());
        assertEquals(expected.getMaxResults(), searchParameters.getMaxResults());
        assertEquals(expected.getOrders(), searchParameters.getOrders());
        assertEquals(expected.getProperties().size(), searchParameters.getProperties().size());
        int index = 0;
        for (PropertySelector<?, ?> expectedPropertySelector : expected.getProperties()) {
            PropertySelector<?, ?> propertySelector = searchParameters.getProperties().get(index++);
            assertEquals(expectedPropertySelector.getAttributes(), propertySelector.getAttributes());
            assertEquals(expectedPropertySelector.getSearchMode(), propertySelector.getSearchMode());
            assertEquals(expectedPropertySelector.getSelected(), propertySelector.getSelected());
        }
    }

}

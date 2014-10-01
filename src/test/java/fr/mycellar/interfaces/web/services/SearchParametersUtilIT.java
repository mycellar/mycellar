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

import jpasearch.repository.query.OrderByDirection;
import jpasearch.repository.query.Path;
import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.SearchBuilder;
import jpasearch.repository.query.selector.Selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class SearchParametersUtilIT {

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

        SearchBuilder<Wine> expectedBuilder = new SearchBuilder<Wine>();
        expectedBuilder.paginate(first, count) //
                .on(new Path<Wine, Object>(Wine.class, firstFilter)).anywhere(firstFilterValue) //
                .on(new Path<Wine, Object>(Wine.class, secondFilter)).anywhere(secondFilterValue).and() //
                .orderBy(OrderByDirection.ASC, firstOrder, Wine.class) //
                .orderBy(OrderByDirection.DESC, secondOrder, Wine.class);
        SearchParameters<Wine> expected = expectedBuilder.build();

        SearchParameters<Wine> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, Wine.class);

        assertEquals(expected.getFirstResult(), searchParameters.getFirstResult());
        assertEquals(expected.getMaxResults(), searchParameters.getMaxResults());
        assertEquals(expected.getOrders(), searchParameters.getOrders());
        assertEquals(expected.getSelectors().getSelectors().size(), searchParameters.getSelectors().getSelectors().size());
        int index = 0;
        for (Selector<?, ?> expectedSelector : expected.getSelectors().getSelectors()) {
            Selector<?, ?> selector = searchParameters.getSelectors().getSelectors().get(index++);
            assertEquals(expectedSelector.getClass(), selector.getClass());
        }
    }

}

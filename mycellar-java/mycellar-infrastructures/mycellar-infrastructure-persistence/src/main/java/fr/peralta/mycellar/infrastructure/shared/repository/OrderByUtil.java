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
package fr.peralta.mycellar.infrastructure.shared.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import fr.peralta.mycellar.domain.shared.repository.OrderBy;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper to create list of {@link Order} out of {@link OrderBy}s.
 */
@Named
@Singleton
public class OrderByUtil {

    public <E> List<Order> buildJpaOrders(Root<E> root, CriteriaBuilder builder, SearchParameters sp) {
        List<Order> jpaOrders = new ArrayList<>();
        for (OrderBy ob : sp.getOrders()) {
            Path<?> path = JpaUtil.getPath(root, ob.getAttributes());
            jpaOrders.add(ob.isOrderDesc() ? builder.desc(path) : builder.asc(path));
        }
        return jpaOrders;
    }

}
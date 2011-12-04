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
package fr.peralta.mycellar.interfaces.client.web.components.shared.data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.wicket.IClusterable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.util.lang.Args;

import fr.peralta.mycellar.domain.shared.repository.AbstractEntityOrder;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;

/**
 * @author speralta
 */
public class MultipleSortState<E, O extends AbstractEntityOrder<E, O>> implements ISortState,
        IClusterable {

    private static final long serialVersionUID = 201111231012L;

    private final O orders;

    /**
     * @param orders
     */
    public MultipleSortState(O orders) {
        this.orders = orders;
    }

    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState#setPropertySortOrder(String,
     *      org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder)
     */
    @Override
    public void setPropertySortOrder(final String property, final SortOrder order) {
        Args.notNull(property, "property");
        Args.notNull(order, "order");

        E propertyEnum = orders.getFrom(property);

        if (propertyEnum != null) {
            if (order == SortOrder.NONE) {
                orders.remove(propertyEnum);
            } else {
                orders.add(propertyEnum, getOrder(order));
            }
        }
    }

    /**
     * @param order
     * @return
     */
    private OrderWayEnum getOrder(SortOrder order) {
        switch (order) {
        case ASCENDING:
            return OrderWayEnum.ASC;
        case DESCENDING:
            return OrderWayEnum.DESC;
        case NONE:
            return null;
        default:
            throw new IllegalStateException("Unknown " + SortOrder.class.getSimpleName()
                    + " value '" + order + "'.");
        }
    }

    /**
     * @param order
     * @return
     */
    private SortOrder getOrder(OrderWayEnum way) {
        if (way == null) {
            return SortOrder.NONE;
        }
        switch (way) {
        case ASC:
            return SortOrder.ASCENDING;
        case DESC:
            return SortOrder.DESCENDING;
        default:
            throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                    + " value '" + way + "'.");
        }
    }

    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState#getPropertySortOrder(java.lang.String)
     */
    @Override
    public SortOrder getPropertySortOrder(final String property) {
        Args.notNull(property, "property");

        E propertyEnum = orders.getFrom(property);
        if (propertyEnum != null) {
            OrderWayEnum way = orders.get(propertyEnum);
            return getOrder(way);
        } else {
            return SortOrder.NONE;
        }
    }

    /**
     * @return the orders
     */
    public O getOrders() {
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

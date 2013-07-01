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
package fr.mycellar.interfaces.facade.web.domain;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;

/**
 * @author speralta
 */
public abstract class OrderCouple<O extends Enum<O>> {

    private final O order;
    private final OrderWayEnum way;

    /**
     * Default constructor.
     * 
     * @param couple
     */
    public OrderCouple(String couple) {
        order = getValueOf(couple.substring(0, couple.indexOf(",")).toUpperCase());
        way = OrderWayEnum.valueOf(couple.substring(couple.indexOf(",") + 1).toUpperCase());
    }

    /**
     * @return
     */
    protected abstract O getValueOf(String value);

    /**
     * @return the order
     */
    public O getOrder() {
        return order;
    }

    /**
     * @return the way
     */
    public OrderWayEnum getWay() {
        return way;
    }

}

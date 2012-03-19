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
package fr.peralta.mycellar.domain.shared.repository;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author speralta
 */
public abstract class AbstractEntityOrder<E, O extends AbstractEntityOrder<E, O>> implements
        Serializable {

    private static final long serialVersionUID = 201111232156L;

    private final Map<E, OrderWayEnum> orders = new LinkedHashMap<E, OrderWayEnum>();

    /**
     * @param order
     * @param way
     * @return this for chaining
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public O add(E order, OrderWayEnum way) {
        orders.put(order, way);
        return (O) this;
    }

    /**
     * @return
     * @see java.util.Map#entrySet()
     */
    public Set<Entry<E, OrderWayEnum>> entrySet() {
        return orders.entrySet();
    }

    /**
     * @param order
     * @return this for chaining
     * @see java.util.Map#remove(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public O remove(E order) {
        orders.remove(order);
        return (O) this;
    }

    /**
     * @param property
     * @return
     */
    public abstract E getFrom(String property);

    /**
     * @param order
     * @return
     * @see java.util.Map#get(java.lang.Object)
     */
    public OrderWayEnum get(E order) {
        return orders.get(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

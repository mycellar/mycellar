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

import static com.google.common.base.Preconditions.*;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Holder class for search ordering used by the {@link SearchParameters}. When
 * used with {@link NamedQueryUtil}, you pass column name. For other usage, pass
 * the property name.
 */
public class OrderBy implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String columnOrProperty;
    private OrderByDirection direction = OrderByDirection.ASC;

    public OrderBy(String columnOrProperty, OrderByDirection direction) {
        this.columnOrProperty = checkNotNull(columnOrProperty);
        this.direction = checkNotNull(direction);
    }

    public OrderBy(String columnOrProperty) {
        this(columnOrProperty, OrderByDirection.ASC);
    }

    public OrderBy(SingularAttribute<?, ?> attribute, OrderByDirection direction) {
        columnOrProperty = checkNotNull(attribute).getName();
        this.direction = checkNotNull(direction);
    }

    public OrderBy(SingularAttribute<?, ?> attribute) {
        this(attribute, OrderByDirection.ASC);
    }

    public String getColumn() {
        return columnOrProperty;
    }

    public String getProperty() {
        return columnOrProperty;
    }

    public OrderByDirection getDirection() {
        return direction;
    }

    public boolean isOrderDesc() {
        return OrderByDirection.DESC == direction;
    }
}
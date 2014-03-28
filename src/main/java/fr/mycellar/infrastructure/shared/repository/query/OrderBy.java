/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.query;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holder class for search ordering used by the {@link SearchBuilder}. When
 * used with {@link NamedQueryUtil}, you pass column name. For other usage, pass
 * the property name.
 */
public class OrderBy<FROM, TO> implements Serializable {
    private static final long serialVersionUID = 201403271745L;

    private final Path<FROM, TO> path;
    private final OrderByDirection direction;

    public OrderBy(OrderByDirection direction, Path<FROM, TO> path) {
        this.direction = direction;
        this.path = path;
    }

    public OrderBy(OrderByDirection direction, String path, Class<FROM> from) {
        this.direction = checkNotNull(direction);
        this.path = new Path<FROM, TO>(checkNotNull(path), checkNotNull(from));
    }

    public List<Attribute<?, ?>> getAttributes() {
        return path.getAttributes();
    }

    public String getPath() {
        return path.getPath();
    }

    public OrderByDirection getDirection() {
        return direction;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderBy<?, ?> other = (OrderBy<?, ?>) obj;
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
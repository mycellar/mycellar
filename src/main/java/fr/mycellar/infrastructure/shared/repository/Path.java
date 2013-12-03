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
package fr.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holder class for path used by the {@link OrderBy}, {@link PropertySelector},
 * {@link TermSelector} and {@link SearchParameters}.
 */
public class Path implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String path;
    private final Class<?> from;
    private transient List<Attribute<?, ?>> attributes;

    public Path(Attribute<?, ?>... attributes) {
        this(Arrays.asList(attributes));
    }

    public Path(List<Attribute<?, ?>> attributes) {
        JpaUtil.getInstance().verifyPath(checkNotNull(attributes));
        this.attributes = new ArrayList<>(attributes);
        path = MetamodelUtil.getInstance().toPath(attributes);
        from = attributes.get(0).getDeclaringType().getJavaType();
    }

    public Path(String path, Class<?> from) {
        this.path = path;
        this.from = from;
        // to verify path
        getAttributes();
    }

    public List<Attribute<?, ?>> getAttributes() {
        if (attributes == null) {
            attributes = MetamodelUtil.getInstance().toAttributes(path, from);
        }
        return attributes;
    }

    public String getPath() {
        return path;
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
        Path other = (Path) obj;
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
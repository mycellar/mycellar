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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.mycellar.infrastructure.shared.repository.util.JpaUtil;
import fr.mycellar.infrastructure.shared.repository.util.MetamodelUtil;

/**
 * Holder class for path used by the {@link OrderBy}, {@link PropertySelector},
 * {@link TermSelector} and {@link SearchBuilder}.
 */
public class Path<FROM, TO> implements Serializable {
    private static final long serialVersionUID = 201403271745L;

    private final String path;
    private final Class<? super FROM> from;
    private transient List<Attribute<?, ?>> attributes;

    public Path(SingularAttribute<? super FROM, TO> attribute) {
        attributes = new ArrayList<>();
        attributes.add(attribute);
        from = attribute.getDeclaringType().getJavaType();
        path = MetamodelUtil.getInstance().toPath(attributes);
        JpaUtil.getInstance().verifyPath(attributes);
    }

    public Path(PluralAttribute<? super FROM, ?, TO> attribute) {
        attributes = new ArrayList<>();
        attributes.add(attribute);
        from = attribute.getDeclaringType().getJavaType();
        path = MetamodelUtil.getInstance().toPath(attributes);
        JpaUtil.getInstance().verifyPath(attributes);
    }

    private <T> Path(Path<FROM, T> old, SingularAttribute<? super T, TO> attribute) {
        attributes = new ArrayList<>(old.getAttributes());
        attributes.add(attribute);
        from = old.from;
        path = MetamodelUtil.getInstance().toPath(attributes);
        JpaUtil.getInstance().verifyPath(attributes);
    }

    private <T> Path(Path<FROM, T> old, PluralAttribute<? super T, ?, TO> attribute) {
        attributes = new ArrayList<>(old.getAttributes());
        attributes.add(attribute);
        from = old.from;
        path = MetamodelUtil.getInstance().toPath(attributes);
        JpaUtil.getInstance().verifyPath(attributes);
    }

    public Path(String path, Class<FROM> from) {
        this.path = path;
        this.from = from;
        // to verify path
        getAttributes();
    }

    public <T> Path<FROM, T> add(SingularAttribute<? super TO, T> attribute) {
        return new Path<FROM, T>(this, attribute);
    }

    public <T> Path<FROM, T> add(PluralAttribute<? super TO, ?, T> attribute) {
        return new Path<FROM, T>(this, attribute);
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
        result = (prime * result) + ((from == null) ? 0 : from.hashCode());
        result = (prime * result) + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Path<?, ?> rhs = (Path<?, ?>) obj;
        return new EqualsBuilder().append(path, rhs.path).append(from, rhs.from).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
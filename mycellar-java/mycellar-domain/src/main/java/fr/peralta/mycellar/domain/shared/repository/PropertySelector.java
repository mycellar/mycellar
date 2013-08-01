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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used to construct OR predicate for a property value. In other words you can
 * search all entities E having a given property set to one of the values
 * values.
 */
public class PropertySelector<E, F> implements Serializable {

    private static final long serialVersionUID = 201308010800L;

    private final List<Attribute<?, ?>> attributes;
    private final List<F> values = new ArrayList<>();
    private SearchMode searchMode; // for string property only.

    public PropertySelector(Attribute<?, ?>... attributes) {
        this(Arrays.asList(checkNotNull(attributes)));
    }

    public PropertySelector(List<Attribute<?, ?>> attributes) {
        this.attributes = checkNotNull(attributes);
        verifyPath(new ArrayList<>(attributes));
    }

    private void verifyPath(List<Attribute<?, ?>> attributes) {
        Class<?> from = attributes.get(0).getJavaType();
        attributes.remove(0);
        for (Attribute<?, ?> attribute : attributes) {
            if (!attribute.getDeclaringType().getJavaType().isAssignableFrom(from)) {
                throw new IllegalStateException("Wrong path.");
            }
            from = attribute.getJavaType();
        }
    }

    public List<Attribute<?, ?>> getAttributes() {
        return attributes;
    }

    /**
     * Get the possible candidates for property.
     */
    public List<F> getValues() {
        return values;
    }

    public boolean isType(Class<?> type) {
        return attributes.get(attributes.size() - 1).getJavaType().isAssignableFrom(type);
    }

    public SearchMode getSearchMode() {
        return searchMode;
    }

    /**
     * In case, the field's type is a String, you can set a searchMode to use.
     * It is null by default.
     */
    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = searchMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("searchMode", searchMode).append("values", values);
        StringBuilder builder = new StringBuilder("{");
        for (Attribute<?, ?> attribute : attributes) {
            builder.append(
                    new ToStringBuilder(attribute, ToStringStyle.SHORT_PREFIX_STYLE).append("declaringType", attribute.getDeclaringType().getJavaType()).append("javaType", attribute.getJavaType())
                            .append("name", attribute.getName()).build()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1).append("}");
        toStringBuilder.append("attributes", builder.toString());
        return toStringBuilder.build();
    }

}
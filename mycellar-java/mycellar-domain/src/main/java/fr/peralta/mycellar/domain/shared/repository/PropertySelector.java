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

import static com.google.common.collect.Lists.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Used to construct OR predicate for a property value. In other words you can
 * search all entities E having a given property set to one of the selected
 * values.
 */
public class PropertySelector<E, F> implements Serializable {

    /**
     * {@link PropertySelector} builder
     */
    public static <E, F> PropertySelector<E, F> newPropertySelector(SingularAttribute<E, F> field) {
        return new PropertySelector<E, F>(field);
    }

    /**
     * {@link PropertySelector} builder
     */
    public static <E, F> PropertySelector<E, F> newPropertySelector(F selected,
            SingularAttribute<E, ?> field, SingularAttribute<?, ?>... fields) {
        PropertySelector<E, F> ps = new PropertySelector<E, F>(field, fields);
        ps.setSelected(selected);
        return ps;
    }

    /**
     * {@link PropertySelector} builder
     */
    public static <E, F> PropertySelector<E, F> newPropertySelector(SingularAttribute<E, F> field,
            SearchMode searchMode) {
        PropertySelector<E, F> ps = new PropertySelector<E, F>(field);
        ps.setSearchMode(searchMode);
        return ps;
    }

    private static final long serialVersionUID = 1L;

    private final Attribute<?, ?>[] fields;
    private List<F> selected = newArrayList();
    private SearchMode searchMode; // for string property only.

    /**
     * @param field
     *            the property that should match one of the selected value.
     */
    public PropertySelector(Attribute<E, F> field) {
        this.fields = new Attribute<?, ?>[] { field };
    }

    /**
     * @param firstField
     *            the first path to property.
     * @param fields
     *            the property that should match one of the selected value.
     */
    public PropertySelector(Attribute<E, ?> firstField, Attribute<?, ?>... fields) {
        Class<?> from = firstField.getDeclaringType().getJavaType();
        for (Attribute<?, ?> field : fields) {
            if (!field.getDeclaringType().getJavaType().isAssignableFrom(from)) {
                throw new IllegalStateException("Wrong path.");
            }
            from = field.getJavaType();
        }
        this.fields = ArrayUtils.add(fields, 0, firstField);
    }

    public Attribute<?, ?>[] getFields() {
        return fields;
    }

    /**
     * Get the possible candidates for property.
     */
    public List<F> getSelected() {
        return selected;
    }

    /**
     * @param selected
     */
    public void setSelected(F selected) {
        this.selected = Arrays.asList(selected);
    }

    /**
     * Set the possible candidates for property.
     */
    public void setSelected(List<F> selected) {
        this.selected = selected;
    }

    public boolean isNotEmpty() {
        return (selected != null) && !selected.isEmpty();
    }

    public void clearSelected() {
        if (selected != null) {
            selected.clear();
        }
    }

    public boolean isBoolean() {
        return fields[fields.length - 1].getJavaType().isAssignableFrom(Boolean.class);
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

}
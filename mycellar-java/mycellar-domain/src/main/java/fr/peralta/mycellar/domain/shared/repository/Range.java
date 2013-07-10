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

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Range support for {@link Comparable} types.
 */
public class Range<E, D extends Comparable<? super D>> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final SingularAttribute<E, D> field;
    private D from;
    private D to;
    private Boolean includeNull;

    /**
     * Constructs a new {@link Range} with no boundaries and no restrictions on
     * field's nullability.
     * 
     * @param field
     *            the attribute of an existing entity.
     */
    public Range(SingularAttribute<E, D> field) {
        this.field = field;
    }

    /**
     * Constructs a new Range.
     * 
     * @param field
     *            the property's name of an existing entity.
     * @param from
     *            the lower boundary of this range. Null means no lower
     *            boundary.
     * @param to
     *            the upper boundary of this range. Null means no upper
     *            boundary.
     */
    public Range(SingularAttribute<E, D> field, D from, D to) {
        this.field = field;
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a new Range.
     * 
     * @param field
     *            an entity's attribute
     * @param from
     *            the lower boundary of this range. Null means no lower
     *            boundary.
     * @param to
     *            the upper boundary of this range. Null means no upper
     *            boundary.
     * @param includeNull
     *            tells whether null should be filtered out or not.
     */
    public Range(SingularAttribute<E, D> field, D from, D to, Boolean includeNull) {
        this.field = field;
        this.from = from;
        this.to = to;
        this.includeNull = includeNull;
    }

    /**
     * Constructs a new Range by copy.
     */
    public Range(Range<E, D> other) {
        this.field = other.getField();
        this.from = other.getFrom();
        this.to = other.getTo();
        this.includeNull = other.getIncludeNull();
    }

    /**
     * @return the entity's attribute this {@link Range} refers to.
     */
    public SingularAttribute<E, D> getField() {
        return field;
    }

    /**
     * @return the lower range boundary or null for unbound lower range.
     */
    public D getFrom() {
        return from;
    }

    /**
     * Sets the lower range boundary. Accepts null for unbound lower range.
     */
    public void setFrom(D from) {
        this.from = from;
    }

    public boolean isFromSet() {
        return getFrom() != null;
    }

    /**
     * @return the upper range boundary or null for unbound upper range.
     */
    public D getTo() {
        return to;
    }

    /**
     * Sets the upper range boundary. Accepts null for unbound upper range.
     */
    public void setTo(D to) {
        this.to = to;
    }

    public boolean isToSet() {
        return getTo() != null;
    }

    public void setIncludeNull(Boolean includeNull) {
        this.includeNull = includeNull;
    }

    public Boolean getIncludeNull() {
        return includeNull;
    }

    public boolean isIncludeNullSet() {
        return includeNull != null;
    }

    public boolean isBetween() {
        return isFromSet() && isToSet();
    }

    public boolean isSet() {
        return isFromSet() || isToSet() || isIncludeNullSet();
    }

    public boolean isValid() {
        if (isBetween()) {
            return getFrom().compareTo(getTo()) <= 0;
        }

        return true;
    }

    public void resetRange() {
        from = null;
        to = null;
        includeNull = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
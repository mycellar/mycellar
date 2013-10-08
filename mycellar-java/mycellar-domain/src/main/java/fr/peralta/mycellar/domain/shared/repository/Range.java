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

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Range support for {@link Comparable} types.
 */
public class Range<E, D extends Comparable<? super D>> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Path path;
    private D from;
    private D to;
    private Boolean includeNull;

    /**
     * Constructs a new {@link Range} with no boundaries and no restrictions on
     * field's nullability.
     * 
     * @param attributes
     *            the path to the attribute of an existing entity.
     */
    public Range(Attribute<?, ?>... attributes) {
        path = new Path(attributes);
    }

    /**
     * Constructs a new Range.
     * 
     * @param from
     *            the lower boundary of this range. Null means no lower
     *            boundary.
     * @param to
     *            the upper boundary of this range. Null means no upper
     *            boundary.
     * @param attributes
     *            the path to the attribute of an existing entity.
     */
    public Range(D from, D to, Attribute<?, ?>... attributes) {
        this(attributes);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a new Range.
     * 
     * @param from
     *            the lower boundary of this range. Null means no lower
     *            boundary.
     * @param to
     *            the upper boundary of this range. Null means no upper
     *            boundary.
     * @param includeNull
     *            tells whether null should be filtered out or not.
     * @param attributes
     *            the path to the attribute of an existing entity.
     */
    public Range(D from, D to, Boolean includeNull, Attribute<?, ?>... attributes) {
        this(from, to, attributes);
        this.includeNull = includeNull;
    }

    /**
     * Constructs a new Range by copy.
     */
    public Range(Range<E, D> other) {
        this.path = other.path;
        this.from = other.from;
        this.to = other.to;
        this.includeNull = other.includeNull;
    }

    public Path getPath() {
        return path;
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

    public Range<E, D> from(D from) {
        setFrom(from);
        return this;
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

    public Range<E, D> to(D to) {
        setTo(from);
        return this;
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

    public Range<E, D> includeNull(Boolean includeNull) {
        setIncludeNull(includeNull);
        return this;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
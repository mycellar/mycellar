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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TermSelector implements Serializable {
    private static final long serialVersionUID = 201308010800L;

    private final SingularAttribute<?, ?> attribute;
    private final List<String> terms = new ArrayList<>();

    public TermSelector(SingularAttribute<?, ?> attribute) {
        this.attribute = attribute;
    }

    public TermSelector() {
        this(null);
    }

    public SingularAttribute<?, ?> getAttribute() {
        return attribute;
    }

    /**
     * @return the terms
     */
    public List<String> getTerms() {
        return terms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("terms", terms);
        StringBuilder builder = new StringBuilder("{");
        builder.append(
                new ToStringBuilder(attribute, ToStringStyle.SHORT_PREFIX_STYLE).append("declaringType", attribute.getDeclaringType().getJavaType()).append("javaType", attribute.getJavaType())
                        .append("name", attribute.getName()).build()).append(",");
        builder.append("}");
        toStringBuilder.append("attribute", builder.toString());
        return toStringBuilder.build();
    }

}
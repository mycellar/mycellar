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
package fr.peralta.mycellar.interfaces.client.web.behaviors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.util.value.IValueMap;

/**
 * @author speralta
 */
public abstract class DependantAttributeModifier extends Behavior {

    private static final long serialVersionUID = 201205111803L;

    private final String attribute;

    private final String value;

    /**
     * @param attribute
     * @param value
     */
    public DependantAttributeModifier(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onComponentTag(Component component, ComponentTag tag) {
        if (tag.getType() != TagType.CLOSE) {
            if (isEnabled(component)) {
                final IValueMap attributes = tag.getAttributes();
                String[] values = ((String) attributes.get(attribute)).split(" ");
                if (dependencyVerified()) {
                    if (!ArrayUtils.contains(values, value)) {
                        values = ArrayUtils.add(values, value);
                    }
                } else {
                    if (ArrayUtils.contains(values, value)) {
                        values = ArrayUtils.removeElement(values, value);
                    }
                }
                StringBuilder builder = new StringBuilder();
                for (String value : values) {
                    builder.append(value).append(" ");
                }
                attributes.put(attribute, builder.toString());
            }
        }
    }

    /**
     * @return
     */
    protected abstract boolean dependencyVerified();

}

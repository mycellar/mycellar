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
package fr.mycellar.interfaces.facade.web;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;

import com.google.common.base.Splitter;

public class MetamodelUtil {
    private static Map<Class<?>, Class<?>> metamodelCache = newHashMap();

    public static List<Attribute<?, ?>> toAttributes(String path, Class<?> from) {
        try {
            List<Attribute<?, ?>> attributes = newArrayList();
            Class<?> current = from;
            for (String pathItem : Splitter.on(".").split(path)) {
                Class<?> metamodelClass = getCachedClass(current);
                Field field = metamodelClass.getField(pathItem);
                Attribute<?, ?> attribute = (Attribute<?, ?>) field.get(null);
                attributes.add(attribute);
                if (attribute instanceof PluralAttribute) {
                    current = ((PluralAttribute<?, ?, ?>) attribute).getElementType().getJavaType();
                } else {
                    current = attribute.getJavaType();
                }
            }
            return attributes;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Class<?> getCachedClass(Class<?> current) throws ClassNotFoundException {
        if (metamodelCache.containsKey(current)) {
            return metamodelCache.get(current);
        }
        Class<?> metamodelClass = Class.forName(current.getName() + "_");
        metamodelCache.put(current, metamodelClass);
        return metamodelClass;
    }
}
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
package fr.mycellar.infrastructure.shared.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.google.common.base.Splitter;

import fr.mycellar.domain.shared.repository.Path;

@Named
@Singleton
public class MetamodelUtil {

    private final Map<Class<?>, Class<?>> metamodelCache = new HashMap<>();

    public SingularAttribute<?, ?> toAttribute(Path path) {
        return toAttribute(path.getFrom(), path.getPath());
    }

    public SingularAttribute<?, ?> toAttribute(Class<?> from, String path) {
        try {
            Class<?> metamodelClass = getCachedClass(from);
            Field field = metamodelClass.getField(path);
            Attribute<?, ?> attribute = (Attribute<?, ?>) field.get(null);
            return (SingularAttribute<?, ?>) attribute;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Attribute<?, ?>> toAttributes(Path path) {
        return toAttributes(path.getFrom(), path.getPath());
    }

    public List<Attribute<?, ?>> toAttributes(Class<?> from, String path) {
        try {
            List<Attribute<?, ?>> attributes = new ArrayList<>();
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

    public boolean isBoolean(Path path) {
        return isType(Boolean.class, path);
    }

    public boolean isString(Path path) {
        return isType(String.class, path);
    }

    public boolean isNumber(Path path) {
        return isType(Number.class, path);
    }

    public boolean isType(Class<?> type, Path path) {
        List<Attribute<?, ?>> attributes = toAttributes(path);
        return type.isAssignableFrom(attributes.get(attributes.size() - 1).getJavaType());
    }

    private Class<?> getCachedClass(Class<?> current) throws ClassNotFoundException {
        if (metamodelCache.containsKey(current)) {
            return metamodelCache.get(current);
        }
        Class<?> metamodelClass = Class.forName(current.getName() + "_");
        metamodelCache.put(current, metamodelClass);
        return metamodelClass;
    }

}

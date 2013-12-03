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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.context.annotation.Lazy;

import com.google.common.base.Splitter;

@Named
@Singleton
@Lazy(false)
public class MetamodelUtil {
    private static MetamodelUtil instance;

    public static MetamodelUtil getInstance() {
        return instance;
    }

    private final Map<Class<?>, Class<?>> metamodelCache = new HashMap<>();

    public MetamodelUtil() {
        instance = this;
    }

    public SingularAttribute<?, ?> toAttribute(String property, Class<?> from) {
        try {
            Class<?> metamodelClass = getCachedClass(from);
            Field field = metamodelClass.getField(property);
            return (SingularAttribute<?, ?>) field.get(null);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Attribute<?, ?>> toAttributes(String path, Class<?> from) {
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

    public String toPath(List<Attribute<?, ?>> attributes) {
        StringBuilder path = new StringBuilder();
        for (Attribute<?, ?> attribute : attributes) {
            if (path.length() > 0) {
                path.append(".");
            }
            path.append(attribute.getName());
        }
        return path.toString();
    }

    public boolean isBoolean(List<Attribute<?, ?>> attributes) {
        return isType(Boolean.class, attributes);
    }

    public boolean isString(List<Attribute<?, ?>> attributes) {
        return isType(String.class, attributes);
    }

    public boolean isNumber(List<Attribute<?, ?>> attributes) {
        return isType(Number.class, attributes);
    }

    public boolean isType(Class<?> type, List<Attribute<?, ?>> attributes) {
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

    /**
     * Retrieves cascade from metamodel attribute
     * 
     * @return an empty collection if no jpa relation annotation can be found.
     */
    public Collection<CascadeType> getCascades(PluralAttribute<?, ?, ?> attribute) {
        if (attribute.getJavaMember() instanceof AccessibleObject) {
            AccessibleObject accessibleObject = (AccessibleObject) attribute.getJavaMember();
            OneToMany oneToMany = accessibleObject.getAnnotation(OneToMany.class);
            if (oneToMany != null) {
                return Arrays.asList(oneToMany.cascade());
            }
            ManyToMany manyToMany = accessibleObject.getAnnotation(ManyToMany.class);
            if (manyToMany != null) {
                return Arrays.asList(manyToMany.cascade());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves cascade from metamodel attribute on a xToMany relation.
     * 
     * @return an empty collection if no jpa relation annotation can be found.
     */
    public Collection<CascadeType> getCascades(SingularAttribute<?, ?> attribute) {
        if (attribute.getJavaMember() instanceof AccessibleObject) {
            AccessibleObject accessibleObject = (AccessibleObject) attribute.getJavaMember();
            OneToOne oneToOne = accessibleObject.getAnnotation(OneToOne.class);
            if (oneToOne != null) {
                return Arrays.asList(oneToOne.cascade());
            }
            ManyToOne manyToOne = accessibleObject.getAnnotation(ManyToOne.class);
            if (manyToOne != null) {
                return Arrays.asList(manyToOne.cascade());
            }
        }
        return new ArrayList<>();
    }

    public boolean isOrphanRemoval(PluralAttribute<?, ?, ?> attribute) {
        if (attribute.getJavaMember() instanceof AccessibleObject) {
            AccessibleObject accessibleObject = (AccessibleObject) attribute.getJavaMember();
            OneToMany oneToMany = accessibleObject.getAnnotation(OneToMany.class);
            if (oneToMany != null) {
                return oneToMany.orphanRemoval();
            }
        }
        return true;
    }
}

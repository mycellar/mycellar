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
package fr.mycellar.interfaces.facade.web.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.Attribute;

import com.google.common.base.Splitter;

/**
 * @author speralta
 */
public class MetamodelUtil {

    private static Map<Class<?>, Class<?>> metamodelCache = new HashMap<>();

    public static Attribute<?, ?>[] toMetamodelPath(String path, Class<?> from) {
        List<Attribute<?, ?>> attributes = toMetamodelListAttributes(path, from);
        return attributes.toArray(new Attribute<?, ?>[attributes.size()]);
    }

    private static List<Attribute<?, ?>> toMetamodelListAttributes(String path, Class<?> from) {
        try {
            List<Attribute<?, ?>> attributes = new ArrayList<>();
            Class<?> current = from;
            for (String pathItem : Splitter.on(".").split(path)) {
                Class<?> metamodelClass;
                if (metamodelCache.containsKey(current)) {
                    metamodelClass = metamodelCache.get(current);
                } else {
                    metamodelClass = Class.forName(current.getName() + "_");
                    metamodelCache.put(current, metamodelClass);
                }
                Attribute<?, ?> attribute = (Attribute<?, ?>) metamodelClass.getField(pathItem).get(null);
                attributes.add(attribute);
                current = attribute.getJavaType();
            }
            return attributes;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}

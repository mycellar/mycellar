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
package fr.peralta.mycellar.interfaces.client.web.components.shared.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author speralta
 */
public final class SelectEnumUtils {

    private static Map<Class<?>, List<?>> cache = new HashMap<>();

    /**
     * @param enumClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final <E extends Enum<E>> List<E> nullBeforeValues(Class<E> enumClass) {
        if (cache.containsKey(enumClass)) {
            return (List<E>) cache.get(enumClass);
        }
        List<E> list = new ArrayList<>();
        list.add(null);
        list.addAll(EnumUtils.getEnumList(enumClass));
        cache.put(enumClass, list);
        return list;
    }

    /**
     * Refuse instanciation.
     */
    private SelectEnumUtils() {
        throw new IllegalStateException();
    }

}

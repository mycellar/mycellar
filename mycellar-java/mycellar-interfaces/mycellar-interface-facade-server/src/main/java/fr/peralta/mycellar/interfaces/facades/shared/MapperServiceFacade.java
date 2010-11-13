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
package fr.peralta.mycellar.interfaces.facades.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.interfaces.facades.shared.mappers.IMapper;

/**
 * @author speralta
 */
@Service
public class MapperServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(MapperServiceFacade.class);

    private final Map<Class<?>, Map<Class<?>, IMapper<?, ?>>> map = new HashMap<Class<?>, Map<Class<?>, IMapper<?, ?>>>();

    /**
     * @param <FROM>
     * @param <TO>
     * @param from
     * @param toClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <FROM, TO> TO map(FROM from, Class<TO> toClass) {
        if (from == null) {
            return null;
        }
        return getMapper((Class<FROM>) from.getClass(), toClass).map(from);
    }

    /**
     * @param <FROM>
     * @param <TO>
     * @param from
     * @param fromClass
     * @param toClass
     * @return
     */
    public <FROM, TO> TO map(FROM from, Class<FROM> fromClass, Class<TO> toClass) {
        if (from == null) {
            return null;
        }
        return getMapper(fromClass, toClass).map(from);
    }

    /**
     * @param <FROM>
     * @param <TO>
     * @param froms
     * @param toClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <FROM, TO> List<TO> mapList(List<FROM> froms, Class<TO> toClass) {
        if (froms == null) {
            return null;
        }
        List<TO> tos = new ArrayList<TO>(froms.size());
        IMapper<FROM, TO> mapper = null;
        for (FROM from : froms) {
            if (mapper == null) {
                mapper = getMapper((Class<FROM>) from.getClass(), toClass);
            }
            tos.add(mapper.map(from));
        }
        return tos;
    }

    public <FROM, TO> void registerMapper(IMapper<FROM, TO> mapper, Class<FROM> fromClass,
            Class<TO> toClass) {
        Map<Class<?>, IMapper<?, ?>> toMap;
        if (map.containsKey(fromClass)) {
            toMap = map.get(fromClass);
        } else {
            toMap = new HashMap<Class<?>, IMapper<?, ?>>();
            map.put(fromClass, toMap);
        }
        if (toMap.containsKey(toClass)) {
            logger.warn("Mapper already registered for " + fromClass.getName() + " to "
                    + toClass.getName() + ".");
        } else {
            logger.trace("Register mapper for " + fromClass.getName() + " to " + toClass.getName()
                    + ".");
            toMap.put(toClass, mapper);
        }
    }

    @SuppressWarnings("unchecked")
    private <FROM, TO> IMapper<FROM, TO> getMapper(Class<FROM> fromClass, Class<TO> toClass) {
        if (map.containsKey(fromClass)) {
            logger.trace("Mappers found for from class " + fromClass.getName() + ".");
            Map<Class<?>, IMapper<?, ?>> toMap = map.get(fromClass);
            if (toMap.containsKey(toClass)) {
                logger.trace("Mapper found for to class " + toClass.getName() + ".");
                IMapper<FROM, TO> mapper = (IMapper<FROM, TO>) toMap.get(toClass);
                return mapper;
            }
        }
        throw new IllegalStateException("Cannot find mapper for " + fromClass.getName() + " to "
                + toClass.getName());
    }

}

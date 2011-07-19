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
package fr.peralta.mycellar.interfaces.client.web.renderers.shared;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author speralta
 */
@Service
public class RendererServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(RendererServiceFacade.class);

    private final Map<Class<?>, IRenderer<?>> map = new HashMap<Class<?>, IRenderer<?>>();

    @SuppressWarnings("unchecked")
    public <T> String render(T toRender) {
        IRenderer<T> renderer = ((IRenderer<T>) map.get(toRender.getClass()));
        if (renderer != null) {
            return renderer.getLabel(toRender);
        } else {
            throw new RuntimeException("Renderer for " + toRender.getClass() + " not registered.");
        }
    }

    public <T> void registerMapper(IRenderer<T> mapper, Class<T> renderedClass) {
        if (map.containsKey(renderedClass)) {
            logger.warn("Renderer already registered for " + renderedClass.getName() + ".");
        } else {
            logger.trace("Register renderer for " + renderedClass.getName() + ".");
            map.put(renderedClass, mapper);
        }
    }

}

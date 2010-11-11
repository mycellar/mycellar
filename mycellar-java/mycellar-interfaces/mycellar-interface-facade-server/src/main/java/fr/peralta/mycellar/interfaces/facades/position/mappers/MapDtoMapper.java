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
package fr.peralta.mycellar.interfaces.facades.position.mappers;

import fr.peralta.mycellar.interfaces.facades.image.Image;
import fr.peralta.mycellar.interfaces.facades.position.Map;
import fr.peralta.mycellar.interfaces.facades.position.Position;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper;

/**
 * @author speralta
 */
public class MapDtoMapper extends AbstractMapper<fr.peralta.mycellar.domain.position.Map, Map> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map map(fr.peralta.mycellar.domain.position.Map from) {
        Map map = new Map();
        map.setPosition(getMapperServiceFacade().map(from.getPosition(), Position.class));
        map.setImage(getMapperServiceFacade().map(from.getImage(), Image.class));
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.position.Map> getFromClass() {
        return fr.peralta.mycellar.domain.position.Map.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Map> getToClass() {
        return Map.class;
    }

}

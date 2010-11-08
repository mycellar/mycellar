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
package fr.peralta.mycellar.interfaces.facades.wine.mappers;

import fr.peralta.mycellar.interfaces.facades.position.dto.Map;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.IMapper;
import fr.peralta.mycellar.interfaces.facades.wine.dto.Country;

/**
 * @author speralta
 */
public class CountryDtoMapper implements IMapper<fr.peralta.mycellar.domain.wine.Country, Country> {

    private MapperServiceFacade mapperServiceFacade;

    @Override
    public Country map(fr.peralta.mycellar.domain.wine.Country country) {
        Country dto = new fr.peralta.mycellar.interfaces.facades.wine.dto.Country();
        dto.setDescription(country.getDescription());
        dto.setMap(mapperServiceFacade.map(country.getMap(), Map.class));
        dto.setName(country.getName());
        dto.setId(country.getId());
        return dto;
    }

    /**
     * @param mapperServiceFacade
     *            the mapperServiceFacade to set
     */
    public void setMapperServiceFacade(MapperServiceFacade mapperServiceFacade) {
        this.mapperServiceFacade = mapperServiceFacade;
    }

}

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

import org.springframework.stereotype.Service;

import fr.peralta.mycellar.interfaces.facades.position.Map;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper;
import fr.peralta.mycellar.interfaces.facades.wine.Appellation;
import fr.peralta.mycellar.interfaces.facades.wine.Region;

/**
 * @author speralta
 */
@Service
public class AppellationDtoMapper extends
        AbstractMapper<fr.peralta.mycellar.domain.wine.Appellation, Appellation> {

    @Override
    public Appellation map(fr.peralta.mycellar.domain.wine.Appellation country) {
        Appellation dto = new fr.peralta.mycellar.interfaces.facades.wine.Appellation();
        dto.setDescription(country.getDescription());
        dto.setRegion(getMapperServiceFacade().map(country.getRegion(), Region.class));
        dto.setMap(getMapperServiceFacade().map(country.getMap(), Map.class));
        dto.setName(country.getName());
        dto.setId(country.getId());
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.wine.Appellation> getFromClass() {
        return fr.peralta.mycellar.domain.wine.Appellation.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Appellation> getToClass() {
        return Appellation.class;
    }

}

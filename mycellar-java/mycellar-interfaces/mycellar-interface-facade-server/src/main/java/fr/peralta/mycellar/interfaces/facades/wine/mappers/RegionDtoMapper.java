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
import fr.peralta.mycellar.interfaces.facades.wine.Country;
import fr.peralta.mycellar.interfaces.facades.wine.Region;

/**
 * @author speralta
 */
@Service
public class RegionDtoMapper extends AbstractMapper<fr.peralta.mycellar.domain.wine.Region, Region> {

    @Override
    public Region map(fr.peralta.mycellar.domain.wine.Region region) {
        Region dto = new fr.peralta.mycellar.interfaces.facades.wine.Region();
        dto.setDescription(region.getDescription());
        dto.setCountry(getMapperServiceFacade().map(region.getCountry(), Country.class));
        dto.setMap(getMapperServiceFacade().map(region.getMap(), Map.class));
        dto.setName(region.getName());
        dto.setId(region.getId());
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.wine.Region> getFromClass() {
        return fr.peralta.mycellar.domain.wine.Region.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Region> getToClass() {
        return Region.class;
    }

}

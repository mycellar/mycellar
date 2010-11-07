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
package fr.peralta.mycellar.interfaces.facades.wine.internal;

import java.util.ArrayList;
import java.util.List;

import fr.peralta.mycellar.domain.wine.Country;

/**
 * @author speralta
 */
public class CountryMapper {

    // TODO : gérer les Map
    public Country map(fr.peralta.mycellar.interfaces.facades.wine.dto.Country dto) {
        return new Country(dto.getName(), null, dto.getDescription());
    }

    public fr.peralta.mycellar.interfaces.facades.wine.dto.Country map(Country country) {
        fr.peralta.mycellar.interfaces.facades.wine.dto.Country dto = new fr.peralta.mycellar.interfaces.facades.wine.dto.Country();
        dto.setDescription(country.getDescription());
        dto.setMap(null);
        dto.setName(country.getName());
        dto.setId(country.getId());
        return dto;
    }

    /**
     * @param all
     * @return
     */
    public List<fr.peralta.mycellar.interfaces.facades.wine.dto.Country> map(List<Country> countries) {
        List<fr.peralta.mycellar.interfaces.facades.wine.dto.Country> result = new ArrayList<fr.peralta.mycellar.interfaces.facades.wine.dto.Country>(
                countries.size());
        for (Country country : countries) {
            result.add(map(country));
        }
        return result;
    }

}

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
package fr.peralta.mycellar.interfaces.facades.wine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.application.wine.CountryService;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.dto.Country;

/**
 * @author speralta
 */
public class WineServiceFacadeImpl implements WineServiceFacade {

    private CountryService countryService;

    private MapperServiceFacade mapperServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getAllCountries() {
        return mapperServiceFacade.mapList(countryService.getAll(), Country.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Integer> getCountriesWithCounts() {
        Map<Country, Integer> result = new HashMap<Country, Integer>();
        Map<fr.peralta.mycellar.domain.wine.Country, Integer> map = countryService
                .getAllWithCounts();
        for (fr.peralta.mycellar.domain.wine.Country country : map.keySet()) {
            result.put(mapperServiceFacade.map(country, Country.class), map.get(country));
        }
        return result;
    }

    /**
     * @param mapperServiceFacade
     *            the mapperServiceFacade to set
     */
    public void setMapperServiceFacade(MapperServiceFacade mapperServiceFacade) {
        this.mapperServiceFacade = mapperServiceFacade;
    }

    /**
     * @param countryService
     *            the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

}

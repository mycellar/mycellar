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
import fr.peralta.mycellar.interfaces.facades.wine.dto.Country;
import fr.peralta.mycellar.interfaces.facades.wine.internal.CountryMapper;

/**
 * @author speralta
 */
public class WineServiceFacadeImpl implements WineServiceFacade {

    private CountryService countryService;

    private final CountryMapper countryMapper = new CountryMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getAllCountries() {
        return countryMapper.map(countryService.getAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Integer> getCountriesWithCounts() {
        Map<Country, Integer> result = new HashMap<Country, Integer>();
        for (int i = 0; i < 10; i++) {
            Country country = new Country();
            country.setName("TEST" + i);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(country, 23 % a);
        }
        return result;
    }

    /**
     * @param countryService
     *            the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

}

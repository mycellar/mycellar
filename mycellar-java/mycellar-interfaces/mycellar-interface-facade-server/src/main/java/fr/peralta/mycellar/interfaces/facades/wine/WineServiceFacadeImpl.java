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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.wine.AppellationService;
import fr.peralta.mycellar.application.wine.CountryService;
import fr.peralta.mycellar.application.wine.RegionService;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;

/**
 * @author speralta
 */
@Service
public class WineServiceFacadeImpl implements WineServiceFacade {

    private CountryService countryService;

    private RegionService regionService;

    private AppellationService appellationService;

    private MapperServiceFacade mapperServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
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
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Map<Region, Integer> getRegionsWithCounts(Country country) {
        Map<Region, Integer> result = new HashMap<Region, Integer>();
        Map<fr.peralta.mycellar.domain.wine.Region, Integer> map = regionService
                .getAllFromCountryWithCounts(mapperServiceFacade.map(country,
                        fr.peralta.mycellar.domain.wine.Country.class));
        for (fr.peralta.mycellar.domain.wine.Region region : map.keySet()) {
            result.put(mapperServiceFacade.map(region, Region.class), map.get(region));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Appellation, Integer> getAppellationsWithCounts(Region region) {
        Map<Appellation, Integer> result = new HashMap<Appellation, Integer>();
        Map<fr.peralta.mycellar.domain.wine.Appellation, Integer> map = appellationService
                .getAllFromRegionWithCounts(mapperServiceFacade.map(region,
                        fr.peralta.mycellar.domain.wine.Region.class));
        for (fr.peralta.mycellar.domain.wine.Appellation appellation : map.keySet()) {
            result.put(mapperServiceFacade.map(appellation, Appellation.class),
                    map.get(appellation));
        }
        return result;
    }

    /**
     * @param mapperServiceFacade
     *            the mapperServiceFacade to set
     */
    @Autowired
    public void setMapperServiceFacade(MapperServiceFacade mapperServiceFacade) {
        this.mapperServiceFacade = mapperServiceFacade;
    }

    /**
     * @param countryService
     *            the countryService to set
     */
    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @param regionService
     *            the regionService to set
     */
    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * @param appellationService
     *            the appellationService to set
     */
    @Autowired
    public void setAppellationService(AppellationService appellationService) {
        this.appellationService = appellationService;
    }

}

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
package fr.peralta.mycellar.application.wine.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.RegionService;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineRepository;

/**
 * @author speralta
 */
@Service
public class RegionServiceImpl implements RegionService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Region, Integer> getAllFromCountriesWithCounts(Country... countries) {
        return wineRepository.getAllRegionsFromCountriesWithCounts(countries);
    }

    /**
     * @param wineRepository
     *            the wineRepository to set
     */
    @Autowired
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

}

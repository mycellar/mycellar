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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.AppellationService;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineRepository;

/**
 * @author speralta
 */
@Service
public class AppellationServiceImpl implements AppellationService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Appellation, Long> getAllFromRegionsWithCounts(Region... regions) {
        List<Region> regionsInRepository = new ArrayList<Region>();
        for (Region region : regions) {
            if (region.getId() != null) {
                regionsInRepository.add(region);
            }
        }
        return wineRepository.getAllAppellationsFromRegionsWithCounts(regionsInRepository
                .toArray(new Region[regionsInRepository.size()]));
    }

    /**
     * @param wineRepository
     *            the wineRepository to set
     */
    @Autowired
    @Qualifier("hibernate")
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

}

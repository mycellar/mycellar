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

import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineRepository;

/**
 * @author speralta
 */
@Service
public class WineServiceImpl implements WineService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Integer> getAllColorFromProducerWithCounts(Producer producer) {
        Map<WineColorEnum, Integer> result = wineRepository
                .getAllColorFromProducerWithCounts(producer);
        // add missing color
        for (WineColorEnum color : WineColorEnum.values()) {
            if (!result.containsKey(color)) {
                result.put(color, 0);
            }
        }
        return result;
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

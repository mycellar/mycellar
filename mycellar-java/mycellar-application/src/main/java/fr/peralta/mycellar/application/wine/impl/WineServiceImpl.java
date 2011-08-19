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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineRepository;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

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
    public Map<WineTypeEnum, Long> getAllTypeFromProducerWithCounts(Producer producer) {
        Map<WineTypeEnum, Long> result;
        if (producer.getId() != null) {
            result = wineRepository.getAllTypeFromProducerWithCounts(producer);
        } else {
            result = new HashMap<WineTypeEnum, Long>();
        }
        // add missing types
        for (WineTypeEnum color : WineTypeEnum.values()) {
            if (!result.containsKey(color)) {
                result.put(color, 0l);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Long> getAllColorFromProducerAndTypeWithCounts(Producer producer,
            WineTypeEnum type) {
        Map<WineColorEnum, Long> result;
        if (producer.getId() != null) {
            result = wineRepository.getAllColorFromProducerAndTypeWithCounts(producer, type);
        } else {
            result = new HashMap<WineColorEnum, Long>();
        }
        // add missing colors
        for (WineColorEnum color : WineColorEnum.values()) {
            if (!result.containsKey(color)) {
                result.put(color, 0l);
            }
        }
        return result;
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

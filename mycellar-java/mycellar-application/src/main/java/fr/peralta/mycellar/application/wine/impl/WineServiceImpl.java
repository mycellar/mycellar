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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
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
    public Map<WineTypeEnum, Long> getAllTypesFromProducersWithCounts(Producer... producers) {
        Map<WineTypeEnum, Long> result;
        Producer[] persistedProducers = new Producer[producers.length];
        int i = 0;
        for (Producer producer : producers) {
            if (producer.getId() != null) {
                persistedProducers[i++] = producer;
            }
        }
        result = wineRepository.getAllTypesFromProducersWithCounts(persistedProducers);
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
    public Map<WineColorEnum, Long> getAllColorsFromTypesAndProducersWithCounts(
            WineTypeEnum[] types, Producer... producers) {
        Producer[] persistedProducers = new Producer[producers.length];
        int i = 0;
        for (Producer producer : producers) {
            if (producer.getId() != null) {
                persistedProducers[i++] = producer;
            }
        }
        Map<WineColorEnum, Long> result = wineRepository
                .getAllColorsFromTypesAndProducersWithCounts(types, persistedProducers);
        // add missing colors
        for (WineColorEnum color : WineColorEnum.values()) {
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
    public List<Wine> getWinesLike(Wine wine) {
        Appellation appellation = null;
        Region region = null;
        Country country = null;
        if (wine.getAppellation() != null) {
            if (wine.getAppellation().getId() != null) {
                appellation = wine.getAppellation();
            } else if (wine.getAppellation().getRegion() != null) {
                if (wine.getAppellation().getRegion().getId() != null) {
                    region = wine.getAppellation().getRegion();
                } else if ((wine.getAppellation().getRegion().getCountry() != null)
                        && (wine.getAppellation().getRegion().getCountry().getId() != null)) {
                    country = wine.getAppellation().getRegion().getCountry();
                }
            }
        }
        Producer producer = null;
        if ((wine.getProducer() != null) && (wine.getProducer().getId() != null)) {
            producer = wine.getProducer();
        }
        WineTypeEnum type = wine.getType();
        WineColorEnum color = wine.getColor();
        Integer vintage = wine.getVintage();
        return wineRepository.getAllWinesFrom(producer, appellation, region, country, type, color,
                vintage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> getWinesFrom(List<WineTypeEnum> types, List<WineColorEnum> colors,
            List<Country> countries, List<Region> regions, List<Appellation> appellations,
            int first, int count) {
        return wineRepository.getAllWinesFrom(types, colors, countries, regions, appellations,
                first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countWinesFrom(List<Country> countries, List<Region> regions,
            List<Appellation> appellations) {
        return wineRepository.countAllWinesFrom(countries, regions, appellations);
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

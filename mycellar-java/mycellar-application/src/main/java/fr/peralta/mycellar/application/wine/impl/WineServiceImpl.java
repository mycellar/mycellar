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
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineRepository;
import fr.peralta.mycellar.domain.wine.repository.WineSearchForm;

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
    public long countWines(WineSearchForm searchForm) {
        return wineRepository.countWines(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> getWines(WineSearchForm searchForm, WineOrder orders, int first, int count) {
        return wineRepository.getWines(searchForm, orders, first, count);
    }

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
        WineSearchForm wineSearchForm = new WineSearchForm();
        if (wine.getAppellation() != null) {
            if (wine.getAppellation().getId() != null) {
                wineSearchForm.getAppellations().add(wine.getAppellation());
            } else if (wine.getAppellation().getRegion() != null) {
                if (wine.getAppellation().getRegion().getId() != null) {
                    wineSearchForm.getRegions().add(wine.getAppellation().getRegion());
                } else if ((wine.getAppellation().getRegion().getCountry() != null)
                        && (wine.getAppellation().getRegion().getCountry().getId() != null)) {
                    wineSearchForm.getCountries().add(
                            wine.getAppellation().getRegion().getCountry());
                }
            }
        }
        if ((wine.getProducer() != null) && (wine.getProducer().getId() != null)) {
            wineSearchForm.getProducers().add(wine.getProducer());
        }
        if (wine.getType() != null) {
            wineSearchForm.getTypes().add(wine.getType());
        }
        if (wine.getColor() != null) {
            wineSearchForm.getColors().add(wine.getColor());
        }
        if (wine.getVintage() != null) {
            wineSearchForm.getVintages().add(wine.getVintage());
        }
        return wineRepository.getWines(wineSearchForm, new WineOrder(), 1, 10);
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

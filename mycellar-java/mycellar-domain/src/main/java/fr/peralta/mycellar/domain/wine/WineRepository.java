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
package fr.peralta.mycellar.domain.wine;

import java.util.List;
import java.util.Map;

/**
 * @author speralta
 * 
 */
public interface WineRepository {

    /**
     * @param regions
     * @return
     */
    Map<Appellation, Long> getAllAppellationsFromRegionsWithCounts(Region... regions);

    /**
     * @return
     */
    Map<Country, Long> getAllCountriesWithCounts();

    /**
     * @param countries
     * @return
     */
    Map<Region, Long> getAllRegionsFromCountriesWithCounts(Country... countries);

    /**
     * @param term
     * @return
     */
    List<Producer> getAllProducersLike(String term);

    /**
     * @param producers
     * @return
     */
    Map<WineTypeEnum, Long> getAllTypesFromProducersWithCounts(Producer... producers);

    /**
     * @param types
     * @param producers
     * @return
     */
    Map<WineColorEnum, Long> getAllColorsFromTypesAndProducersWithCounts(WineTypeEnum[] types,
            Producer... producers);

    /**
     * @return
     */
    Map<Format, Long> getAllFormatWithCounts();

    /**
     * @param producer
     * @param appellation
     * @param region
     * @param country
     * @param type
     * @param color
     * @param vintage
     * @return
     */
    List<Wine> getAllWinesFrom(Producer producer, Appellation appellation, Region region,
            Country country, WineTypeEnum type, WineColorEnum color, Integer vintage);

    /**
     * @param types
     * @param colors
     * @param countries
     * @param regions
     * @param appellations
     * @param first
     * @param count
     * @return
     */
    List<Wine> getAllWinesFrom(List<WineTypeEnum> types, List<WineColorEnum> colors,
            List<Country> countries, List<Region> regions, List<Appellation> appellations,
            int first, int count);

    /**
     * @param countries
     * @param regions
     * @param appellations
     * @return
     */
    long countAllWinesFrom(List<Country> countries, List<Region> regions,
            List<Appellation> appellations);

}

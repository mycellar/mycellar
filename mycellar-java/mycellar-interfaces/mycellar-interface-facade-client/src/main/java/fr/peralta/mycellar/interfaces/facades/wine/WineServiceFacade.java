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

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @return
     */
    Map<Country, Long> getCountriesWithCounts();

    /**
     * @param countries
     * @return
     */
    Map<Region, Long> getRegionsWithCounts(Country... countries);

    /**
     * @param regions
     * @return
     */
    Map<Appellation, Long> getAppellationsWithCounts(Region... regions);

    /**
     * @param term
     * @return
     */
    List<Producer> getProducersLike(String term);

    /**
     * @param producers
     * @return
     */
    Map<WineTypeEnum, Long> getTypesWithCounts(Producer... producers);

    /**
     * @param types
     * @param producers
     * @return
     */
    Map<WineColorEnum, Long> getColorsWithCounts(WineTypeEnum[] types, Producer... producers);

    /**
     * @param object
     * @param object2
     * @return
     */
    Map<WineColorEnum, Long> getColorsWithCounts(WineTypeEnum type, Producer... producers);

    /**
     * @param producers
     * @return
     */
    Map<WineColorEnum, Long> getColorsWithCounts(Producer... producers);

    /**
     * @return
     */
    Map<Format, Long> getFormatWithCounts();

    /**
     * @param wine
     * @return
     */
    List<Wine> getWinesLike(Wine wine);

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
    List<Wine> getWinesFrom(List<WineTypeEnum> types, List<WineColorEnum> colors,
            List<Country> countries, List<Region> regions, List<Appellation> appellations,
            int first, int count);

    /**
     * @param countries
     * @param regions
     * @param appellations
     * @return
     */
    long countWinesFrom(List<Country> countries, List<Region> regions,
            List<Appellation> appellations);

}

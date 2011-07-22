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
     * @param region
     * @return
     */
    Map<Appellation, Integer> getAllAppellationsFromRegionWithCounts(Region region);

    /**
     * @return
     */
    Map<Country, Integer> getAllCountriesWithCounts();

    /**
     * @param country
     * @return
     */
    Map<Region, Integer> getAllRegionsFromCountryWithCounts(Country country);

    /**
     * @param term
     * @return
     */
    List<Producer> getAllProducerStartingWith(String term);

    /**
     * @param producer
     * @return
     */
    Map<WineTypeEnum, Integer> getAllTypeFromProducerWithCounts(Producer producer);

    /**
     * @param producer
     * @param type
     * @return
     */
    Map<WineColorEnum, Integer> getAllColorFromProducerAndTypeWithCounts(Producer producer,
            WineTypeEnum type);

    /**
     * @return
     */
    Map<Format, Integer> getAllFormatWithCounts();

}

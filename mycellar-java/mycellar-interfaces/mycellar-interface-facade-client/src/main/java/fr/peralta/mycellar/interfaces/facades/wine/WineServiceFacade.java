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
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @return
     */
    Map<Country, Integer> getCountriesWithCounts();

    /**
     * @param country
     * @return
     */
    Map<Region, Integer> getRegionsWithCounts(Country country);

    /**
     * @param region
     * @return
     */
    Map<Appellation, Integer> getAppellationsWithCounts(Region region);

    /**
     * @param term
     * @return
     */
    List<Producer> getProducersStartingWith(String term);

    /**
     * @param producer
     * @return
     */
    Map<WineTypeEnum, Integer> getTypeWithCounts(Producer producer);

    /**
     * @param producer
     * @param type
     * @return
     */
    Map<WineColorEnum, Integer> getColorWithCounts(Producer producer, WineTypeEnum type);

    /**
     * @return
     */
    Map<Format, Integer> getFormatWithCounts();

}

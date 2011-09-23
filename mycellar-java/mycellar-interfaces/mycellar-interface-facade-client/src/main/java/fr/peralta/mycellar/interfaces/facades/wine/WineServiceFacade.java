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
import fr.peralta.mycellar.domain.wine.repository.AppellationCountEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationSearchForm;
import fr.peralta.mycellar.domain.wine.repository.CountryCountEnum;
import fr.peralta.mycellar.domain.wine.repository.CountrySearchForm;
import fr.peralta.mycellar.domain.wine.repository.RegionCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionSearchForm;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineSearchForm;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @param searchForm
     * @return
     */
    long countWines(WineSearchForm searchForm);

    /**
     * @param searchForm
     * @param first
     * @param count
     * @return
     */
    List<Wine> getWines(WineSearchForm searchForm, WineOrder order, int first, int count);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<Country, Long> getCountries(CountrySearchForm searchForm, CountryCountEnum count);

    /**
     * @param countries
     * @return
     */
    Map<Region, Long> getRegions(RegionSearchForm searchForm, RegionCountEnum count);

    /**
     * @param regions
     * @return
     */
    Map<Appellation, Long> getAppellations(AppellationSearchForm searchForm,
            AppellationCountEnum count);

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

}

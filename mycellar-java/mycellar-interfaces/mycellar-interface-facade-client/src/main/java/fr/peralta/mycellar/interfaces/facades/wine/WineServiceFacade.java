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

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @param searchForm
     * @return
     */
    long countWines(SearchForm searchForm);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Wine> getWines(SearchForm searchForm, WineOrder order, int first, int count);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<Country, Long> getCountries(SearchForm searchForm, CountEnum count);

    /**
     * @param countries
     * @return
     */
    Map<Region, Long> getRegions(SearchForm searchForm, CountEnum count);

    /**
     * @param regions
     * @return
     */
    Map<Appellation, Long> getAppellations(SearchForm searchForm, CountEnum count);

    /**
     * @param term
     * @return
     */
    List<Producer> getProducersLike(String term);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum count);

    /**
     * @param searchForm
     * @param wine
     * @return
     */
    Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum wine);

    /**
     * @return
     */
    Map<Format, Long> getFormats(SearchForm searchForm, CountEnum count);

    /**
     * @param producerId
     * @return
     */
    Producer getProducerById(Integer producerId);

}

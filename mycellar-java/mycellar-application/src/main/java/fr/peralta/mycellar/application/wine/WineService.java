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
package fr.peralta.mycellar.application.wine;

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.application.shared.EntitySearchFormService;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;

/**
 * @author speralta
 */
public interface WineService extends EntitySearchFormService<Wine, WineOrderEnum, WineOrder> {

    /**
     * @param producer
     * @param appellation
     * @param type
     * @param color
     * @param name
     * @param vintage
     * @return
     */
    Wine find(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color,
            String name, Integer vintage);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum count);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum count);

    List<Wine> createVintages(Wine wine, int from, int to);

    Wine createVintage(Wine wine, int year);

}

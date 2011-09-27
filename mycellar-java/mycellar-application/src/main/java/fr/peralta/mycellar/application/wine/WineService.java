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

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;

/**
 * @author speralta
 */
public interface WineService {

    long countWines(SearchForm searchForm);

    /**
     * @param searchForm
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Wine> getWines(SearchForm searchForm, WineOrder orders, int first, int count);

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
     * @param wine
     * @return
     */
    List<Wine> getWinesLike(Wine wine);

}

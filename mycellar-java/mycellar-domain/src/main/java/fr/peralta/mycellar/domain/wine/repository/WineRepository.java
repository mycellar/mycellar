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
package fr.peralta.mycellar.domain.wine.repository;

import java.util.Map;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.EntitySearchFormRepository;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
public interface WineRepository extends EntitySearchFormRepository<Wine, WineOrderEnum, WineOrder> {

    /**
     * @param searchForm
     * @param countEnum
     * @return
     */
    Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum countEnum);

    /**
     * @param searchForm
     * @param countEnum
     * @return
     */
    Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum countEnum);

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

}

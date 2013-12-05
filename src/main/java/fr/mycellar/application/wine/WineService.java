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
package fr.mycellar.application.wine;

import java.util.List;

import fr.mycellar.application.shared.SimpleService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.WineColorEnum;
import fr.mycellar.domain.wine.WineTypeEnum;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
public interface WineService extends SimpleService<Wine> {

    Wine find(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage);

    List<Wine> createVintages(Wine wine, int from, int to) throws BusinessException;

    long countWinesLike(String input, SearchParameters searchParameters);

    List<Wine> getWinesLike(String input, SearchParameters searchParameters);

}

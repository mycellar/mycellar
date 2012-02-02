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
package fr.peralta.mycellar.application.stock;

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrder;

/**
 * @author speralta
 */
public interface CellarService {

    /**
     * @param cellarShare
     */
    void saveCellarShare(CellarShare cellarShare);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<Cellar, Long> getAll(SearchForm searchForm, CountEnum count);

    /**
     * @param searchForm
     * @param orders
     * @param count
     * @param first
     * @return
     */
    List<CellarShare> getShares(SearchForm searchForm, CellarShareOrder orders, int first, int count);

    /**
     * @param searchForm
     * @return
     */
    long countShares(SearchForm searchForm);

    /**
     * @param cellarShare
     */
    void deleteCellarShare(CellarShare cellarShare);

}

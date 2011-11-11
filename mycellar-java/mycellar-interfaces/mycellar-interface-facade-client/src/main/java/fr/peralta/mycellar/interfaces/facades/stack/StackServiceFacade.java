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
package fr.peralta.mycellar.interfaces.facades.stack;

import java.util.List;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;

/**
 * @author speralta
 */
public interface StackServiceFacade {

    /**
     * @param exception
     */
    void onException(Exception exception);

    /**
     * @param searchForm
     * @return
     */
    long countStacks(SearchForm searchForm);

    /**
     * 
     */
    void deleteAllStacks();

    /**
     * @param searchForm
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Stack> getStacks(SearchForm searchForm, StackOrder orders, int first, int count);

    /**
     * @param stackId
     * @return
     */
    Stack getStackById(Integer stackId);

}

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
package fr.mycellar.interfaces.facades.stack;

import java.util.List;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stack.Stack;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public interface StackServiceFacade {

    /**
     * @param throwable
     */
    void onThrowable(Throwable throwable);

    /**
     * @param searchParameters
     * @return
     */
    long countStacks(SearchParameters<Stack> searchParameters);

    /**
     * @param stack
     * @throws BusinessException
     */
    void deleteStack(Stack stack) throws BusinessException;

    /**
     * @throws BusinessException
     */
    void deleteAllStacks() throws BusinessException;

    /**
     * @param searchParameters
     * @return
     */
    List<Stack> getStacks(SearchParameters<Stack> searchParameters);

    /**
     * @param stackId
     * @return
     */
    Stack getStackById(Integer stackId);

}

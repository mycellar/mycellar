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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.stack.StackService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.stack.Stack;

/**
 * @author speralta
 */
@Service
public class StackServiceFacadeImpl implements StackServiceFacade {

    private StackService stackService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Stack getStackById(Integer stackId) {
        return stackService.getById(stackId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllStacks() throws BusinessException {
        stackService.deleteAllStacks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteStack(Stack stack) throws BusinessException {
        stackService.delete(stack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countStacks(SearchParameters searchParameters) {
        return stackService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Stack> getStacks(SearchParameters searchParameters) {
        return stackService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void onThrowable(Throwable throwable) {
        stackService.onThrowable(throwable);
    }

    /**
     * @param stackService
     *            the stackService to set
     */
    @Autowired
    public void setStackService(StackService stackService) {
        this.stackService = stackService;
    }

}

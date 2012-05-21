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
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.stack.StackService;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;

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
    public void deleteAllStacks() {
        stackService.deleteAllStacks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countStacks() {
        return stackService.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Stack> getStacks(StackOrder orders, int first, int count) {
        return stackService.getAll(orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void onException(Exception exception) {
        stackService.onException(exception);
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

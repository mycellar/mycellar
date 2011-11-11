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
package fr.peralta.mycellar.application.stack.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.stack.StackService;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;
import fr.peralta.mycellar.domain.stack.repository.StackRepository;

/**
 * @author speralta
 */
@Service
public class StackServiceImpl implements StackService {

    private StackRepository stackRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Stack getStackById(Integer stackId) {
        return stackRepository.getStackById(stackId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countStacks(SearchForm searchForm) {
        return stackRepository.countStacks(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Stack> getStacks(SearchForm searchForm, StackOrder orders, int first, int count) {
        return stackRepository.getStacks(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllStacks() {
        stackRepository.deleteAllStacks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        String stackContent = stringWriter.toString();
        Stack stack = stackRepository.getByHashCode(stackContent.hashCode());
        if (stack == null) {
            stack = new Stack();
            stack.setStack(stackContent);
        } else {
            stack.increaseCount();
        }
        stackRepository.save(stack);
    }

    /**
     * @param stackRepository
     *            the stackRepository to set
     */
    @Autowired
    public void setStackRepository(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

}

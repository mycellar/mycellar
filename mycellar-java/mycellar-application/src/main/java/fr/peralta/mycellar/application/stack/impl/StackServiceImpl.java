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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.stack.StackService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.Stack_;
import fr.peralta.mycellar.domain.stack.repository.StackRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class StackServiceImpl extends AbstractSimpleService<Stack, StackRepository> implements StackService {

    private StackRepository stackRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Stack entity) throws BusinessException {

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
    public void onThrowable(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        String stackContent = stringWriter.toString();
        Stack stack = stackRepository.findUniqueOrNone(new SearchParameters().property(PropertySelector.newPropertySelector(stackContent.hashCode(), Stack_.hashCode)));
        if (stack == null) {
            stack = new Stack();
            stack.setStack(stackContent);
        } else {
            stack.increaseCount();
        }
        stackRepository.save(stack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StackRepository getRepository() {
        return stackRepository;
    }

    /**
     * @param stackRepository
     *            the stackRepository to set
     */
    @Inject
    public void setStackRepository(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

}

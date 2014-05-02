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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.SearchParameters;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import fr.mycellar.application.stack.StackService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stack.Stack;

/**
 * @author speralta
 */
@Named("stackServiceFacade")
@Singleton
public class StackServiceFacadeImpl implements StackServiceFacade {

    private StackService stackService;

    private PlatformTransactionManager transactionManager;

    @Override
    public Stack getStackById(Integer stackId) {
        return stackService.getById(stackId);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAllStacks() throws BusinessException {
        stackService.deleteAllStacks();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteStack(Stack stack) throws BusinessException {
        stackService.delete(stack);
    }

    @Override
    @Transactional(readOnly = true)
    public long countStacks(SearchParameters<Stack> search) {
        return stackService.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stack> getStacks(SearchParameters<Stack> search) {
        return stackService.find(search);
    }

    @Override
    public synchronized void onThrowable(final Throwable throwable) {
        // The transaction must be inside the lock. So we must use a transaction
        // template and not the Transactional annotation.
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.setReadOnly(false);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                stackService.onThrowable(throwable);
                return null;
            }
        });
    }

    // METHODS BEAN

    @Inject
    public void setStackService(StackService stackService) {
        this.stackService = stackService;
    }

    @Inject
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}

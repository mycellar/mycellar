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
package fr.mycellar.infrastructure.stack.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.domain.stack.Stack;
import fr.mycellar.domain.stack.repository.StackRepository;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaStackRepository extends JpaSimpleRepository<Stack> implements StackRepository {

    /**
     * Default constructor.
     */
    public JpaStackRepository() {
        super(Stack.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllStacks() {
        getEntityManager().createQuery("DELETE " + Stack.class.getSimpleName()).executeUpdate();
    }

}
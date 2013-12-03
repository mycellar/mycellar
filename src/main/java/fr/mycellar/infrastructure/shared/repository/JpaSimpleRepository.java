/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
public abstract class JpaSimpleRepository<E extends IdentifiedEntity> extends JpaGenericRepository<E, Integer> implements SimpleRepository<E> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public JpaSimpleRepository(Class<E> type) {
        super(type);
    }

    @Override
    public final E getById(Integer id) {
        return getEntityManager().find(getType(), id);
    }

    @Override
    public final E save(E entity) {
        E result = getEntityManager().merge(entity);
        logger.debug("Entity merged {}.", result);
        postSave(entity);
        return result;
    }

    @Override
    public final void delete(E entity) {
        E toRemove = getEntityManager().find(getType(), entity.getId());
        getEntityManager().remove(toRemove);
        logger.debug("Entity removed {}.", toRemove);
        postDelete(entity);
    }

    // Could be overriden

    protected void postSave(E entity) {

    }

    protected void postDelete(E entity) {

    }
}

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
package fr.peralta.mycellar.infrastructure.shared.repository;

import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.SimpleRepository;

/**
 * @author speralta
 */
public abstract class JpaSimpleRepository<E extends IdentifiedEntity> extends
        JpaGenericRepository<E, Integer> implements SimpleRepository<E> {

    private static final Logger logger = LoggerFactory.getLogger(JpaSimpleRepository.class);

    /**
     * @param type
     * @param indexedAttributes
     */
    public JpaSimpleRepository(Class<E> type, SingularAttribute<?, ?>... indexedAttributes) {
        super(type, indexedAttributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final E getById(Integer id) {
        return getEntityManager().find(getType(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final E save(E entity) {
        E result = getEntityManager().merge(entity);
        logger.debug("Entity merged {}.", result);
        postSave(entity);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void delete(E entity) {
        E toRemove = getEntityManager().find(getType(), entity.getId());
        getEntityManager().remove(toRemove);
        logger.debug("Entity removed {}.", toRemove);
        postDelete(entity);
    }

    // Could be overriden

    /**
     * @param entity
     */
    protected void postSave(E entity) {

    }

    /**
     * @param entity
     */
    protected void postDelete(E entity) {

    }
}

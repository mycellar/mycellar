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
package fr.mycellar.application.shared;

import jpasearch.repository.SimpleRepository;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
public abstract class AbstractSimpleService<E extends IdentifiedEntity, R extends SimpleRepository<E>> extends AbstractGenericService<R, E, Integer> implements SimpleService<E> {

    @Override
    public final E getById(Integer id) {
        return getRepository().getById(id);
    }

    @Override
    public final void delete(E entity) throws BusinessException {
        validateDelete(entity);
        getRepository().delete(entity);
    }

    protected void validateDelete(E entity) throws BusinessException {

    }

    @Override
    public final E save(E entity) throws BusinessException {
        validate(entity);
        return saveInternal(entity);
    }

    protected E saveInternal(E entity) throws BusinessException {
        return getRepository().save(entity);
    }

}
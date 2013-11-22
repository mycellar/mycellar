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

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
public interface SimpleService<E extends IdentifiedEntity> extends GenericService<E, Integer> {

    /**
     * @param entity
     */
    void delete(E entity) throws BusinessException;

    /**
     * @param id
     * @return
     */
    E getById(Integer id);

    /**
     * @param entity
     * @return
     */
    E save(E entity) throws BusinessException;

    /**
     * @param entity
     * @throws BusinessException
     */
    void validate(E entity) throws BusinessException;

}
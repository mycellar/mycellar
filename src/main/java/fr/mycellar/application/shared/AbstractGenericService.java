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

import java.io.Serializable;
import java.util.List;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.GenericRepository;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public abstract class AbstractGenericService<R extends GenericRepository<E, PK>, E extends Identifiable<PK>, PK extends Serializable> implements GenericService<E, PK> {

    @Override
    public long count(SearchParameters<E> searchParameters) {
        return getRepository().findCount(searchParameters);
    }

    @Override
    public List<E> find(SearchParameters<E> searchParameters) {
        return getRepository().find(searchParameters);
    }

    @Override
    public E findUnique(SearchParameters<E> searchParameters) {
        return getRepository().findUnique(searchParameters);
    }

    @Override
    public E findUniqueOrNone(SearchParameters<E> searchParameters) {
        return getRepository().findUniqueOrNone(searchParameters);
    }

    protected abstract R getRepository();

}

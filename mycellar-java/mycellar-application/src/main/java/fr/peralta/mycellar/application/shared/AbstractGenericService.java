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
package fr.peralta.mycellar.application.shared;

import java.io.Serializable;
import java.util.List;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.GenericRepository;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * @author speralta
 */
public abstract class AbstractGenericService<R extends GenericRepository<E, PK>, E extends Identifiable<PK>, PK extends Serializable>
        implements GenericService<E, PK> {

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(SearchParameters searchParameters) {
        return getRepository().findCount(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> find(SearchParameters searchParameters) {
        return getRepository().find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findUnique(SearchParameters searchParameters) {
        return getRepository().findUnique(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findUniqueOrNone(SearchParameters searchParameters) {
        return getRepository().findUniqueOrNone(searchParameters);
    }

    protected abstract R getRepository();

}

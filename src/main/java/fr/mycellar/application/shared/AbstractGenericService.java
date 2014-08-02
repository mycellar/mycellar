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

import jpasearch.domain.Identifiable;
import jpasearch.repository.GenericRepository;
import jpasearch.repository.query.ResultParameters;
import jpasearch.repository.query.SearchParameters;

/**
 * @author speralta
 */
public abstract class AbstractGenericService<R extends GenericRepository<E, PK>, E extends Identifiable<PK>, PK extends Serializable> implements GenericService<E, PK> {

    @Override
    public long count(SearchParameters<E> search) {
        return getRepository().findCount(search);
    }

    @Override
    public long countProperty(SearchParameters<E> search, ResultParameters<E, ?> resultParameters) {
        return getRepository().findPropertyCount(search, resultParameters);
    }

    @Override
    public List<E> find(SearchParameters<E> search) {
        return getRepository().find(search);
    }

    @Override
    public <X> List<X> findProperty(SearchParameters<E> search, ResultParameters<E, X> resultParameters) {
        return getRepository().findProperty(search, resultParameters);
    }

    @Override
    public E findUnique(SearchParameters<E> search) {
        return getRepository().findUnique(search);
    }

    @Override
    public E findUniqueOrNone(SearchParameters<E> search) {
        return getRepository().findUniqueOrNone(search);
    }

    protected abstract R getRepository();

}

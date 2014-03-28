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
package fr.mycellar.infrastructure.shared.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.metamodel.Attribute;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 * 
 * @param <E>
 * @param <PK>
 */
public interface GenericRepository<E extends Identifiable<PK>, PK extends Serializable> {

    /**
     * Find and load a list of E instance.
     * 
     * @param search
     *            carries additional search information
     * @return the entities matching the search.
     */
    List<E> find(SearchParameters<E> search);

    /**
     * Count the number of E instances.
     * 
     * @param search
     *            carries additional search information
     * @return the number of entities matching the search.
     */
    long findCount(SearchParameters<E> search);

    /**
     * Find a list of E property.
     * 
     * @param propertyType
     *            type of the property
     * @param entity
     *            a sample entity whose non-null properties may be used as
     *            search hints
     * @param search
     *            carries additional search information
     * @param attributes
     *            the list of attributes to the property
     * @return the entities property matching the search.
     */
    <T> List<T> findProperty(Class<T> propertyType, SearchParameters<E> search, Attribute<?, ?>... attributes);

    /**
     * Count the number of E instances.
     * 
     * @param entity
     *            a sample entity whose non-null properties may be used as
     *            search hint
     * @param search
     *            carries additional search information
     * @param attributes
     *            the list of attributes to the property
     * @return the number of entities matching the search.
     */
    long findPropertyCount(SearchParameters<E> search, Attribute<?, ?>... attributes);

    /**
     * @param search
     * @return
     */
    E findUnique(SearchParameters<E> search);

    /**
     * We request at most 2, if there's more than one then we throw a
     * {@link NonUniqueResultException}
     * 
     * @param search
     * @return
     * @throws NonUniqueResultException
     */
    E findUniqueOrNone(SearchParameters<E> search);

}
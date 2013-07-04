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
package fr.peralta.mycellar.domain.shared.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import fr.peralta.mycellar.domain.shared.Identifiable;

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
     * @param searchParameters
     *            carries additional search information
     * @return the entities matching the search.
     */
    List<E> find(SearchParameters searchParameters);

    /**
     * Count the number of E instances.
     * 
     * @param searchParameters
     *            carries additional search information
     * @return the number of entities matching the search.
     */
    long findCount(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    E findUnique(SearchParameters searchParameters);

    /**
     * We request at most 2, if there's more than one then we throw a
     * {@link NonUniqueResultException}
     * 
     * @param searchParameters
     * @return
     * @throws NonUniqueResultException
     */
    E findUniqueOrNone(SearchParameters searchParameters);

}
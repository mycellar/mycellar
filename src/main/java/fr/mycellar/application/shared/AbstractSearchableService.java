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

import java.util.List;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.infrastructure.shared.repository.SimpleRepository;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public abstract class AbstractSearchableService<E extends IdentifiedEntity, R extends SimpleRepository<E>> extends AbstractSimpleService<E, R> implements SearchableService<E> {

    @Override
    public final long countAllLike(String term, SearchParameters<E> search) {
        return getRepository().findCount(addTermToSearchParameters(term, search));
    }

    @Override
    public final List<E> getAllLike(String term, SearchParameters<E> search) {
        return getRepository().find(addTermToSearchParameters(term, search));
    }

    protected abstract SearchParameters<E> addTermToSearchParameters(String term, SearchParameters<E> search);

}
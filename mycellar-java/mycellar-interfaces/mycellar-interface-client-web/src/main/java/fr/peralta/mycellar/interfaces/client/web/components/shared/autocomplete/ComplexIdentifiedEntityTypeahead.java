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
package fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete;

import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;

/**
 * @author speralta
 * 
 * @param <E>
 */
public abstract class ComplexIdentifiedEntityTypeahead<E extends IdentifiedEntity> extends
        ComplexTypeahead<E> implements TypeaheadIdentifiedEntityProvider<E> {

    private static final long serialVersionUID = 201108082348L;

    private final FilterEnum[] filters;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param filters
     */
    public ComplexIdentifiedEntityTypeahead(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, FilterEnum... filters) {
        super(id, label, searchFormModel);
        this.filters = filters;
    }

    /**
     * @param id
     * @return
     */
    @Override
    protected AbstractTypeaheadComponent<E> createAutocomplete(String id) {
        return new IdentifiedEntityTypeaheadComponent<E>(id, this);
    }

    /**
     * @return the filters
     */
    public FilterEnum[] getFilters() {
        return filters;
    }

}

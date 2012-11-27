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
package fr.peralta.mycellar.interfaces.client.web.components.shared.data;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;

import fr.peralta.mycellar.domain.shared.repository.AbstractEntityOrder;

/**
 * @author speralta
 */
public abstract class MultipleSortableDataProvider<T, E, O extends AbstractEntityOrder<E, O>>
        implements ISortableDataProvider<T, E> {

    private static final long serialVersionUID = 201111231029L;

    private final MultipleSortState<E, O> state;

    /**
     * @param orders
     */
    public MultipleSortableDataProvider(O orders) {
        state = new MultipleSortState<E, O>(orders);
    }

    /**
     * @see ISortableDataProvider#getSortState()
     */
    @Override
    public final ISortState<E> getSortState() {
        return state;
    }

    /**
     * @return the state
     */
    public MultipleSortState<E, O> getState() {
        return state;
    }

    /**
     * Sets the current sort state
     * 
     * @param property
     *            sort property
     * @param order
     *            sort order
     */
    public void setSort(final E property, final SortOrder order) {
        state.setPropertySortOrder(property, order);
    }

    /**
     * @see ISortableDataProvider#detach()
     */
    @Override
    public void detach() {
    }

}

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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @author speralta
 */
public class AdvancedAjaxFallbackHeadersToolbar<S> extends AjaxFallbackHeadersToolbar<S> {

    private static final long serialVersionUID = 201111280845L;

    /**
     * @param table
     * @param stateLocator
     */
    public AdvancedAjaxFallbackHeadersToolbar(DataTable<?, S> table,
            ISortStateLocator<S> stateLocator) {
        super(table, stateLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected WebMarkupContainer newSortableHeader(String borderId, S property,
            ISortStateLocator<S> locator) {
        return new AdvancedAjaxFallbackOrderByBorder<S>(borderId, property, locator,
                getAjaxCallListener()) {
            private static final long serialVersionUID = 201111280847L;

            @Override
            protected void onAjaxClick(final AjaxRequestTarget target) {
                target.add(getTable());
            }

            @Override
            protected void onSortChanged() {
                super.onSortChanged();
                getTable().setCurrentPage(0);
            }
        };
    }

}

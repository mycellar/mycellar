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

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @author speralta
 */
public class AdvancedTable<T, S> extends DataTable<T, S> {

    private static final long serialVersionUID = 201111221939L;

    /**
     * @param id
     * @param columns
     * @param dataProvider
     * @param rowsPerPage
     */
    public AdvancedTable(String id, List<IColumn<T, S>> columns,
            ISortableDataProvider<T, S> dataProvider, int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        addTopToolbar(new ImageAjaxNavigationToolbar(this));
        addTopToolbar(new AdvancedAjaxFallbackHeadersToolbar<S>(this, dataProvider));
        ((WebMarkupContainer) get("body")).add(new NoRecordsRow(this));
        addBottomToolbar(new AdvancedAjaxFallbackHeadersToolbar<S>(this, dataProvider));
        addBottomToolbar(new ImageAjaxNavigationToolbar(this));
    }

}

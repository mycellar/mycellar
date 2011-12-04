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
public class AdvancedTable<T> extends DataTable<T> {

    private static final long serialVersionUID = 201111221939L;

    /**
     * @param id
     * @param columns
     * @param colSizes
     * @param dataProvider
     * @param rowsPerPage
     */
    public AdvancedTable(String id, List<IColumn<T>> columns, List<Integer> colSizes,
            ISortableDataProvider<T> dataProvider, int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        setOutputMarkupId(true);
        setVersioned(false);
        add(new ColsRepeater("cols", colSizes));
        addTopToolbar(new ImageAjaxNavigationToolbar(this));
        addTopToolbar(new AdvancedAjaxFallbackHeadersToolbar(this, dataProvider));
        ((WebMarkupContainer) get("body")).add(new NoRecordsRow(this));
        addBottomToolbar(new AdvancedAjaxFallbackHeadersToolbar(this, dataProvider));
        addBottomToolbar(new ImageAjaxNavigationToolbar(this));
    }

}

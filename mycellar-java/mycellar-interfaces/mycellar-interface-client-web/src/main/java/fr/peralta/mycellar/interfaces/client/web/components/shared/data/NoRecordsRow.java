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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author speralta
 */
public class NoRecordsRow extends Panel {

    private static final long serialVersionUID = 201111291917L;

    private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel(
            "datatable.no-records-found");

    private final AdvancedTable<?, ?> table;

    /**
     * Constructor
     * 
     * @param table
     *            data table this toolbar will be attached to
     */
    public NoRecordsRow(final AdvancedTable<?, ?> table) {
        this(table, DEFAULT_MESSAGE_MODEL);
    }

    /**
     * @param table
     *            data table this toolbar will be attached to
     * @param messageModel
     *            model that will be used to display the "no records found"
     *            message
     */
    public NoRecordsRow(final AdvancedTable<?, ?> table, final IModel<String> messageModel) {
        super("noRows");
        this.table = table;

        WebMarkupContainer td = new WebMarkupContainer("td");
        add(td);

        td.add(AttributeModifier.replace("colspan", String.valueOf(table.getColumns().size())));
        td.add(new Label("msg", messageModel));
    }

    /**
     * Only shows this toolbar when there are no rows
     * 
     * @see org.apache.wicket.Component#isVisible()
     */
    @Override
    public boolean isVisible() {
        return table.getRowCount() == 0;
    }
}

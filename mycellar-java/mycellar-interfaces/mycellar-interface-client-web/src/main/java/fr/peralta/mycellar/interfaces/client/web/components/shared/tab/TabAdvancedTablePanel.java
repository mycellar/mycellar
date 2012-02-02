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
package fr.peralta.mycellar.interfaces.client.web.components.shared.tab;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 */
public abstract class TabAdvancedTablePanel<O> extends Panel {

    private static final long serialVersionUID = 201201241756L;

    /**
     * @param id
     * @param model
     */
    public TabAdvancedTablePanel(String id, IModel<?> model) {
        super(id, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(createAdvancedTable("table"));
    }

    /**
     * @param searchForm
     * @return
     */
    protected abstract AdvancedTable<O> createAdvancedTable(String tableId);

}

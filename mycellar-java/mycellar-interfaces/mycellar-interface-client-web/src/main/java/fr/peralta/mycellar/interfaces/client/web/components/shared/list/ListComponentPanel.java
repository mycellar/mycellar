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
package fr.peralta.mycellar.interfaces.client.web.components.shared.list;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author speralta
 */
public class ListComponentPanel<O> extends Panel {

    private static final long serialVersionUID = 201108041152L;

    private static final String LIST_COMPONENT_ID = "list";

    /**
     * @param id
     * @param list
     */
    public ListComponentPanel(String id, IModel<? extends List<? extends ListData<O>>> list) {
        super(id);
        add(new ListComponent<O>(LIST_COMPONENT_ID, list));
    }

}

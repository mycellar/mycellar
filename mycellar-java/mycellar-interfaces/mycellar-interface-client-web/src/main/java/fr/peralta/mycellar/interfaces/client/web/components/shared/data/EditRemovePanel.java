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

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;

/**
 * @author speralta
 */
public class EditRemovePanel extends Panel {

    private static final long serialVersionUID = 201201251241L;

    /**
     * @param id
     * @param model
     */
    public EditRemovePanel(String id, IModel<?> model) {
        this(id, model, true, true);
    }

    /**
     * @param id
     * @param model
     * @param edit
     * @param delete
     */
    public EditRemovePanel(String id, IModel<?> model, boolean edit, boolean delete) {
        super(id, model);
        add(new ActionLink("editLink", Action.SELECT).setVisibilityAllowed(edit));
        add(new ActionLink("removeLink", Action.DELETE).setVisibilityAllowed(delete));
    }

}

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
package fr.peralta.mycellar.interfaces.client.web.components.shared.form;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;

/**
 * @author speralta
 */
public class ObjectForm<O> extends Form<O> {

    private static final long serialVersionUID = 201011071626L;

    public static final String EDIT_PANEL_COMPONENT_ID = "newObject";

    /**
     * @param id
     * @param newObject
     */
    public ObjectForm(String id, O newObject) {
        super(id, new CompoundPropertyModel<O>(newObject));
        add(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID));
        add(new Button("saveObject"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        send(findParent(Panel.class), Broadcast.EXACT, Action.SAVE);
    }
}

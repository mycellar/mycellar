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

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ChildAwareCompoundPropertyModel;

/**
 * @author speralta
 */
public class ObjectForm<O> extends Form<O> {

    private static final long serialVersionUID = 201107252130L;

    public static final String EDIT_PANEL_COMPONENT_ID = "newObject";

    /**
     * @param id
     */
    public ObjectForm(String id) {
        super(id);
        add(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID).setOutputMarkupId(true));
        add(new Button("saveObject"));
        add(new CancelButton("cancelObject"));
    }

    /**
     * @param id
     * @param newObject
     */
    public ObjectForm(String id, O newObject) {
        super(id, new ChildAwareCompoundPropertyModel<O>(newObject));
        add(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID).setOutputMarkupId(true));
        add(new Button("saveObject"));
        add(new CancelButton("cancelObject"));
    }

    public ObjectForm<O> setNewObject(O newObject) {
        setModel(new ChildAwareCompoundPropertyModel<O>(newObject));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        send(getParent(), Broadcast.BUBBLE, Action.SAVE);
    }

}

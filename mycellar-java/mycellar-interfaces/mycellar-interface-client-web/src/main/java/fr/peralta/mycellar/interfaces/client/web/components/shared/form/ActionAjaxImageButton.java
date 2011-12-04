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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;

/**
 * @author speralta
 * 
 */
public class ActionAjaxImageButton extends AjaxImageButton {

    private static final long serialVersionUID = 201111181935L;
    private static final Logger logger = LoggerFactory.getLogger(ActionAjaxImageButton.class);

    private final IModel<Action> actionModel;

    /**
     * @param id
     * @param action
     * @param resourceReference
     */
    public ActionAjaxImageButton(String id, Action action, ResourceReference resourceReference) {
        super(id, resourceReference);
        setDefaultFormProcessing(false);
        actionModel = new Model<Action>(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        send(getParent(), Broadcast.BUBBLE, actionModel.getObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        logger.warn("ajax error {} : {}", new Object[] { target, form });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachModels() {
        actionModel.detach();
        super.detachModels();
    }

}

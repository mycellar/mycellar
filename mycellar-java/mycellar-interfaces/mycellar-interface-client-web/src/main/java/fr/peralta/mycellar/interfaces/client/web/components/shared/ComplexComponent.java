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
package fr.peralta.mycellar.interfaces.client.web.components.shared;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ComplexComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 */
public abstract class ComplexComponent<O> extends SimpleComponent<O> {

    private static final long serialVersionUID = 201107281247L;

    private static final String ADD_COMPONENT_ID = "add";
    private static final String CREATE_FORM_COMPONENT_ID = "createForm";

    /**
     * @param id
     * @param label
     */
    public ComplexComponent(String id, IModel<String> label) {
        super(id, label);
        FormComponentFeedbackBorder container = (FormComponentFeedbackBorder) get(CONTAINER_COMPONENT_ID);
        container.addToBorder(new ActionLink(ADD_COMPONENT_ID, Action.ADD));
        container.add(new ObjectForm<O>(CREATE_FORM_COMPONENT_ID).setVisibilityAllowed(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormComponentFeedbackBorder createBorder(String id, IModel<String> label) {
        return new ComplexComponentFeedbackBorder(CONTAINER_COMPONENT_ID, label, id, true,
                getFilteredIdsForFeedback());
    }

    /**
     * @param id
     * @return
     */
    protected abstract Component createComponentForCreation(String id);

    /**
     * @return
     */
    protected abstract O createObject();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalOnConfigure() {
        get(
                CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                        + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).setVisibilityAllowed(
                !isValued()
                        && !get(
                                CONTAINER_COMPONENT_ID + PATH_SEPARATOR
                                        + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR
                                        + CREATE_FORM_COMPONENT_ID).isVisibilityAllowed());
        get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + ADD_COMPONENT_ID).setVisibilityAllowed(
                !isValued()
                        && !get(
                                CONTAINER_COMPONENT_ID + PATH_SEPARATOR
                                        + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR
                                        + CREATE_FORM_COMPONENT_ID).isVisibilityAllowed());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onSave(IEventSource source, Action action) {
        ObjectForm<O> objectForm = ((ObjectForm<O>) get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR
                + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR + CREATE_FORM_COMPONENT_ID));
        objectForm.setVisibilityAllowed(false);
        markAsValued(((ObjectForm<O>) get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR
                + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR + CREATE_FORM_COMPONENT_ID))
                .getModelObject());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCancel(IEventSource source, Action action) {
        get(
                CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                        + PATH_SEPARATOR + CREATE_FORM_COMPONENT_ID).setVisibilityAllowed(false);
        markAsNonValued();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onAdd(IEventSource source, Action action) {
        ((ObjectForm<O>) get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                + PATH_SEPARATOR + CREATE_FORM_COMPONENT_ID)).setNewObject(createObject())
                .replace(createComponentForCreation(ObjectForm.EDIT_PANEL_COMPONENT_ID))
                .setVisibilityAllowed(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final O createDefaultObject() {
        return createObject();
    }

}

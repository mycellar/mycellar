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
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ComplexComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 */
public abstract class ComplexComponent<O, C extends Component> extends SimpleComponent<O, C> {

    private static final long serialVersionUID = 201107281247L;

    private static final String ADD_COMPONENT_ID = "add";
    private static final String CREATE_FORM_COMPONENT_ID = "createForm";

    private final ActionLink addLink;
    private ObjectForm<O> createForm;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public ComplexComponent(String id, IModel<String> label, IModel<SearchForm> searchFormModel) {
        super(id, label, searchFormModel);
        getContainer().addToBorder(addLink = new ActionLink(ADD_COMPONENT_ID, Action.ADD));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        getContainer().add(
                (createForm = createForm(CREATE_FORM_COMPONENT_ID, getSearchFormModel()))
                        .hideForm());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormComponentFeedbackBorder createBorder(String id, String forId, IModel<String> label) {
        return new ComplexComponentFeedbackBorder(id, label, forId, true,
                getFilteredIdsForFeedback());
    }

    /**
     * @param id
     * @param searchFormModel
     * @return
     */
    protected abstract ObjectForm<O> createForm(String id, IModel<SearchForm> searchFormModel);

    /**
     * @return
     */
    protected abstract O createObject();

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void internalOnConfigure() {
        boolean allowed = !isValued() && !createForm.isVisibilityAllowed();
        getSelectorComponent().setVisibilityAllowed(allowed);
        addLink.setVisibilityAllowed(allowed);
        setOtherComponentsVisibilityAllowed(allowed);
    }

    /**
     * @param allowed
     */
    protected void setOtherComponentsVisibilityAllowed(boolean allowed) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSave(IEvent<?> event) {
        markAsValued(createForm.getModelObject());
        createForm.hideForm();
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCancel(IEvent<?> event) {
        createForm.hideForm();
        super.onCancel(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onAdd(IEvent<?> event) {
        createForm.setNewObject(createObject()).displayForm();
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final O createDefaultObject() {
        return createObject();
    }

    /**
     * @return the createForm
     */
    protected final ObjectForm<O> getCreateForm() {
        return createForm;
    }

}

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

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ContainerVisibleFeedbackMessageFilter;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FeedbackPanel;

/**
 * @author speralta
 */
public abstract class ObjectForm<O> extends Form<O> {

    private static final long serialVersionUID = 201107252130L;

    private static final String EDIT_PANEL_COMPONENT_ID = "newObject";

    private final IModel<SearchForm> searchFormModel;
    private final CancelButton cancelButton;

    /**
     * @param id
     * @param searchFormModel
     */
    public ObjectForm(String id, IModel<SearchForm> searchFormModel) {
        super(id, new CompoundPropertyModel<O>((O) null));
        this.searchFormModel = searchFormModel;
        add(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID).setOutputMarkupId(true));
        add(new FeedbackPanel("feedback", new ContainerVisibleFeedbackMessageFilter(this)));
        add(new Button("saveObject"));
        add(cancelButton = new CancelButton("cancelObject"));
    }

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     */
    public ObjectForm(String id, IModel<SearchForm> searchFormModel, O newObject) {
        super(id, new CompoundPropertyModel<O>(newObject));
        this.searchFormModel = searchFormModel;
        add(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID).setOutputMarkupId(true));
        add(new FeedbackPanel("feedback", new ContainerVisibleFeedbackMessageFilter(this)));
        add(new Button("saveObject"));
        add(cancelButton = new CancelButton("cancelObject"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachModels() {
        searchFormModel.detach();
        super.detachModels();
    }

    public ObjectForm<O> setNewObject(O newObject) {
        setModel(new CompoundPropertyModel<O>(newObject));
        return this;
    }

    public final ObjectForm<O> displayForm() {
        setVisibilityAllowed(true);
        replace(createEditPanel(EDIT_PANEL_COMPONENT_ID, searchFormModel));
        return this;
    }

    public final ObjectForm<O> hideForm() {
        setVisibilityAllowed(false);
        replace(new WebMarkupContainer(EDIT_PANEL_COMPONENT_ID).setOutputMarkupId(true));
        return this;
    }

    protected abstract Component createEditPanel(String id, IModel<SearchForm> searchFormModel);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        send(getParent(), Broadcast.BUBBLE, Action.SAVE);
    }

    public boolean isCancelButton(IEventSource source) {
        return cancelButton == source;
    }

}

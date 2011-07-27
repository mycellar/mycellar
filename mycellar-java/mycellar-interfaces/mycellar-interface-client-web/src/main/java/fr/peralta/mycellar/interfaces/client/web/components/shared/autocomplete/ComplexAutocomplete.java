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
package fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class ComplexAutocomplete<O extends IdentifiedEntity<O>> extends Panel {

    private static final long serialVersionUID = 201107252130L;

    private static final String AUTOCOMPLETE_COMPONENT_ID = "autocomplete";
    private static final String CREATE_FORM_COMPONENT_ID = "createForm";
    private static final String VALUE_COMPONENT_ID = "value";
    private static final String ADD_COMPONENT_ID = "add";
    private static final String CANCEL_COMPONENT_ID = "cancelValue";

    private final Logger logger = LoggerFactory.getLogger(ComplexAutocomplete.class);

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param id
     * @param label
     */
    public ComplexAutocomplete(String id, IModel<?> label) {
        super(id);
        setOutputMarkupId(true);
        add(new Label("label", label));
        add(createAutocomplete(AUTOCOMPLETE_COMPONENT_ID, new Model<O>()));
        add(new TextField<String>(VALUE_COMPONENT_ID).setEnabled(false).setVisibilityAllowed(false));
        add(createHiddenCreateForm());
        add(new ActionLink(ADD_COMPONENT_ID, Action.ADD));
        add(new ActionLink(CANCEL_COMPONENT_ID, Action.CANCEL).setVisibilityAllowed(false));
    }

    /**
     * @param id
     * @param model
     * @return
     */
    protected abstract AutocompleteAjaxComponent<O> createAutocomplete(String id, IModel<O> model);

    /**
     * @param object
     * @return
     */
    protected String getLabelFor(O object) {
        return rendererServiceFacade.render(object);
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
    @SuppressWarnings("unchecked")
    @Override
    protected void onModelChanged() {
        IModel<O> model = (IModel<O>) getDefaultModel();
        if ((model != null) && (model.getObject() != null)) {
            get(VALUE_COMPONENT_ID).setVisibilityAllowed(true).setDefaultModel(
                    new Model<String>(getLabelFor(model.getObject())));
            get(CANCEL_COMPONENT_ID).setVisibilityAllowed(true);
            replace(createHiddenCreateForm());
            get(ADD_COMPONENT_ID).setVisibilityAllowed(false);
            get(AUTOCOMPLETE_COMPONENT_ID).setVisibilityAllowed(false);
        } else {
            get(AUTOCOMPLETE_COMPONENT_ID).setVisibilityAllowed(true);
            get(ADD_COMPONENT_ID).setVisibilityAllowed(true);
            get(VALUE_COMPONENT_ID).setVisibilityAllowed(false).setDefaultModel(
                    new Model<String>(null));
            get(CANCEL_COMPONENT_ID).setVisibilityAllowed(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                setDefaultModelObject(((AbstractAutocompleteAjaxComponent<?>) event.getSource())
                        .getModelObject());
                break;
            case ADD:
                get(ADD_COMPONENT_ID).setVisibilityAllowed(false);
                get(AUTOCOMPLETE_COMPONENT_ID).setVisibilityAllowed(false);
                replace(
                        new ObjectForm<O>(CREATE_FORM_COMPONENT_ID, createObject())
                                .replace(createComponentForCreation(ObjectForm.EDIT_PANEL_COMPONENT_ID)))
                        .setVisibilityAllowed(true);
                break;
            case SAVE:
                setDefaultModelObject(get(CREATE_FORM_COMPONENT_ID).getDefaultModelObject());
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            if (action.isAjax()) {
                action.getAjaxRequestTarget().add(this);
            }
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

    private Component createHiddenCreateForm() {
        return new EmptyPanel(CREATE_FORM_COMPONENT_ID).setVisibilityAllowed(false);
    }
}

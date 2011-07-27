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
package fr.peralta.mycellar.interfaces.client.web.components.shared.cloud;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class ComplexTagCloud<O> extends SimpleTagCloud<O> {

    private static final long serialVersionUID = 201107252130L;

    private static final String CREATE_FORM_COMPONENT_ID = "createForm";
    private static final String ADD_COMPONENT_ID = "add";

    private final Logger logger = LoggerFactory.getLogger(ComplexTagCloud.class);

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param id
     * @param label
     * @param objects
     */
    public ComplexTagCloud(String id, IModel<?> label, Map<O, Integer> objects) {
        super(id, label, objects);
        add(createHiddenCreateForm());
        add(new ActionLink(ADD_COMPONENT_ID, Action.ADD));
    }

    /**
     * @param object
     * @return
     */
    @Override
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
    @Override
    protected void onModelChanged() {
        super.onModelChanged();
        replace(createHiddenCreateForm());
        get(ADD_COMPONENT_ID).setVisibilityAllowed(getDefaultModelObject() == null);
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
                setDefaultModelObject(((Tag<?>) event.getSource()).getDefaultModelObject());
                break;
            case ADD:
                get(ADD_COMPONENT_ID).setVisibilityAllowed(false);
                get(CLOUD_COMPONENT_ID).setVisibilityAllowed(false);
                replace(new ObjectForm<O>(CREATE_FORM_COMPONENT_ID, createObject()).replace(
                        createComponentForCreation(ObjectForm.EDIT_PANEL_COMPONENT_ID))
                        .setVisibilityAllowed(true));
                break;
            case SAVE:
                setDefaultModelObject(get(CREATE_FORM_COMPONENT_ID).getDefaultModelObject());
                break;
            case CANCEL:
                setDefaultModelObject(null);
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

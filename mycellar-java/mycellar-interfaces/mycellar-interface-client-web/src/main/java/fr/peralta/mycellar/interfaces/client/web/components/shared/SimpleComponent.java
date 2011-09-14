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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public abstract class SimpleComponent<O> extends Panel {

    private static final long serialVersionUID = 201107281247L;
    private static final Logger logger = LoggerFactory.getLogger(SimpleComponent.class);

    protected static final String CONTAINER_COMPONENT_ID = "container";
    protected static final String CANCEL_COMPONENT_ID = "cancel";
    protected static final String LABEL_COMPONENT_ID = "label";
    protected static final String SELECTOR_COMPONENT_ID = "selector";
    protected static final String VALUE_COMPONENT_ID = "value";

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    private boolean valued = false;

    /**
     * @param id
     * @param label
     */
    public SimpleComponent(String id, IModel<String> label) {
        super(id);
        setOutputMarkupId(true);
        WebMarkupContainer container = new WebMarkupContainer("container");
        container.add(new Label(LABEL_COMPONENT_ID, label));
        container.add(new EmptyPanel(SELECTOR_COMPONENT_ID));
        container.add(new TextField<String>(VALUE_COMPONENT_ID).setEnabled(false).setDefaultModel(
                new Model<String>()));
        container.add(new ActionLink(CANCEL_COMPONENT_ID, Action.CANCEL));
        add(container);
    }

    /**
     * Sets model.
     * 
     * @param model
     */
    @SuppressWarnings("unchecked")
    public final IModel<? extends O> getModel() {
        return (IModel<? extends O>) getDefaultModel();
    }

    /**
     * Gets model object.
     * 
     * @return model object
     */
    @SuppressWarnings("unchecked")
    public final O getModelObject() {
        return (O) getDefaultModelObject();
    }

    /**
     * Sets model.
     * 
     * @param model
     *            the model
     */
    public final void setModel(IModel<? extends O> model) {
        setDefaultModel(model);
    }

    /**
     * Sets model object.
     * 
     * @param object
     *            the model object
     */
    public final void setModelObject(O object) {
        setDefaultModelObject(object);
    }

    /**
     * @param modelObject
     */
    private void configureComponent(O modelObject) {
        if (isReadyToSelect()
                && (get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + SELECTOR_COMPONENT_ID) instanceof EmptyPanel)) {
            get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).replaceWith(
                    createSelectorComponent(SELECTOR_COMPONENT_ID));
        } else if (!isReadyToSelect()
                && !(get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + SELECTOR_COMPONENT_ID) instanceof EmptyPanel)) {
            get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).replaceWith(
                    new EmptyPanel(SELECTOR_COMPONENT_ID));
        }
        internalConfigureComponent(modelObject);
        get(CONTAINER_COMPONENT_ID).setVisibilityAllowed(isReadyToSelect());
        get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CANCEL_COMPONENT_ID).setVisibilityAllowed(
                isValued());
        Component valueComponent = get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + VALUE_COMPONENT_ID)
                .setVisibilityAllowed(isValued());
        if (isValued()) {
            String value = getValueLabelFor(modelObject);
            valueComponent.setDefaultModel(new Model<String>(value)).add(
                    new AttributeModifier("size", new Model<Integer>(value.length())));
        } else {
            valueComponent.setDefaultModel(new Model<String>());
        }
    }

    /**
     * @param modelObject
     */
    protected void internalConfigureComponent(O modelObject) {
        get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).setVisibilityAllowed(
                !isValued());
    }

    /**
     * @param id
     * @return
     */
    protected abstract Component createSelectorComponent(String id);

    /**
     * @return
     */
    protected abstract boolean isReadyToSelect();

    /**
     * @param object
     * @return
     */
    protected String getValueLabelFor(O object) {
        return rendererServiceFacade.render(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void onConfigure() {
        super.onConfigure();
        configureComponent(getModelObject());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected IModel<?> initModel() {
        return new CompoundPropertyModel<O>((IModel<O>) super.initModel());
    }

    /**
     * @param object
     *            not null
     * @return true if the object is created or selected
     */
    public boolean isValued() {
        return valued;
    }

    /**
     * @param valued
     *            the valued to set
     */
    protected void setValued(boolean valued) {
        this.valued = valued;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged() {
        send(getParent(), Broadcast.BUBBLE, Action.MODEL_CHANGED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                onSelect(event.getSource(), action);
                break;
            case CANCEL:
                onCancel(event.getSource(), action);
                break;
            case ADD:
                onAdd(event.getSource(), action);
                break;
            case SAVE:
                onSave(event.getSource(), action);
                break;
            case MODEL_CHANGED:
                onModelChanged(event.getSource(), action);
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            AjaxTool.ajaxReRender(this);
        } else {
            onOtherEvent(event);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

    protected void onOtherEvent(IEvent<?> event) {

    }

    protected void onSelect(IEventSource source, Action action) {
        setValued(true);
        setModelObject(getModelObjectFromEvent(source));
    }

    protected void onCancel(IEventSource source, Action action) {
        setValued(false);
        setModelObject(null);
    }

    protected void onModelChanged(IEventSource source, Action action) {
        throw new WicketRuntimeException("Action " + action + " not managed from source " + source
                + ".");
    }

    protected void onSave(IEventSource source, Action action) {
        throw new WicketRuntimeException("Action " + action + " not managed from source " + source
                + ".");
    }

    protected void onAdd(IEventSource source, Action action) {
        throw new WicketRuntimeException("Action " + action + " not managed from source " + source
                + ".");
    }

    /**
     * @param source
     * @return
     */
    protected abstract O getModelObjectFromEvent(IEventSource source);

    protected final RendererServiceFacade getRendererServiceFacade() {
        return rendererServiceFacade;
    }

}

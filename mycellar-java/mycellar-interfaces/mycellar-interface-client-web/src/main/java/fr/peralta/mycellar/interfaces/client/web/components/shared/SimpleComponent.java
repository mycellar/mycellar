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
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public abstract class SimpleComponent<O> extends CompoundPropertyPanel<O> {

    /**
     * @author speralta
     */
    private static class SimpleComponentLabel<O> extends Label {

        private static final long serialVersionUID = 201202230714L;

        private final SimpleComponent<O> component;

        /**
         * @param id
         * @param model
         */
        private SimpleComponentLabel(String id, IModel<?> model, SimpleComponent<O> component) {
            super(id, model);
            this.component = component;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);

            if (component.isRequired()) {
                tag.append("class", "required", " ");
            }
            if (!component.isValid()) {
                tag.append("class", "error", " ");
            }

            if (!component.isEnabledInHierarchy()) {
                tag.append("class", "disabled", " ");
            }
        }
    }

    private static final long serialVersionUID = 201107281247L;
    private static final Logger logger = LoggerFactory.getLogger(SimpleComponent.class);

    protected static final String CONTAINER_COMPONENT_ID = "container";
    protected static final String CONTAINER_BODY_COMPONENT_ID = CONTAINER_COMPONENT_ID + "_"
            + FormComponentFeedbackBorder.BODY;
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
        setRequired(true);
        FormComponentFeedbackBorder container = new FormComponentFeedbackBorder(
                CONTAINER_COMPONENT_ID, true, getFilteredIdsForFeedback());
        container.add(new SimpleComponentLabel<O>(LABEL_COMPONENT_ID, label, this));
        container.add(new ValueComponent(VALUE_COMPONENT_ID));
        add(container);
    }

    protected String[] getFilteredIdsForFeedback() {
        return null;
    }

    public boolean isContainerVisibleInHierarchy() {
        return get(CONTAINER_COMPONENT_ID).isVisibleInHierarchy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        ((WebMarkupContainer) get(CONTAINER_COMPONENT_ID))
                .add(createSelectorComponent(SELECTOR_COMPONENT_ID));
    }

    /**
     * @param id
     * @return
     */
    protected abstract Component createSelectorComponent(String id);

    /**
     * @return
     */
    protected boolean isReadyToSelect() {
        return true;
    }

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
        get(CONTAINER_COMPONENT_ID).setVisibilityAllowed(isReadyToSelect());
        internalOnConfigure();
        get(
                CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                        + PATH_SEPARATOR + VALUE_COMPONENT_ID).setVisibilityAllowed(valued);
    }

    /**
     * 
     */
    protected void internalOnConfigure() {
        get(
                CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                        + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).setVisibilityAllowed(!valued);
    }

    /**
     * @param object
     *            not null
     * @return true if the object is created or selected
     */
    public final boolean isValued() {
        return valued;
    }

    protected final void markAsNonValued() {
        valued = false;
        setModelObject(createDefaultObject());
    }

    protected final void markAsValued(O object) {
        valued = true;
        setModelObject(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void convertInput() {
        setConvertedInput(getModelObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkRequired() {
        if (isRequired()) {
            return isValued();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        if (isContainerVisibleInHierarchy()) {
            super.validate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged() {
        send(getParent(), Broadcast.BUBBLE, Action.MODEL_CHANGED);
        Component valueComponent = get(CONTAINER_COMPONENT_ID + PATH_SEPARATOR
                + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR + VALUE_COMPONENT_ID);
        if (valued) {
            String value = getValueLabelFor(getModelObject());
            valueComponent.setDefaultModel(new Model<String>(value));
        } else {
            valueComponent.setDefaultModel(new Model<String>());
        }
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
        markAsValued(getModelObjectFromEvent(source));
    }

    protected void onCancel(IEventSource source, Action action) {
        markAsNonValued();
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

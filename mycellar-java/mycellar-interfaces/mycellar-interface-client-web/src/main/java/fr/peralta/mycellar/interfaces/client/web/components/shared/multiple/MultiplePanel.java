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
package fr.peralta.mycellar.interfaces.client.web.components.shared.multiple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.renderers.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * TODO extends SimpleComponent ?
 * 
 * @author speralta
 */
public abstract class MultiplePanel<O> extends Panel {

    private static final long serialVersionUID = 201108041152L;
    private static final Logger logger = LoggerFactory.getLogger(MultiplePanel.class);

    private static final String MULTIPLE_COMPONENT_ID = "multiple";

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param id
     */
    public MultiplePanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new Multiple<O>(MULTIPLE_COMPONENT_ID));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onBeforeRender() {
        Map<O, Long> objects = getData();
        if (getModelObject() != null) {
            for (Iterator<O> iterator = getModelObject().iterator(); iterator.hasNext();) {
                O object = iterator.next();
                if (!objects.containsKey(object)) {
                    iterator.remove();
                }
            }
        }
        ((Multiple<O>) get(MULTIPLE_COMPONENT_ID)).setList(getListFrom(objects));
        super.onBeforeRender();
    }

    /**
     * @return
     */
    protected abstract Map<O, Long> getData();

    /**
     * Sets model.
     * 
     * @param model
     */
    @SuppressWarnings("unchecked")
    public final IModel<? extends List<O>> getModel() {
        return (IModel<? extends List<O>>) getDefaultModel();
    }

    /**
     * Gets model object.
     * 
     * @return model object
     */
    @SuppressWarnings("unchecked")
    public final List<O> getModelObject() {
        return (List<O>) getDefaultModelObject();
    }

    /**
     * Sets model.
     * 
     * @param model
     *            the model
     */
    public final Component setModel(IModel<? extends List<O>> model) {
        return setDefaultModel(model);
    }

    /**
     * Sets model object.
     * 
     * @param object
     *            the model object
     */
    public final Component setModelObject(List<O> object) {
        return setDefaultModelObject(object);
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
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof MultipleSelection) {
                    O object = (O) ((MultipleSelection<?>) event.getSource())
                            .getDefaultModelObject();
                    List<O> modelObject = new ArrayList<O>(getModelObject());
                    if (modelObject.contains(object)) {
                        modelObject.remove(object);
                    } else {
                        modelObject.add(object);
                    }
                    setModelObject(modelObject);
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

    /**
     * @param objects
     * @return
     */
    private List<MultipleData<O>> getListFrom(Map<O, Long> objects) {
        List<MultipleData<O>> list = new ArrayList<MultipleData<O>>();
        for (O object : objects.keySet()) {
            list.add(new MultipleData<O>(object, objects.get(object), getValueLabelFor(object)));
        }
        Collections.sort(list, new MultipleComparator());
        return list;
    }

    /**
     * @param object
     * @return
     */
    protected String getValueLabelFor(O object) {
        return rendererServiceFacade.render(object);
    }

}

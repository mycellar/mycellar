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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class SimpleTagCloud<O> extends Panel {

    private static final long serialVersionUID = 201107191845L;

    protected static final String CLOUD_COMPONENT_ID = "cloud";
    protected static final String VALUE_COMPONENT_ID = "value";

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param id
     * @param label
     * @param objects
     */
    public SimpleTagCloud(String id, IModel<?> label, Map<O, Integer> objects) {
        super(id);
        add(new Label("label", label));
        add(new TagCloud<O>(CLOUD_COMPONENT_ID, getListFrom(objects)));
        add(new Label(VALUE_COMPONENT_ID).setVisibilityAllowed(false));
    }

    /**
     * @param objects
     * @return
     */
    private List<TagData<O>> getListFrom(Map<O, Integer> objects) {
        List<TagData<O>> list = new ArrayList<TagData<O>>();
        int min = 0;
        int max = 0;
        for (int value : objects.values()) {
            if (min == 0) {
                min = value;
            } else {
                min = Math.min(min, value);
            }
            max = Math.max(max, value);
        }
        for (O object : objects.keySet()) {
            list.add(new TagData<O>(object, ((float) (objects.get(object) - min) / (float) Math
                    .max(1, max - min)) + 1, getLabelFor(object)));
        }
        return list;
    }

    /**
     * @param object
     * @return
     */
    protected String getLabelFor(O object) {
        return rendererServiceFacade.render(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                event.stop();
                setDefaultModelObject(((Tag<?>) event.getSource()).getModelObject());
                get(VALUE_COMPONENT_ID).setVisibilityAllowed(true).setDefaultModel(
                        new Model<String>(getLabelFor((O) getDefaultModelObject())));
                get(CLOUD_COMPONENT_ID).setVisibilityAllowed(false);
                send(getParent(), Broadcast.EXACT, Action.SELECT);
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
        }
    }

}

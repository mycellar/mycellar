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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class ComplexTagCloud<O> extends Panel {

    private static final long serialVersionUID = 201011071626L;

    private static final String CLOUD_COMPONENT_ID = "cloud";
    private static final String CREATE_FORM_COMPONENT_ID = "createForm";
    private static final String VALUE_COMPONENT_ID = "value";
    private static final String ADD_COMPONENT_ID = "add";

    static final class CloudView<O> extends PropertyListView<TagData<O>> {
        private static final long serialVersionUID = 201011071626L;

        /**
         * @param id
         * @param list
         */
        public CloudView(String id, List<? extends TagData<O>> list) {
            super(id, list);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(ListItem<TagData<O>> item) {
            item.add(new Tag<O>("object"));
        }

    }

    static final class TagData<O> implements Serializable {

        private static final long serialVersionUID = 201011071626L;

        private final float size;

        private final O object;

        private final String label;

        /**
         * @param object
         * @param size
         * @param label
         */
        public TagData(O object, float size, String label) {
            this.object = object;
            this.size = size;
            this.label = label;
        }

        public String getStyle() {
            return "font-size: " + Float.toString(size) + "em;";
        }

        /**
         * @return the object
         */
        public O getObject() {
            return object;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }
    }

    static final class Tag<O> extends Link<TagData<O>> {

        private static final long serialVersionUID = 201011071626L;

        /**
         * @param id
         */
        public Tag(String id) {
            super(id);
            add(new Label("label").add(new AttributeAppender("style",
                    new ComponentPropertyModel<String>("style"), ";")));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onClick() {
            send(findParent(ComplexTagCloud.class), Broadcast.EXACT, Action.SELECT);
        }
    }

    /**
     * @param id
     * @param label
     * @param objects
     */
    public ComplexTagCloud(String id, IModel<?> label, Map<O, Integer> objects) {
        super(id);
        add(new Label("label", label));
        add(new CloudView<O>(CLOUD_COMPONENT_ID, getListFrom(objects)));
        add(new Label(VALUE_COMPONENT_ID).setVisibilityAllowed(false));
        add(createHiddenCreateForm());
        add(new ActionLink(ADD_COMPONENT_ID, Action.ADD));
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
    protected abstract String getLabelFor(O object);

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
    public void onEvent(IEvent<?> event) {
        Action action = (Action) event.getPayload();
        switch (action) {
        case SELECT:
            setDefaultModelObject(((Tag<?>) event.getSource()).getModelObject());
            get(ADD_COMPONENT_ID).setVisibilityAllowed(false);
            get(VALUE_COMPONENT_ID).setVisibilityAllowed(true).setDefaultModel(
                    new Model<String>(getLabelFor((O) getDefaultModelObject())));
            get(CLOUD_COMPONENT_ID).setVisibilityAllowed(false);
            send(getParent(), Broadcast.EXACT, Action.SELECT);
            break;
        case ADD:
            get(ADD_COMPONENT_ID).setVisibilityAllowed(false);
            get(CLOUD_COMPONENT_ID).setVisibilityAllowed(false);
            replace(
                    new ObjectForm<O>(CREATE_FORM_COMPONENT_ID, createObject())
                            .replace(createComponentForCreation(ObjectForm.EDIT_PANEL_COMPONENT_ID)))
                    .setVisibilityAllowed(true);
            break;
        case SAVE:
            setDefaultModelObject(get(CREATE_FORM_COMPONENT_ID).getDefaultModelObject());
            get(VALUE_COMPONENT_ID).setVisibilityAllowed(true).setDefaultModel(
                    new Model<String>(getLabelFor((O) getDefaultModelObject())));
            replace(createHiddenCreateForm());
            break;
        default:
            throw new WicketRuntimeException("Action " + action + " not managed.");
        }
    }

    private Component createHiddenCreateForm() {
        return new EmptyPanel(CREATE_FORM_COMPONENT_ID).setVisibilityAllowed(false);
    }
}

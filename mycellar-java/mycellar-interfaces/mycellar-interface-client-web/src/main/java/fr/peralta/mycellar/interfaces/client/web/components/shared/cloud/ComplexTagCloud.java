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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.ComplexComponent;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class ComplexTagCloud<O> extends ComplexComponent<O> {

    private static final long serialVersionUID = 201111161904L;

    /**
     * @param id
     * @param label
     */
    public ComplexTagCloud(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected O getModelObjectFromEvent(IEventSource source) {
        if (source instanceof Tag) {
            return (O) ((Tag<?>) source).getDefaultModelObject();
        } else {
            throw new WicketRuntimeException("Event did not come from " + Tag.class.getSimpleName()
                    + ".");
        }
    }

    /**
     * @param object
     * @return
     */
    protected String getSelectorLabelFor(O object) {
        return getRendererServiceFacade().render(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Component createSelectorComponent(String id) {
        return new TagCloudPanel<O>(id, getList());
    }

    @SuppressWarnings("unchecked")
    protected void refreshTagCloud() {
        TagCloudPanel<O> tagCloudPanel = ((TagCloudPanel<O>) get(CONTAINER_COMPONENT_ID
                + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID + PATH_SEPARATOR
                + SELECTOR_COMPONENT_ID));
        if (tagCloudPanel != null) {
            tagCloudPanel.changeList(getList());
        }
    }

    /**
     * @param objects
     * @return
     */
    private List<TagData<O>> getList() {
        Map<O, Long> choices;
        if (isReadyToSelect()) {
            choices = getChoices();
        } else {
            choices = new HashMap<O, Long>();
        }
        List<TagData<O>> list = new ArrayList<TagData<O>>();
        long min = 0;
        long max = 0;
        for (long value : choices.values()) {
            if (min == 0) {
                min = value;
            } else {
                min = Math.min(min, value);
            }
            max = Math.max(max, value);
        }
        for (O object : choices.keySet()) {
            list.add(new TagData<O>(object, ((float) (choices.get(object) - min) / (float) Math
                    .max(1, max - min)) + 1, getSelectorLabelFor(object)));
        }
        return list;
    }

    /**
     * @return
     */
    protected abstract Map<O, Long> getChoices();

}

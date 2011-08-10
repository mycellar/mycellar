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

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.SimpleComponent;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class SimpleTagCloud<O> extends SimpleComponent<O> {

    private static final long serialVersionUID = 201108082344L;

    /**
     * @param id
     * @param label
     */
    public SimpleTagCloud(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * @param objects
     * @return
     */
    protected List<TagData<O>> getListFrom(Map<O, Integer> objects) {
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
                    .max(1, max - min)) + 1, getValueLabelFor(object)));
        }
        return list;
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
            throw new WicketRuntimeException("Event did not come from Tag.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Component createSelectorComponent(String id) {
        return createTagCloudPanel(id);
    }

    /**
     * @param id
     * @return
     */
    protected abstract TagCloudPanel<O> createTagCloudPanel(String id);

}

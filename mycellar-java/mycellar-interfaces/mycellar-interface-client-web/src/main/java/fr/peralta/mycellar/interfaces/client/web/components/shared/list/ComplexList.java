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
package fr.peralta.mycellar.interfaces.client.web.components.shared.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.ComplexComponent;

/**
 * @author speralta
 */
public abstract class ComplexList<O> extends ComplexComponent<O> {

    private static final long serialVersionUID = 201109101934L;

    /**
     * @param id
     * @param label
     */
    public ComplexList(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Component createSelectorComponent(String id) {
        return createListComponentPanel(id);
    }

    /**
     * @param id
     * @return
     */
    protected ListComponentPanel<O> createListComponentPanel(String id) {
        return new ListComponentPanel<O>(id, getList());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected O getModelObjectFromEvent(IEventSource source) {
        if (source instanceof ListSelection) {
            return (O) ((ListSelection<?>) source).getDefaultModelObject();
        } else {
            throw new WicketRuntimeException("Event did not come from "
                    + ListSelection.class.getSimpleName() + ".");
        }
    }

    /**
     * @param object
     * @return
     */
    protected String getSelectorLabelFor(O object) {
        return getRendererServiceFacade().render(object);
    }

    @SuppressWarnings("unchecked")
    protected void refreshList() {
        ListComponentPanel<O> listComponentPanel = ((ListComponentPanel<O>) get(CONTAINER_COMPONENT_ID
                + PATH_SEPARATOR
                + CONTAINER_BODY_COMPONENT_ID
                + PATH_SEPARATOR
                + SELECTOR_COMPONENT_ID));
        if (listComponentPanel != null) {
            listComponentPanel.changeList(getList());
        }
    }

    /**
     * @return
     */
    private final List<ListData<O>> getList() {
        List<O> list;
        if (isReadyToSelect()) {
            list = getChoices();
        } else {
            list = new ArrayList<O>();
        }
        List<ListData<O>> result = new ArrayList<ListData<O>>();
        for (O object : list) {
            result.add(new ListData<O>(object, getSelectorLabelFor(object)));
        }
        return result;
    }

    /**
     * @return
     */
    protected abstract List<O> getChoices();

}

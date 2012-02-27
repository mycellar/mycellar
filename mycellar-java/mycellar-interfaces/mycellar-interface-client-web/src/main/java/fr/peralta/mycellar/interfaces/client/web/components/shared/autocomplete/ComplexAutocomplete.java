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
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ComplexComponent;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class ComplexAutocomplete<O extends IdentifiedEntity<O>> extends
        ComplexComponent<O> {

    private static final long serialVersionUID = 201108082348L;

    /**
     * @param id
     * @param label
     */
    public ComplexAutocomplete(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * @param id
     * @return
     */
    protected abstract AutocompleteAjaxComponent<O> createAutocomplete(String id);

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Component createSelectorComponent(String id) {
        return createAutocomplete(id).setDefaultModel(new Model<O>());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected O getModelObjectFromEvent(IEventSource source) {
        if (source instanceof AutocompleteAjaxComponent) {
            return ((AutocompleteAjaxComponent<O>) source).getModelObject();
        } else {
            throw new WicketRuntimeException("Event did not come from Tag.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCancel(IEventSource source, Action action) {
        get(
                CONTAINER_COMPONENT_ID + PATH_SEPARATOR + CONTAINER_BODY_COMPONENT_ID
                        + PATH_SEPARATOR + SELECTOR_COMPONENT_ID).setDefaultModel(new Model<O>());
        super.onCancel(source, action);
    }

}

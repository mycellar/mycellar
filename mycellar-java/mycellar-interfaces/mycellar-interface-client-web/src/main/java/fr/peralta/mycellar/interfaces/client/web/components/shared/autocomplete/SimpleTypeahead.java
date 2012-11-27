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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SimpleComponent;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class SimpleTypeahead<O extends IdentifiedEntity> extends
        SimpleComponent<O, AbstractTypeaheadComponent<O>> {

    private static final long serialVersionUID = 201108082348L;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public SimpleTypeahead(String id, IModel<String> label, IModel<SearchForm> searchFormModel) {
        super(id, label, searchFormModel);
    }

    /**
     * @param id
     * @return
     */
    protected abstract AbstractTypeaheadComponent<O> createAutocomplete(String id);

    /**
     * {@inheritDoc}
     */
    @Override
    protected final AbstractTypeaheadComponent<O> createSelectorComponent(String id) {
        AbstractTypeaheadComponent<O> component = createAutocomplete(id);
        component.setModel(new Model<O>());
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected O getModelObjectFromEvent(IEventSource source) {
        if (source == getSelectorComponent()) {
            return getSelectorComponent().getModelObject();
        } else {
            throw new WicketRuntimeException("Event did not come from ObjectAutoCompleteField.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCancel(IEvent<?> event) {
        getSelectorComponent().setDefaultModel(new Model<O>());
        super.onCancel(event);
    }

}

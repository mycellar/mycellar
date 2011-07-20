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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.EntityChoiceRenderer;

/**
 * @author speralta
 */
public abstract class AbstractAutocompleteAjaxComponent<E extends IdentifiedEntity<E>> extends
        AutocompleteAjaxComponent<E> {

    private static final long serialVersionUID = 201107191921L;

    private Class<? extends Component> parentComponentToReRender;

    /**
     * @param id
     * @param parentComponentToReRender
     */
    public AbstractAutocompleteAjaxComponent(String id,
            Class<? extends Component> parentComponentToReRender) {
        this(id, null, parentComponentToReRender);
    }

    /**
     * @param id
     * @param model
     * @param parentComponentToReRender
     */
    public AbstractAutocompleteAjaxComponent(String id, IModel<E> model,
            Class<? extends Component> parentComponentToReRender) {
        super(id, model);
        this.parentComponentToReRender = parentComponentToReRender;
        EntityChoiceRenderer<E> entityChoiceRenderer = new EntityChoiceRenderer<E>();
        Injector.get().inject(entityChoiceRenderer);
        setChoiceRenderer(entityChoiceRenderer);
        setAutoUpdate(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getValueOnSearchFail(String input) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        target.add(findParent(parentComponentToReRender));
        send(getParent(), Broadcast.BUBBLE, Action.SELECT);
    }
}

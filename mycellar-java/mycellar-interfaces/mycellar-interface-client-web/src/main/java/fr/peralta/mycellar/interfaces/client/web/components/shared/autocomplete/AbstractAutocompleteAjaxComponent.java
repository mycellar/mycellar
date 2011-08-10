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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;

/**
 * @author speralta
 */
public abstract class AbstractAutocompleteAjaxComponent<E extends IdentifiedEntity<E>> extends
        AutocompleteAjaxComponent<E> {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     */
    public AbstractAutocompleteAjaxComponent(String id) {
        this(id, null);
    }

    /**
     * @param id
     * @param model
     */
    public AbstractAutocompleteAjaxComponent(String id, IModel<E> model) {
        super(id, model);
        setChoiceRenderer(new EntityChoiceRenderer<E>());
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
        send(getParent(), Broadcast.BUBBLE, Action.SELECT.setAjaxRequestTarget(target));
    }
}

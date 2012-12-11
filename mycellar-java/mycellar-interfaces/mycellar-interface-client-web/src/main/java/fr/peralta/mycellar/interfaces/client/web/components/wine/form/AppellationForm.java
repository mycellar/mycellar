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
package fr.peralta.mycellar.interfaces.client.web.components.wine.form;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.AppellationEditPanel;

/**
 * @author speralta
 */
public class AppellationForm extends ObjectForm<Appellation> {

    private static final long serialVersionUID = 201205101324L;

    private final FilterEnum[] filters;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     * @param filters
     */
    public AppellationForm(String id, IModel<SearchForm> searchFormModel, Appellation newObject,
            FilterEnum... filters) {
        super(id, searchFormModel, newObject);
        this.filters = filters;
    }

    /**
     * @param id
     * @param searchFormModel
     * @param filters
     */
    public AppellationForm(String id, IModel<SearchForm> searchFormModel, FilterEnum... filters) {
        super(id, searchFormModel);
        this.filters = filters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new AppellationEditPanel(id, searchFormModel, filters);
    }
}

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
package fr.peralta.mycellar.interfaces.client.web.components.stock.form;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.CellarShareEditPanel;

/**
 * @author speralta
 */
public class CellarShareForm extends ObjectForm<CellarShare> {

    private static final long serialVersionUID = 201205101128L;

    private final CountEnum count;
    private final FilterEnum[] filters;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     * @param count
     * @param filters
     */
    public CellarShareForm(String id, IModel<SearchForm> searchFormModel, CellarShare newObject,
            CountEnum count, FilterEnum... filters) {
        super(id, searchFormModel, newObject);
        this.count = count;
        this.filters = filters;
    }

    /**
     * @param id
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public CellarShareForm(String id, IModel<SearchForm> searchFormModel, CountEnum count,
            FilterEnum... filters) {
        super(id, searchFormModel);
        this.count = count;
        this.filters = filters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new CellarShareEditPanel(id, searchFormModel, count, filters);
    }

}

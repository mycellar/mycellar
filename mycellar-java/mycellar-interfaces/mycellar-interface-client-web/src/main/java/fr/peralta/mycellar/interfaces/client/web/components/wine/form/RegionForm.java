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

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.RegionEditPanel;

/**
 * @author speralta
 */
public class RegionForm extends ObjectForm<Region> {

    private static final long serialVersionUID = 201205101324L;

    private final CountEnum count;
    private boolean countryCancelAllowed = true;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     * @param count
     */
    public RegionForm(String id, IModel<SearchForm> searchFormModel, Region newObject,
            CountEnum count) {
        super(id, searchFormModel, newObject);
        this.count = count;
    }

    /**
     * @param id
     * @param searchFormModel
     * @param count
     */
    public RegionForm(String id, IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id, searchFormModel);
        this.count = count;
    }

    /**
     * @param countryCancelAllowed
     *            the countryCancelAllowed to set
     */
    public RegionForm setCountryCancelAllowed(boolean countryCancelAllowed) {
        this.countryCancelAllowed = countryCancelAllowed;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new RegionEditPanel(id, count).setCountryCancelAllowed(countryCancelAllowed);
    }

}

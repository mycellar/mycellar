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
package fr.peralta.mycellar.interfaces.client.web.components.wine.cloud;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.CountryEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class CountryComplexTagCloud extends ComplexTagCloud<Country> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final CountEnum count;

    /**
     * @param id
     * @param label
     * @param count
     */
    public CountryComplexTagCloud(String id, IModel<String> label, CountEnum count) {
        super(id, label);
        this.count = count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new CountryEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Country createObject() {
        return new Country();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<Country> createTagCloudPanel(String id) {
        return new TagCloudPanel<Country>(id, getListFrom(wineServiceFacade.getCountries(
                new SearchForm(), count)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return true;
    }

}

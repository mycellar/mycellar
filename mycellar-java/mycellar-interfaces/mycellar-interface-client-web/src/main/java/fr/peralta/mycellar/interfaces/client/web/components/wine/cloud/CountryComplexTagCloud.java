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

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.CountryEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.Country;

/**
 * @author speralta
 */
public class CountryComplexTagCloud extends ComplexTagCloud<Country> {

    private static final long serialVersionUID = 201011071626L;

    /**
     * @param id
     * @param label
     * @param objects
     */
    public CountryComplexTagCloud(String id, IModel<?> label, Map<Country, Integer> objects) {
        super(id, label, objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLabelFor(Country object) {
        return object.getName();
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

}

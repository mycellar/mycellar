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
package fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;

import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.ComplexAutoComplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.ProducerEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ProducerComplexAutoComplete extends ComplexAutoComplete<Producer> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     */
    public ProducerComplexAutoComplete(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectAutoCompleteBuilder<Producer, Integer> createAutocomplete() {
        return new ObjectAutoCompleteBuilder<Producer, Integer>(new ProducerAutoCompleteProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Producer getObject(Integer id) {
        return wineServiceFacade.getProducerById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new ProducerEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Producer createObject() {
        return new Producer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return true;
    }

}

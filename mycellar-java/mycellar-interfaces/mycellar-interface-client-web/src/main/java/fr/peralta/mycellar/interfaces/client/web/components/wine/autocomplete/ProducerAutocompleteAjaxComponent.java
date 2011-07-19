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

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.AbstractAutocompleteAjaxComponent;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ProducerAutocompleteAjaxComponent extends AbstractAutocompleteAjaxComponent<Producer> {

    private static final long serialVersionUID = 201107182208L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     */
    public ProducerAutocompleteAjaxComponent(String id) {
        super(id);
    }

    /**
     * @param id
     * @param model
     */
    public ProducerAutocompleteAjaxComponent(String id, IModel<Producer> model) {
        super(id, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producer> getValues(String term) {
        return wineServiceFacade.getProducersStartingWith(term);
    }
}

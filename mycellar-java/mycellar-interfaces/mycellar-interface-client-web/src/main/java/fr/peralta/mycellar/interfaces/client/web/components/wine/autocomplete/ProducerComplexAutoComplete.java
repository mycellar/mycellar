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

import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.ComplexAutoComplete;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.ProducerForm;

/**
 * @author speralta
 */
public class ProducerComplexAutoComplete extends ComplexAutoComplete<Producer> {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public ProducerComplexAutoComplete(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel) {
        super(id, label, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AutocompleteAjaxComponent<Producer> createAutocomplete(String id) {
        return new ProducerAutoCompleteAjaxComponent(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Producer> createForm(String id, IModel<SearchForm> searchFormModel) {
        return new ProducerForm(id, searchFormModel);
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
    protected FilterEnum getFilterToReplace() {
        return FilterEnum.PRODUCER;
    }
}

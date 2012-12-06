/*
 * Copyright 2011, MyConfiguration
 *
 * This file is part of MyConfiguration.
 *
 * MyConfiguration is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyConfiguration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyConfiguration. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.components.admin.form;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.admin.edit.ConfigurationEditPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 */
public class ConfigurationForm extends ObjectForm<Configuration> {

    private static final long serialVersionUID = 201205101327L;

    private boolean keyReadonly = false;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     */
    public ConfigurationForm(String id, IModel<SearchForm> searchFormModel, Configuration newObject) {
        super(id, searchFormModel, newObject);
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public ConfigurationForm(String id, IModel<SearchForm> searchFormModel) {
        super(id, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new ConfigurationEditPanel(id).setKeyReadonly(keyReadonly);
    }

    /**
     * @param keyReadonly
     * @return this
     */
    public ConfigurationForm setKeyReadonly(boolean keyReadonly) {
        this.keyReadonly = keyReadonly;
        return this;
    }

}

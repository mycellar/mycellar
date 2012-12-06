/*
 * Copyright 2011, MyBookingEvent
 *
 * This file is part of MyBookingEvent.
 *
 * MyBookingEvent is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBookingEvent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBookingEvent. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.admin;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.admin.form.ConfigurationForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.admin.AdministrationServiceFacade;

/**
 * @author speralta
 */
public class ConfigurationPage extends AbstractEditPage<Configuration> {

    private static final long serialVersionUID = 201203270918L;

    private static final String CONFIGURATION_ID_PARAMETER = "configuration";

    /**
     * @param configuration
     * @return
     */
    public static PageParameters getPageParameters(Configuration configuration) {
        return new PageParameters().add(CONFIGURATION_ID_PARAMETER, configuration.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(CONFIGURATION_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private AdministrationServiceFacade administrationServiceFacade;

    /**
     * @param parameters
     */
    public ConfigurationPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Configuration getObjectById(Integer objectId) {
        return administrationServiceFacade.getConfigurationById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Configuration createNewObject() {
        return new Configuration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return CONFIGURATION_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Configuration object) throws BusinessException {
        administrationServiceFacade.saveConfiguration(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Configuration> createObjectForm(String id,
            IModel<SearchForm> searchFormModel, Configuration object) {
        return new ConfigurationForm(id, searchFormModel, object)
                .setKeyReadonly(object.getId() != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Configuration, ?, ?>> getListPageClass() {
        return ConfigurationsPage.class;
    }

}

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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrderEnum;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.interfaces.client.web.components.admin.data.ConfigurationDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.admin.AdministrationServiceFacade;

/**
 * @author speralta
 */
public class ConfigurationsPage extends
        AbstractListPage<Configuration, ConfigurationOrderEnum, ConfigurationOrder> {

    private static final long serialVersionUID = 201203262250L;

    @SpringBean
    private AdministrationServiceFacade administrationServiceFacade;

    /**
     * @param parameters
     */
    public ConfigurationsPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MultipleSortableDataProvider<Configuration, ConfigurationOrderEnum, ConfigurationOrder> getDataProvider() {
        return new ConfigurationDataProvider();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<IColumn<Configuration, ConfigurationOrderEnum>> getColumns() {
        List<IColumn<Configuration, ConfigurationOrderEnum>> columns = new ArrayList<IColumn<Configuration, ConfigurationOrderEnum>>();
        columns.add(new PropertyColumn<Configuration, ConfigurationOrderEnum>(new ResourceModel(
                "key"), ConfigurationOrderEnum.KEY, "key"));
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageParameters getEditPageParameters(Configuration object) {
        if (object == null) {
            return ConfigurationPage.getPageParametersForCreation();
        } else {
            return ConfigurationPage.getPageParameters(object);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditPage<Configuration>> getEditPageClass() {
        return ConfigurationPage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteObject(Configuration object) throws BusinessException {
        administrationServiceFacade.deleteConfiguration(object);
    }

}

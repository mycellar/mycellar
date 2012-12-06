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
package fr.peralta.mycellar.interfaces.client.web.components.admin.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrderEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.admin.AdministrationServiceFacade;

/**
 * @author speralta
 */
public class ConfigurationDataProvider extends
        MultipleSortableDataProvider<Configuration, ConfigurationOrderEnum, ConfigurationOrder> {

    private static final long serialVersionUID = 201111081450L;

    @SpringBean
    private AdministrationServiceFacade administrationServiceFacade;

    /**
     * Default constructor.
     */
    public ConfigurationDataProvider() {
        super(new ConfigurationOrder().add(ConfigurationOrderEnum.KEY, OrderWayEnum.ASC));
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Configuration> iterator(long first, long count) {
        return administrationServiceFacade.getConfigurations(getState().getOrders(), first, count)
                .iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return administrationServiceFacade.countConfigurations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Configuration> model(Configuration object) {
        return new Model<Configuration>(object);
    }
}

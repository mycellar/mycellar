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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavPanel;
import fr.peralta.mycellar.interfaces.client.web.descriptors.DescriptorServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;

/**
 * @author speralta
 */
public class ListPage extends AdminSuperPage {

    private static final long serialVersionUID = 201203262309L;

    @SpringBean
    private DescriptorServiceFacade descriptorServiceFacade;

    /**
     * @param parameters
     */
    public ListPage(PageParameters parameters) {
        super(parameters);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new NavPanel("listMenu", getClass(), getClass(),
                descriptorServiceFacade.getListPages()));
    }

}

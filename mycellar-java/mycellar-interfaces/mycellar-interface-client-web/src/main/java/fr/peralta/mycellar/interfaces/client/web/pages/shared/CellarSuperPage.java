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
package fr.peralta.mycellar.interfaces.client.web.pages.shared;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.peralta.mycellar.interfaces.client.web.components.shared.menu.MenuPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.menu.MenuablePageDescriptor;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.InputOutputPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.PackageArrivalPage;

/**
 * @author speralta
 */
public abstract class CellarSuperPage extends BasePage {

    private static final long serialVersionUID = 201117181723L;

    /**
     * @param parameters
     */
    public CellarSuperPage(PageParameters parameters) {
        super(parameters);
        add(new MenuPanel("submenu", getSubMenuClass(), getCellarPageDescriptors()));
    }

    /**
     * @return
     */
    private List<MenuablePageDescriptor> getCellarPageDescriptors() {
        List<MenuablePageDescriptor> descriptors = new ArrayList<MenuablePageDescriptor>();
        descriptors.add(new MenuablePageDescriptor(CellarsPage.class, CellarsPage.class,
                "Your cellars"));
        descriptors.add(new MenuablePageDescriptor(InputOutputPage.class, InputOutputPage.class,
                "I/O"));
        descriptors.add(new MenuablePageDescriptor(PackageArrivalPage.class,
                PackageArrivalPage.class, "Package arrival"));
        return descriptors;
    }

    /**
     * @return
     */
    protected Class<? extends CellarSuperPage> getSubMenuClass() {
        return this.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Class<? extends BasePage> getMenuClass() {
        return CellarSuperPage.class;
    }

}

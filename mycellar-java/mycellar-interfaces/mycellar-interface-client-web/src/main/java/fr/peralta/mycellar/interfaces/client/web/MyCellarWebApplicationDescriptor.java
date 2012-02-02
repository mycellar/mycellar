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
package fr.peralta.mycellar.interfaces.client.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.peralta.mycellar.interfaces.client.web.components.shared.menu.MenuablePageDescriptor;
import fr.peralta.mycellar.interfaces.client.web.components.shared.menu.MenuableSubPageDescriptor;
import fr.peralta.mycellar.interfaces.client.web.pages.HomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AdminPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.DrinkBottlesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.InputOutputPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.ShareCellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.pedia.PediaHomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.PediaSuperPage;

/**
 * @author speralta
 */
public class MyCellarWebApplicationDescriptor implements Serializable {

    private static final long serialVersionUID = 201011122248L;

    public static MyCellarWebApplicationDescriptor get() {
        return SINGLETON;
    }

    private static MyCellarWebApplicationDescriptor SINGLETON = new MyCellarWebApplicationDescriptor();

    private final List<MenuablePageDescriptor> menuablePageDescriptors = new ArrayList<MenuablePageDescriptor>();

    /**
     * Default constructor.
     */
    private MyCellarWebApplicationDescriptor() {
        menuablePageDescriptors.add(new MenuablePageDescriptor(HomePage.class, HomeSuperPage.class,
                "Home"));
        menuablePageDescriptors.add(new MenuablePageDescriptor(CellarsPage.class,
                CellarSuperPage.class, "Your cellar")
                .add(new MenuableSubPageDescriptor(CellarsPage.class, CellarsPage.class,
                        "Your cellars"))
                .add(new MenuableSubPageDescriptor(ShareCellarsPage.class, ShareCellarsPage.class,
                        "Share cellars"))
                .add(new MenuableSubPageDescriptor(InputOutputPage.class, InputOutputPage.class,
                        "I/O"))
                .add(new MenuableSubPageDescriptor(PackageArrivalPage.class,
                        PackageArrivalPage.class, "Package arrival"))
                .add(new MenuableSubPageDescriptor(DrinkBottlesPage.class, DrinkBottlesPage.class,
                        "Drink bottles")));
        menuablePageDescriptors.add(new MenuablePageDescriptor(PediaHomePage.class,
                PediaSuperPage.class, "Vinopedia"));
        menuablePageDescriptors.add(new MenuablePageDescriptor(AdminPage.class,
                AdminSuperPage.class, "Admin"));
    }

    /**
     * @return the menuablePageDescriptors
     */
    public List<MenuablePageDescriptor> getMenuablePageDescriptors() {
        return menuablePageDescriptors;
    }

}

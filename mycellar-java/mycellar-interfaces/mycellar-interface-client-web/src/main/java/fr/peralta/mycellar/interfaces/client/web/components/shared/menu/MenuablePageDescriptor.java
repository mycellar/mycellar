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
package fr.peralta.mycellar.interfaces.client.web.components.shared.menu;

import java.io.Serializable;

import fr.peralta.mycellar.interfaces.client.web.pages.shared.BasePage;

/**
 * @author speralta
 */
public class MenuablePageDescriptor implements Serializable {

    private static final long serialVersionUID = 201011122248L;

    private final Class<? extends BasePage> pageClass;

    private final Class<? extends BasePage> superPageClass;

    private final String pageTitle;

    /**
     * @param pageClass
     */
    public MenuablePageDescriptor(Class<? extends BasePage> pageClass,
            Class<? extends BasePage> superPageClass, String pageTitle) {
        this.pageClass = pageClass;
        this.superPageClass = superPageClass;
        this.pageTitle = pageTitle;
    }

    /**
     * @return the superPageClass
     */
    public Class<? extends BasePage> getSuperPageClass() {
        return superPageClass;
    }

    /**
     * @return the pageTitle
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * @return the pageClass
     */
    public Class<? extends BasePage> getPageClass() {
        return pageClass;
    }

}

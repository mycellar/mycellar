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
package fr.mycellar.interfaces.web.services.nav;

/**
 * @author speralta
 */
public class NavPageDescriptor implements NavDescriptor {

    private static final long serialVersionUID = 201011122248L;

    private final String route;

    private final String label;

    private final String icon;

    /**
     * @param route
     * @param label
     */
    public NavPageDescriptor(String route, String label) {
        this(route, label, null);
    }

    /**
     * @param route
     * @param label
     * @param icon
     */
    public NavPageDescriptor(String route, String label, String icon) {
        this.route = route;
        this.label = label;
        this.icon = icon;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the route
     */
    public String getRoute() {
        return route;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

}

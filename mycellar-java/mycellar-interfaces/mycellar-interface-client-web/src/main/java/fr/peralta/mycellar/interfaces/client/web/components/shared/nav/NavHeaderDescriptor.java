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
package fr.peralta.mycellar.interfaces.client.web.components.shared.nav;

import java.util.ArrayList;
import java.util.List;

/**
 * @author speralta
 */
public class NavHeaderDescriptor implements NavDescriptor {

    private static final long serialVersionUID = 201011122248L;

    private final List<NavPageDescriptor> pages = new ArrayList<NavPageDescriptor>();

    private final String headerKey;

    /**
     * @param headerKey
     */
    public NavHeaderDescriptor(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * @return the headerKey
     */
    public String getHeaderKey() {
        return headerKey;
    }

    /**
     * @return the pages
     */
    public List<NavPageDescriptor> getPages() {
        return pages;
    }

    /**
     * @param e
     * @return this
     * @see java.util.List#add(java.lang.Object)
     */
    public NavHeaderDescriptor addPage(NavPageDescriptor e) {
        pages.add(e);
        return this;
    }

}

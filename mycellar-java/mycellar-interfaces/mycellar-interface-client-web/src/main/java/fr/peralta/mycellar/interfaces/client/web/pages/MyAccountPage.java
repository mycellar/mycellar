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
package fr.peralta.mycellar.interfaces.client.web.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;

/**
 * @author speralta
 */
public class MyAccountPage extends HomeSuperPage {

    private static final long serialVersionUID = 201203262223L;

    /**
     * @param parameters
     */
    public MyAccountPage(PageParameters parameters) {
        super(parameters);
    }

}

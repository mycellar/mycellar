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
package fr.peralta.mycellar.interfaces.client.web.components.wine.edit;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.CountryComplexTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class BottleEditPanel extends Panel {

    private static final long serialVersionUID = 201011071626L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     */
    public BottleEditPanel(String id) {
        super(id);
        add(new CountryComplexTagCloud("country", new StringResourceModel("country", this, null),
                wineServiceFacade.getCountriesWithCounts()));
        add(new TextField<Integer>("quantity").setRequired(true));
    }
}

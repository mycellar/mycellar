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
package fr.peralta.mycellar.interfaces.client.web.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.interfaces.client.web.components.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.dto.Country;

/**
 * @author speralta
 */
public class BottleEditPanel extends Panel {

    private static final long serialVersionUID = -4529022577457265483L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     */
    public BottleEditPanel(String id) {
        super(id);
        add(new ComplexTagCloud<Country>("country", new StringResourceModel(
                "country", this, null),
                wineServiceFacade.getCountriesWithCounts()) {
            private static final long serialVersionUID = -1134198832819662620L;

            /**
             * {@inheritDoc}
             */
            @Override
            protected String getLabelFor(Country object) {
                return object.getName();
            }
        });
        add(new TextField<Integer>("quantity").setRequired(true));
    }
}

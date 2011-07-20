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

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import org.odlabs.wiquery.ui.datepicker.DatePicker;

import fr.peralta.mycellar.domain.stock.Arrival;
import fr.peralta.mycellar.interfaces.client.web.components.stock.ArrivalBottlesEditPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class PackageArrivalPage extends CellarSuperPage {

    private static final long serialVersionUID = 201117181723L;

    private final Arrival arrival = new Arrival();

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public PackageArrivalPage(PageParameters parameters) {
        super(parameters);
        Form<Arrival> form = new Form<Arrival>("form", new CompoundPropertyModel<Arrival>(arrival)) {
            private static final long serialVersionUID = 201011071905L;

            /**
             * {@inheritDoc}
             */
            @Override
            protected void onSubmit() {
                stockServiceFacade.arrival(getModelObject());
                setResponsePage(HomePage.class);
            }
        };
        form.add(new DatePicker<LocalDate>("date"));
        form.add(new TextField<String>("source"));
        form.add(new TextField<Float>("otherCharges"));
        form.add(new ArrivalBottlesEditPanel("arrivalBottles"));
        add(form);
    }

}

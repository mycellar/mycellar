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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.interfaces.client.web.components.wine.BottlesEditPanel;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.peralta.mycellar.interfaces.facades.stock.dto.Arrival;

/**
 * @author speralta
 */
public class PackageArrivalPage extends WebPage {

    private final Arrival arrival = new Arrival();

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public PackageArrivalPage(PageParameters parameters) {
        super(parameters);
        Form<Arrival> form = new Form<Arrival>("form",
                new CompoundPropertyModel<Arrival>(arrival)) {
            private static final long serialVersionUID = -5943230106768403188L;

            /**
             * {@inheritDoc}
             */
            @Override
            protected void onSubmit() {
                IFormSubmittingComponent submit = getRootForm()
                        .findSubmittingButton();
                if (submit == null || submit.getForm() == this) {
                    stockServiceFacade.arrival(getModelObject());
                }
            }
        };
        form.add(new TextField<LocalDate>("date"));
        form.add(new TextField<String>("source"));
        form.add(new TextField<Float>("otherCharges"));
        form.add(new BottlesEditPanel("bottles"));
        add(form);
    }

}

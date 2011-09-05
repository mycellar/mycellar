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
package fr.peralta.mycellar.interfaces.client.web.pages.cellar;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class NewCellarPage extends CellarSuperPage {

    private static final long serialVersionUID = 201108310956L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public NewCellarPage(PageParameters parameters) {
        super(parameters);
        Form<Cellar> form = new Form<Cellar>("form",
                new CompoundPropertyModel<Cellar>(new Cellar())) {
            private static final long serialVersionUID = 201108310958L;

            @Override
            protected void onSubmit() {
                Cellar cellar = getModelObject();
                cellar.setOwner(UserKey.getUserLoggedIn());
                stockServiceFacade.newCellar(cellar);
                setResponsePage(CellarsPage.class);
            }
        };
        form.add(new TextField<String>("name"));
        add(form);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends CellarSuperPage> getSubMenuClass() {
        return CellarsPage.class;
    }

}

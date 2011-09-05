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

import java.util.ArrayList;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.multiple.MultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarsPage extends CellarSuperPage {

    private static final long serialVersionUID = 201108170919L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    private final IModel<ArrayList<Cellar>> cellars = new Model<ArrayList<Cellar>>();

    /**
     * @param parameters
     */
    public CellarsPage(PageParameters parameters) {
        super(parameters);
        add(new BookmarkablePageLink<NewCellarPage>("newCellar", NewCellarPage.class));
        add(new MultiplePanel<Cellar>("cellars",
                stockServiceFacade.getAllCellarsWithCountsFromUser(UserKey.getUserLoggedIn())));
        cellars.setObject(new ArrayList<Cellar>());
        get("cellars").setDefaultModel(cellars);
    }

}

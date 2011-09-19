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
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class InputOutputPage extends CellarSuperPage {

    private static final long serialVersionUID = 201108170920L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param parameters
     */
    public InputOutputPage(PageParameters parameters) {
        super(parameters);
        Set<Cellar> cellars = stockServiceFacade.getAllCellarsWithCountsFromUser(
                UserKey.getUserLoggedIn()).keySet();
        final List<Movement<?>> movements;
        if ((cellars == null) || (cellars.size() == 0)) {
            movements = new ArrayList<Movement<?>>();
        } else {
            movements = stockServiceFacade.getAllMovementsFromCellars(cellars
                    .toArray(new Cellar[cellars.size()]));
        }
        add(new ListView<Movement<?>>("list", movements) {
            private static final long serialVersionUID = 201109161902L;

            /**
             * {@inheritDoc}
             */
            @Override
            protected void populateItem(ListItem<Movement<?>> item) {
                item.setModel(new CompoundPropertyModel<Movement<?>>(item.getModel()));
                item.add(new Label("date"));
                item.add(new Label("cellar.name"));
                item.add(new Label("bottle", rendererServiceFacade.render(item.getModelObject()
                        .getBottle())));
                item.add(new Label("number"));
                item.add(new Label("io", (item.getModelObject() instanceof Input) ? "I" : "O"));
            }
        });
        add(new WebMarkupContainer("noIO").setVisibilityAllowed(movements.isEmpty()));
    }
}

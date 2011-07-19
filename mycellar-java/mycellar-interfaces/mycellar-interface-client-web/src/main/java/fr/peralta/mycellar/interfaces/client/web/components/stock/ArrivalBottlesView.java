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
package fr.peralta.mycellar.interfaces.client.web.components.stock;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.stock.ArrivalBottle;

/**
 * @author speralta
 */
public class ArrivalBottlesView extends PropertyListView<ArrivalBottle> {

    private static final long serialVersionUID = 201011071626L;

    /**
     * @param id
     */
    public ArrivalBottlesView(String id) {
        super(id);
    }

    /**
     * @param id
     * @param model
     */
    public ArrivalBottlesView(String id, IModel<? extends List<? extends ArrivalBottle>> model) {
        super(id, model);
    }

    /**
     * @param id
     * @param list
     */
    public ArrivalBottlesView(String id, List<? extends ArrivalBottle> list) {
        super(id, list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(ListItem<ArrivalBottle> item) {
        item.add(new Label("bottle.wine.appellation.region.country.name"));
        item.add(new Label("bottle.wine.appellation.region.name"));
        item.add(new Label("bottle.wine.appellation.name"));
        item.add(new Label("bottle.wine.producer.name"));
        item.add(new Label("bottle.format.name"));
        item.add(new Label("quantity"));
        item.add(new WebMarkupContainer("remove").add(removeLink("removeBottle", item)));
    }

}

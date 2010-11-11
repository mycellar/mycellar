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
package fr.peralta.mycellar.interfaces.client.web.components.wine;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.interfaces.facades.stock.Bottle;

/**
 * @author speralta
 */
public class BottlesView extends PropertyListView<Bottle> {

    private static final long serialVersionUID = 201011071626L;

    /**
     * @param id
     */
    public BottlesView(String id) {
        super(id);
    }

    /**
     * @param id
     * @param model
     */
    public BottlesView(String id, IModel<? extends List<? extends Bottle>> model) {
        super(id, model);
    }

    /**
     * @param id
     * @param list
     */
    public BottlesView(String id, List<? extends Bottle> list) {
        super(id, list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(ListItem<Bottle> item) {
        item.add(new Label("label"));
        item.add(new Label("quantity"));
        item.add(new WebMarkupContainer("remove").add(removeLink("removeBottle", item)));
    }

}

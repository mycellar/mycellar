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
package fr.peralta.mycellar.interfaces.client.web.components.stock.data;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataViewBase;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Stock;

/**
 * @author speralta
 * 
 */
public class StockDataView extends DataViewBase<Stock> {

    private static final long serialVersionUID = 201109192009L;

    /**
     * @param id
     * @param searchFormModel
     */
    public StockDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, new StockDataProvider(searchFormModel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(Item<Stock> item) {
        item.setModel(new CompoundPropertyModel<Stock>(item.getModel()));
        item.add(new Label("bottle.wine.appellation.region.country.name"));
        item.add(new Label("bottle.wine.appellation.region.name"));
        item.add(new Label("bottle.wine.appellation.name"));
        item.add(new Label("bottle.wine.producer.name"));
        item.add(new Label("bottle.wine.name"));
        item.add(new Label("bottle.wine.type"));
        item.add(new Label("bottle.wine.color"));
        item.add(new Label("bottle.wine.vintage"));
        item.add(new Label("bottle.format.name"));
        item.add(new Label("quantity"));
    }

}

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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.StockOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 * 
 */
public class StockDataView extends AdvancedTable<Stock, StockOrderEnum> {

    private static final long serialVersionUID = 201109192009L;

    private static List<IColumn<Stock, StockOrderEnum>> getNewColumns() {
        List<IColumn<Stock, StockOrderEnum>> columns = new ArrayList<IColumn<Stock, StockOrderEnum>>();
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("country"),
                StockOrderEnum.COUNTRY_NAME, "bottle.wine.appellation.region.country.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("region"),
                StockOrderEnum.REGION_NAME, "bottle.wine.appellation.region.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("appellation"),
                StockOrderEnum.APPELLATION_NAME, "bottle.wine.appellation.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("producer"),
                StockOrderEnum.PRODUCER_NAME, "bottle.wine.producer.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("wine"),
                StockOrderEnum.WINE_NAME, "bottle.wine.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("type"),
                StockOrderEnum.TYPE, "bottle.wine.type"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("color"),
                StockOrderEnum.COLOR, "bottle.wine.color"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("vintage"),
                StockOrderEnum.WINE_VINTAGE, "bottle.wine.vintage") {
            private static final long serialVersionUID = 201111301732L;

            @Override
            public String getCssClass() {
                return "ca";
            }

        });
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("format"),
                StockOrderEnum.FORMAT_NAME, "bottle.format.name"));
        columns.add(new PropertyColumn<Stock, StockOrderEnum>(new ResourceModel("quantityAbrev"),
                StockOrderEnum.QUANTITY, "quantity") {
            private static final long serialVersionUID = 201111301732L;

            @Override
            public String getCssClass() {
                return "ca";
            }

        });
        return columns;
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public StockDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new StockDataProvider(searchFormModel), 30);
    }

}

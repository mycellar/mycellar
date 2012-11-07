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
package fr.peralta.mycellar.interfaces.client.web.components.wine.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 * 
 */
public class WineDataView extends AdvancedTable<Wine, WineOrderEnum> {

    private static final long serialVersionUID = 201109192009L;

    private static List<IColumn<Wine, WineOrderEnum>> getNewColumns() {
        List<IColumn<Wine, WineOrderEnum>> columns = new ArrayList<IColumn<Wine, WineOrderEnum>>();
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("country"),
                WineOrderEnum.COUNTRY_NAME, "appellation.region.country.name"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("region"),
                WineOrderEnum.REGION_NAME, "appellation.region.name"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("appellation"),
                WineOrderEnum.APPELLATION_NAME, "appellation.name"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("producer"),
                WineOrderEnum.PRODUCER_NAME, "producer.name"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("name"),
                WineOrderEnum.NAME, "name"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("type"),
                WineOrderEnum.TYPE, "type"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("color"),
                WineOrderEnum.COLOR, "color"));
        columns.add(new PropertyColumn<Wine, WineOrderEnum>(new ResourceModel("vintage"),
                WineOrderEnum.VINTAGE, "vintage") {
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
    public WineDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new WineDataProvider(searchFormModel), 30);
    }

}

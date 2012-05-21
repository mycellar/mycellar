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

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.repository.MovementOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 * 
 */
public class MovementDataView extends AdvancedTable<Movement> {

    private static final long serialVersionUID = 201109192009L;

    private static List<IColumn<Movement>> getNewColumns() {
        List<IColumn<Movement>> columns = new ArrayList<IColumn<Movement>>();
        columns.add(new PropertyColumn<Movement>(new ResourceModel("date"), MovementOrderEnum.DATE
                .name(), "date"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("cellar"),
                MovementOrderEnum.CELLAR_NAME.name(), "cellar.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("country"),
                MovementOrderEnum.COUNTRY_NAME.name(),
                "bottle.wine.appellation.region.country.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("region"),
                MovementOrderEnum.REGION_NAME.name(), "bottle.wine.appellation.region.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("appellation"),
                MovementOrderEnum.APPELLATION_NAME.name(), "bottle.wine.appellation.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("producer"),
                MovementOrderEnum.PRODUCER_NAME.name(), "bottle.wine.producer.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("wine"),
                MovementOrderEnum.WINE_NAME.name(), "bottle.wine.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("type"), MovementOrderEnum.TYPE
                .name(), "bottle.wine.type"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("color"),
                MovementOrderEnum.COLOR.name(), "bottle.wine.color"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("vintage"),
                MovementOrderEnum.WINE_VINTAGE.name(), "bottle.wine.vintage") {
            private static final long serialVersionUID = 201111301732L;

            @Override
            public String getCssClass() {
                return "ca";
            }

        });
        columns.add(new PropertyColumn<Movement>(new ResourceModel("format"),
                MovementOrderEnum.FORMAT_NAME.name(), "bottle.format.name"));
        columns.add(new PropertyColumn<Movement>(new ResourceModel("numberAbrev"),
                MovementOrderEnum.NUMBER.name(), "number") {
            private static final long serialVersionUID = 201111301732L;

            @Override
            public String getCssClass() {
                return "ca";
            }

        });
        columns.add(new AbstractColumn<Movement>(new ResourceModel("io")) {
            private static final long serialVersionUID = 201111290925L;

            @Override
            public void populateItem(Item<ICellPopulator<Movement>> cellItem, String componentId,
                    IModel<Movement> rowModel) {
                cellItem.add(new Label(componentId, rowModel.getObject() instanceof Input ? "E"
                        : "S"));
            }

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
    public MovementDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new MovementDataProvider(searchFormModel), 30);
    }

}

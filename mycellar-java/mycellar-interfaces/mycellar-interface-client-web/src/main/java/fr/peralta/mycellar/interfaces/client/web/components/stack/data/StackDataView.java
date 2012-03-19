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
package fr.peralta.mycellar.interfaces.client.web.components.stack.data;

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
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 */
public class StackDataView extends AdvancedTable<Stack> {

    private static final long serialVersionUID = 201111081449L;

    private static List<IColumn<Stack>> getNewColumns() {
        List<IColumn<Stack>> columns = new ArrayList<IColumn<Stack>>();
        columns.add(new PropertyColumn<Stack>(new ResourceModel("hashCode"), "hashCode"));
        columns.add(new AbstractColumn<Stack>(new ResourceModel("content")) {
            private static final long serialVersionUID = 201111290925L;

            @Override
            public void populateItem(Item<ICellPopulator<Stack>> cellItem, String componentId,
                    IModel<Stack> rowModel) {
                cellItem.add(new Label(componentId, rowModel.getObject().getStack()
                        .substring(0, rowModel.getObject().getStack().indexOf("\n"))));
            }
        });
        columns.add(new PropertyColumn<Stack>(new ResourceModel("count"), StackOrderEnum.COUNT
                .name(), "count"));
        columns.add(new AbstractColumn<Stack>(new ResourceModel("detail")) {
            private static final long serialVersionUID = 201111290925L;

            @Override
            public void populateItem(Item<ICellPopulator<Stack>> cellItem, String componentId,
                    IModel<Stack> rowModel) {
                cellItem.add(new StackDetailPanel(componentId, rowModel.getObject().getId()));
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
    public StackDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new StackDataProvider(searchFormModel), 20);
    }

}

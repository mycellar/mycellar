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
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.EditColumn;

/**
 * @author speralta
 * 
 */
public class CellarShareDataView extends AdvancedTable<CellarShare> {

    private static final long serialVersionUID = 201109192009L;

    private static List<IColumn<CellarShare>> getNewColumns() {
        List<IColumn<CellarShare>> columns = new ArrayList<IColumn<CellarShare>>();
        columns.add(new PropertyColumn<CellarShare>(new ResourceModel("email"),
                CellarShareOrderEnum.EMAIL.name(), "email"));
        columns.add(new PropertyColumn<CellarShare>(new ResourceModel("accessRight"),
                CellarShareOrderEnum.ACCESS_RIGHT.name(), "accessRight") {
            private static final long serialVersionUID = 201201301841L;

            @Override
            public String getCssClass() {
                return "ca";
            }
        });
        columns.add(new EditColumn<CellarShare>());
        return columns;
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public CellarShareDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new CellarShareDataProvider(searchFormModel), 30);
    }

}

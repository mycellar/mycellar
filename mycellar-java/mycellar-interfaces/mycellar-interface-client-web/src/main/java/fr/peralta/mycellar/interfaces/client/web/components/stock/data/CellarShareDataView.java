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
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.ActionsColumn;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 * 
 */
public class CellarShareDataView extends AdvancedTable<CellarShare, CellarShareOrderEnum> {

    private static final long serialVersionUID = 201109192009L;

    private static List<IColumn<CellarShare, CellarShareOrderEnum>> getNewColumns() {
        List<IColumn<CellarShare, CellarShareOrderEnum>> columns = new ArrayList<IColumn<CellarShare, CellarShareOrderEnum>>();
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(
                new ResourceModel("email"), CellarShareOrderEnum.EMAIL, "email"));
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(new ResourceModel(
                "accessRight"), CellarShareOrderEnum.ACCESS_RIGHT, "accessRight"));
        columns.add(new ActionsColumn<CellarShare, CellarShareOrderEnum>());
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

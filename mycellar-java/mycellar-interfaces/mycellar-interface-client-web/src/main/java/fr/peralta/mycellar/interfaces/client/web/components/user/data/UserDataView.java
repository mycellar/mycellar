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
package fr.peralta.mycellar.interfaces.client.web.components.user.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;

/**
 * @author speralta
 */
public class UserDataView extends AdvancedTable<User> {

    private static final long serialVersionUID = 201110110858L;

    private static List<IColumn<User>> getNewColumns() {
        List<IColumn<User>> columns = new ArrayList<IColumn<User>>();
        columns.add(new PropertyColumn<User>(new ResourceModel("email"),
                UserOrderEnum.EMAIL.name(), "email"));
        columns.add(new PropertyColumn<User>(new ResourceModel("firstname"),
                UserOrderEnum.FIRSTNAME.name(), "firstname"));
        columns.add(new PropertyColumn<User>(new ResourceModel("lastname"), UserOrderEnum.LASTNAME
                .name(), "lastname"));
        return columns;
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public UserDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, getNewColumns(), new UserDataProvider(searchFormModel), 30);
    }

}

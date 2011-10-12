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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataViewBase;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public class UserDataView extends DataViewBase<User> {

    private static final long serialVersionUID = 201110110858L;

    /**
     * @param id
     * @param searchFormModel
     */
    public UserDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, new UserDataProvider(searchFormModel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(Item<User> item) {
        item.setModel(new CompoundPropertyModel<User>(item.getModel()));
        item.add(new Label("email"));
        item.add(new Label("lastname"));
        item.add(new Label("firstname"));
    }

}

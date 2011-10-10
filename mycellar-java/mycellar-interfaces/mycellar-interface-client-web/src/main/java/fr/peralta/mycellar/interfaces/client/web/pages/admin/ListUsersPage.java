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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class ListUsersPage extends AdminSuperPage {

    private static final long serialVersionUID = 201111101705L;

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param parameters
     */
    public ListUsersPage(PageParameters parameters) {
        super(parameters);
        add(new ListView<User>("users", userServiceFacade.getAllUsers()) {
            private static final long serialVersionUID = 3683050941476201289L;

            @Override
            protected void populateItem(ListItem<User> item) {
                item.setModel(new CompoundPropertyModel<User>(item.getModelObject()));
                item.add(new Label("email"));
                item.add(new Label("firstname"));
                item.add(new Label("lastname"));
                item.add(new Label("password"));
            }
        });
    }

}

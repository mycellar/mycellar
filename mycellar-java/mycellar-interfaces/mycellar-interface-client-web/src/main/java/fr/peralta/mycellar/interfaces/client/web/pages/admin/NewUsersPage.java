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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.ActionsColumn;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;
import fr.peralta.mycellar.interfaces.client.web.components.user.data.NewUsersDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.user.UserPage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class NewUsersPage extends AdminSuperPage {

    private static final long serialVersionUID = 201212121300L;

    private static final Logger logger = LoggerFactory.getLogger(NewUsersPage.class);

    /**
     * @param parameters
     */
    public NewUsersPage(PageParameters parameters) {
        super(parameters);
        List<IColumn<User, UserOrderEnum>> columns = new ArrayList<IColumn<User, UserOrderEnum>>();
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("email"),
                UserOrderEnum.EMAIL, "email"));
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("firstname"),
                UserOrderEnum.FIRSTNAME, "firstname"));
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("lastname"),
                UserOrderEnum.LASTNAME, "lastname"));
        columns.add(new ActionsColumn<User, UserOrderEnum>(true, false, false));
        add(new AdvancedTable<User, UserOrderEnum>("list", columns, new NewUsersDataProvider(), 50));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof ActionLink) {
                    User user = ((User) ((ActionLink) event.getSource()).getParent()
                            .getDefaultModelObject());
                    setResponsePage(UserPage.class, UserPage.getPageParameters(user));
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}

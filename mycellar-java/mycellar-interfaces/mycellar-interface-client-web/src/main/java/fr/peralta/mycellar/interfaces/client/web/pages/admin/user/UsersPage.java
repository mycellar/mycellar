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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.user.data.UserDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class UsersPage extends AbstractListPage<User, UserOrderEnum, UserOrder> {

    private static final long serialVersionUID = 201111101705L;

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param parameters
     */
    public UsersPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MultipleSortableDataProvider<User, UserOrderEnum, UserOrder> getDataProvider() {
        return new UserDataProvider();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<IColumn<User, UserOrderEnum>> getColumns() {
        List<IColumn<User, UserOrderEnum>> columns = new ArrayList<IColumn<User, UserOrderEnum>>();
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("email"),
                UserOrderEnum.EMAIL, "email"));
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("firstname"),
                UserOrderEnum.FIRSTNAME, "firstname"));
        columns.add(new PropertyColumn<User, UserOrderEnum>(new ResourceModel("lastname"),
                UserOrderEnum.LASTNAME, "lastname"));
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageParameters getEditPageParameters(User object) {
        if (object == null) {
            return UserPage.getPageParametersForCreation();
        } else {
            return UserPage.getPageParameters(object);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditPage<User>> getEditPageClass() {
        return UserPage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteObject(User object) throws BusinessException {
        userServiceFacade.deleteUser(object);
    }

}

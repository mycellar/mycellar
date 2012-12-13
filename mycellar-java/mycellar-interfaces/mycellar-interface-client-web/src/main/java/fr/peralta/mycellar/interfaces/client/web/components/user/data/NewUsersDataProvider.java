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

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 * 
 */
public class NewUsersDataProvider extends
        MultipleSortableDataProvider<User, UserOrderEnum, UserOrder> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * Default constructor.
     */
    public NewUsersDataProvider() {
        super(new UserOrder().add(UserOrderEnum.LASTNAME, OrderWayEnum.ASC).add(
                UserOrderEnum.FIRSTNAME, OrderWayEnum.ASC));
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends User> iterator(long first, long count) {
        return userServiceFacade.getNewUsers(getState().getOrders(), first, count).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return (int) userServiceFacade.countNewUsers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<User> model(User object) {
        return new Model<User>(object);
    }

}

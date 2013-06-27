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
package fr.mycellar.interfaces.facade.web.domain.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
@Service
@Path("/domain/user")
public class UserWebService {

    private UserServiceFacade userServiceFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users/count")
    public long countUsers() {
        return userServiceFacade.countUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users/list/{first}/{count}")
    public List<User> getUsers(@PathParam("{first}") long first, @PathParam("{count}") long count) {
        UserOrder orders = new UserOrder();
        return userServiceFacade.getUsers(orders, first, count);
    }

    /**
     * @param userServiceFacade
     *            the userServiceFacade to set
     */
    @Autowired
    public void setUserServiceFacade(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

}

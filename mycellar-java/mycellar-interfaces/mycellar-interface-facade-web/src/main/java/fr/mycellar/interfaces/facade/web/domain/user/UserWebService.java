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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.facade.web.domain.FilterCouple;
import fr.mycellar.interfaces.facade.web.domain.ListWithCount;
import fr.mycellar.interfaces.facade.web.domain.OrderCouple;
import fr.mycellar.interfaces.facade.web.domain.SearchParametersUtil;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
@Service
@Path("/domain/user")
public class UserWebService {

    private UserServiceFacade userServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // USER
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    public ListWithCount<User> getUsers(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, User.class);
        List<User> users;
        if (count == 0) {
            users = new ArrayList<>();
        } else {
            users = userServiceFacade.getUsers(searchParameters);
        }
        return new ListWithCount<>(userServiceFacade.countUsers(searchParameters), users);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{id}")
    public User getUserById(@PathParam("id") int userId) {
        return userServiceFacade.getUserById(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    public User saveUser(User user) throws BusinessException {
        return userServiceFacade.saveUser(user);
    }

    // BEAN METHODS

    /**
     * @param userServiceFacade
     *            the userServiceFacade to set
     */
    @Inject
    public void setUserServiceFacade(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

    /**
     * @param searchParametersUtil
     *            the searchParametersUtil to set
     */
    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}

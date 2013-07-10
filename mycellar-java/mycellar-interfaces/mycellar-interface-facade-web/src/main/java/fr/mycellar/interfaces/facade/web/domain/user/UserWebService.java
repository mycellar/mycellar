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

import static fr.mycellar.interfaces.facade.web.domain.MetamodelUtil.*;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.facade.web.domain.ListWithCount;
import fr.mycellar.interfaces.facade.web.domain.OrderCouple;
import fr.peralta.mycellar.domain.shared.repository.OrderBy;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    public ListWithCount<User> getUsers(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), User.class)));
        }
        return new ListWithCount<>(userServiceFacade.countUsers(searchParameters), userServiceFacade.getUsers(searchParameters));
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

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
package fr.mycellar.interfaces.web.services.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stack.Stack;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.stack.StackServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/stack")
public class StackDomainWebService {

    private StackServiceFacade stackServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stacks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Stack> getStacks(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Stack.class);
        List<Stack> stacks;
        if (count == 0) {
            stacks = new ArrayList<>();
        } else {
            stacks = stackServiceFacade.getStacks(searchParameters);
        }
        return new ListWithCount<>(stackServiceFacade.countStacks(searchParameters), stacks);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stack/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Stack getStackById(@PathParam("id") int stackId) {
        return stackServiceFacade.getStackById(stackId);
    }

    @DELETE
    @Path("stack/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteStackById(@PathParam("id") int stackId) throws BusinessException {
        stackServiceFacade.deleteStack(stackServiceFacade.getStackById(stackId));
    }

    @DELETE
    @Path("stacks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAllStacks() throws BusinessException {
        stackServiceFacade.deleteAllStacks();
    }

    // BEAN METHODS

    @Inject
    public void setStackServiceFacade(StackServiceFacade stackServiceFacade) {
        this.stackServiceFacade = stackServiceFacade;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}

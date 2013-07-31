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
package fr.mycellar.interfaces.facade.web.admin.domain.stack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.facade.web.FilterCouple;
import fr.mycellar.interfaces.facade.web.ListWithCount;
import fr.mycellar.interfaces.facade.web.OrderCouple;
import fr.mycellar.interfaces.facade.web.SearchParametersUtil;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
@Service
@Path("/domain/stack")
public class StackWebService {

    private StackServiceFacade stackServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stacks")
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
    public Stack getStackById(@PathParam("id") int stackId) {
        return stackServiceFacade.getStackById(stackId);
    }

    @DELETE
    @Path("stack/{id}")
    public void deleteStackById(@PathParam("id") int stackId) throws BusinessException {
        stackServiceFacade.deleteStack(stackServiceFacade.getStackById(stackId));
    }

    @DELETE
    @Path("stacks")
    public void deleteAllStacks() throws BusinessException {
        stackServiceFacade.deleteAllStacks();
    }

    // BEAN METHODS

    /**
     * @param stackServiceFacade
     *            the stackServiceFacade to set
     */
    @Inject
    public void setStackServiceFacade(StackServiceFacade stackServiceFacade) {
        this.stackServiceFacade = stackServiceFacade;
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

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
package fr.mycellar.interfaces.facade.web.domain.stack;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;
import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
@Service
@Path("/domain/stack")
public class StackWebService {

    private StackServiceFacade stackServiceFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stacks/count")
    public long countStacks() {
        return stackServiceFacade.countStacks();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stacks/list")
    public List<Stack> getStacks(@QueryParam("first") long first, @QueryParam("count") long count) {
        StackOrder orders = new StackOrder();
        return stackServiceFacade.getStacks(orders, first, count);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stack/{id}")
    public Stack getStackById(@PathParam("id") int stackId) {
        return stackServiceFacade.getStackById(stackId);
    }

    /**
     * @param stackServiceFacade
     *            the stackServiceFacade to set
     */
    @Autowired
    public void setStackServiceFacade(StackServiceFacade stackServiceFacade) {
        this.stackServiceFacade = stackServiceFacade;
    }

}
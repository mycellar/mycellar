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
package fr.mycellar.interfaces.web.services.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/admin/tools")
public class AdminToolsWebService {

    private WineServiceFacade wineServiceFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createVintages")
    public List<Wine> createVintages(Wine wine, @QueryParam("from") int from, @QueryParam("to") int to) throws BusinessException {
        if (to < from) {
            throw new BusinessException(BusinessError.OTHER_00001);
        }
        return wineServiceFacade.createVintages(wine, from, to);
    }

    // BEANS METHODS

    /**
     * @param wineServiceFacade
     *            the wineServiceFacade to set
     */
    @Inject
    public void setWineServiceFacade(WineServiceFacade wineServiceFacade) {
        this.wineServiceFacade = wineServiceFacade;
    }

}

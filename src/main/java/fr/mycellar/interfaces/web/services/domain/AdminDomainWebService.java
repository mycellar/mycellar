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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.admin.AdministrationServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/admin")
public class AdminDomainWebService {

    private AdministrationServiceFacade administrationServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // BOOKING
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("configurations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Configuration> getConfigurations(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Configuration.class);
        List<Configuration> configurations;
        if (count == 0) {
            configurations = new ArrayList<>();
        } else {
            configurations = administrationServiceFacade.getConfigurations(searchParameters);
        }
        return new ListWithCount<>(administrationServiceFacade.countConfigurations(searchParameters), configurations);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("configuration/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Configuration getConfigurationById(@PathParam("id") int configurationId) {
        return administrationServiceFacade.getConfigurationById(configurationId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("configuration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Configuration saveConfiguration(Configuration configuration) throws BusinessException {
        return administrationServiceFacade.saveConfiguration(configuration);
    }

    // BEANS Methods

    /**
     * @param administrationServiceFacade
     *            the administrationServiceFacade to set
     */
    @Inject
    public void setAdministrationServiceFacade(AdministrationServiceFacade administrationServiceFacade) {
        this.administrationServiceFacade = administrationServiceFacade;
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

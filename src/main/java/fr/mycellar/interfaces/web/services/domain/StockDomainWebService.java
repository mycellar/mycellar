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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/stock")
public class StockDomainWebService {

    private StockServiceFacade stockServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // CELLAR
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Cellar> getCellars(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Cellar.class);
        List<Cellar> cellars;
        if (count == 0) {
            cellars = new ArrayList<>();
        } else {
            cellars = stockServiceFacade.getCellars(searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countCellars(searchParameters), cellars);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Cellar getCellarById(@PathParam("id") int cellarId) {
        return stockServiceFacade.getCellarById(cellarId);
    }

    @DELETE
    @Path("cellar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCellarById(@PathParam("id") int cellarId) throws BusinessException {
        stockServiceFacade.deleteCellar(stockServiceFacade.getCellarById(cellarId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Cellar saveCellar(Cellar cellar) throws BusinessException {
        return stockServiceFacade.saveCellar(cellar);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateCellar(Cellar cellar) throws BusinessException {
        stockServiceFacade.validateCellar(cellar);
    }

    // --------------
    // CELLAR SHARE
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<CellarShare> getCellarShares(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, CellarShare.class);
        List<CellarShare> cellarShares;
        if (count == 0) {
            cellarShares = new ArrayList<>();
        } else {
            cellarShares = stockServiceFacade.getCellarShares(searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countCellarShares(searchParameters), cellarShares);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShare/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CellarShare getCellarShareById(@PathParam("id") int cellarShareId) {
        return stockServiceFacade.getCellarShareById(cellarShareId);
    }

    @DELETE
    @Path("cellarShare/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCellarShareById(@PathParam("id") int cellarShareId) throws BusinessException {
        stockServiceFacade.deleteCellarShare(stockServiceFacade.getCellarShareById(cellarShareId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShare")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException {
        return stockServiceFacade.saveCellarShare(cellarShare);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellarShare")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateCellarShare(CellarShare cellarShare) throws BusinessException {
        stockServiceFacade.validateCellarShare(cellarShare);
    }

    // BEANS Methods

    @Inject
    public void setStockServiceFacade(StockServiceFacade stockServiceFacade) {
        this.stockServiceFacade = stockServiceFacade;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}

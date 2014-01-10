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
package fr.mycellar.interfaces.web.services.stock;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Movement_;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.mycellar.interfaces.web.security.CurrentUserService;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/stock")
public class StockWebService {

    private StockServiceFacade stockServiceFacade;

    private CurrentUserService currentUserService;

    private SearchParametersUtil searchParametersUtil;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Cellar> getCellarsForCurrentUser() {
        return new ListWithCount<>(stockServiceFacade.getCellars(currentUserService.getCurrentUser()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movements")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Movement> getMovements(@QueryParam("cellarId") Integer cellarId, @QueryParam("first") int first, @QueryParam("count") int count,
            @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        if (!stockServiceFacade.hasReadRight(cellarId, currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("No read access to this cellar.");
        }
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Movement.class);
        searchParameters.property(Movement_.cellar, Cellar_.id, cellarId);
        List<Movement> movements;
        if (count == 0) {
            movements = new ArrayList<>();
        } else {
            movements = stockServiceFacade.getMovements(searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countMovements(searchParameters), movements);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stocks")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Stock> getStocks(@QueryParam("cellarId") Integer cellarId, @QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        if (!stockServiceFacade.hasReadRight(cellarId, currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("No read access to this cellar.");
        }
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Stock.class);
        searchParameters.property(Stock_.cellar, Cellar_.id, cellarId);
        List<Stock> stocks;
        if (count == 0) {
            stocks = new ArrayList<>();
        } else {
            stocks = stockServiceFacade.getStocks(searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countStocks(searchParameters), stocks);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<CellarShare> getCellarShares(@QueryParam("cellarId") Integer cellarId, @QueryParam("first") int first, @QueryParam("count") int count,
            @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        if (!stockServiceFacade.isOwner(cellarId, currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("Current user isn't the owner of the cellar.");
        }
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, CellarShare.class);
        searchParameters.property(CellarShare_.cellar, Cellar_.id, cellarId);
        List<CellarShare> cellarShares;
        if (count == 0) {
            cellarShares = new ArrayList<>();
        } else {
            cellarShares = stockServiceFacade.getCellarShares(searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countCellarShares(searchParameters), cellarShares);
    }

    @Inject
    public void setStockServiceFacade(StockServiceFacade stockServiceFacade) {
        this.stockServiceFacade = stockServiceFacade;
    }

    @Inject
    public void setCurrentUserService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}

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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jpasearch.repository.query.ResultParameters;
import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.ResultBuilder;
import jpasearch.repository.query.builder.SearchBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Arrival;
import fr.mycellar.domain.stock.Bottle_;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.stock.Drink;
import fr.mycellar.domain.stock.DrinkBottle;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Movement_;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.Wine_;
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
    public ListWithCount<Cellar> getCellarsForCurrentUser( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders, //
            @QueryParam("like") String term) {
        User user = currentUserService.getCurrentUser();
        SearchParameters<Cellar> searchParameters = searchParametersUtil.getSearchBuilder(first, count, filters, orders, Cellar.class) //
                .disjunction() //
                .on(Cellar_.owner).equalsTo(user) //
                .on(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail()) //
                .and().build();
        List<Cellar> cellars;
        if (count == 0) {
            cellars = new ArrayList<>();
        } else {
            cellars = stockServiceFacade.getCellarsLike(term, searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countCellarsLike(term, searchParameters), cellars);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public Cellar getCellarById(@PathParam("id") int cellarId) {
        User user = currentUserService.getCurrentUser();
        if (!stockServiceFacade.hasReadRight(cellarId, user.getEmail())) {
            throw new AccessDeniedException("Cannot access to this cellar.");
        }
        return stockServiceFacade.getCellarById(cellarId);
    }

    @DELETE
    @Path("cellars/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void deleteCellarById(@PathParam("id") int cellarId) throws BusinessException {
        User user = currentUserService.getCurrentUser();
        if (!stockServiceFacade.isOwner(cellarId, user.getEmail())) {
            throw new AccessDeniedException("Cannot delete this cellar.");
        }
        stockServiceFacade.deleteCellar(stockServiceFacade.getCellarById(cellarId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public Cellar saveCellar(@PathParam("id") int id, Cellar cellar) throws BusinessException {
        User user = currentUserService.getCurrentUser();
        if (!stockServiceFacade.isOwner(id, user.getEmail())) {
            throw new AccessDeniedException("Cannot modify this cellar.");
        }
        if ((id == cellar.getId()) && (stockServiceFacade.getCellarById(id) != null)) {
            return stockServiceFacade.saveCellar(cellar);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public Cellar saveCellar(Cellar cellar) throws BusinessException {
        User user = currentUserService.getCurrentUser();
        if (cellar.getId() == null) {
            cellar.setOwner(user);
            return stockServiceFacade.saveCellar(cellar);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellar")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void validateCellar(Cellar cellar) throws BusinessException {
        if (cellar.getOwner() != null) {
            User user = currentUserService.getCurrentUser();
            if (!user.equals(cellar.getOwner())) {
                throw new AccessDeniedException("Cannot modify this cellar.");
            }
        }
        stockServiceFacade.validateCellar(cellar);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movements")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Movement> getMovements(@QueryParam("cellarId") Integer cellarId, @QueryParam("first") int first, @QueryParam("count") int count,
            @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        if ((cellarId != null) && !stockServiceFacade.hasReadRight(cellarId, currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("No read access to this cellar.");
        }
        SearchBuilder<Movement> searchBuilder = searchParametersUtil.getSearchBuilder(first, count, filters, orders, Movement.class);
        if (cellarId != null) {
            searchBuilder.distinct().on(Movement_.cellar).to(Cellar_.id).equalsTo(cellarId);
        } else {
            User user = currentUserService.getCurrentUser();
            searchBuilder.distinct().disjunction() //
            .on(Movement_.cellar).to(Cellar_.owner).equalsTo(user) //
            .on(Movement_.cellar).to(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail());
        }
        SearchParameters<Movement> searchParameters = searchBuilder.build();
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
    @Path("wines")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Wine> getWinesFromStocksForCellarLike( //
            @QueryParam("cellarId") Integer cellarId, //
            @QueryParam("input") String input, //
            @QueryParam("first") int first, //
            @QueryParam("count") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders) {
        if ((cellarId != null) && !stockServiceFacade.hasReadRight(cellarId, currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("No read access to this cellar.");
        }
        SearchBuilder<Stock> searchBuilder = searchParametersUtil.getSearchBuilder(first, count, filters, orders, Stock.class);
        if (cellarId != null) {
            searchBuilder.distinct().on(Stock_.cellar).to(Cellar_.id).equalsTo(cellarId);
        } else {
            User user = currentUserService.getCurrentUser();
            searchBuilder.distinct().disjunction() //
            .on(Stock_.cellar).to(Cellar_.owner).equalsTo(user) //
            .on(Stock_.cellar).to(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail());
        }
        if (StringUtils.isNotBlank(input)) {
            searchBuilder.fullText(Stock_.bottle).to(Bottle_.wine).to(Wine_.appellation).to(Appellation_.region).to(NamedEntity_.name) //
            .andOn(Stock_.bottle).to(Bottle_.wine).to(Wine_.appellation).to(NamedEntity_.name) //
            .andOn(Stock_.bottle).to(Bottle_.wine).to(Wine_.producer).to(NamedEntity_.name) //
            .andOn(Stock_.bottle).to(Bottle_.wine).to(NamedEntity_.name) //
            .andOn(Stock_.bottle).to(Bottle_.wine).to(Wine_.vintage) //
            .andOn(Stock_.bottle).to(Bottle_.format).to(NamedEntity_.name) //
            .andMode().search(input);
        }
        SearchParameters<Stock> searchParameters = searchBuilder.build();
        ResultParameters<Stock, Wine> resultParameters = new ResultBuilder<>(Stock_.bottle).to(Bottle_.wine).build();
        List<Wine> wines;
        if (count == 0) {
            wines = new ArrayList<>();
        } else {
            wines = stockServiceFacade.getFromStocks(searchParameters, resultParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countFromStocks(searchParameters, resultParameters), wines);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stocks")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public ListWithCount<Stock> getStocksForWine(@QueryParam("wineId") Integer wineId, @QueryParam("first") int first, @QueryParam("count") int count,
            @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        User user = currentUserService.getCurrentUser();
        SearchParameters<Stock> searchParameters = searchParametersUtil.getSearchBuilder(first, count, filters, orders, Stock.class) //
                .on(Stock_.bottle).to(Bottle_.wine).to(Wine_.id).equalsTo(wineId) //
                .disjunction() //
                .on(Stock_.cellar).to(Cellar_.owner).equalsTo(user) //
                .on(Stock_.cellar).to(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail()) //
                .and().build();
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
    public ListWithCount<CellarShare> getCellarSharesForCurrentUser( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders, //
            @QueryParam("like") String term) {
        User user = currentUserService.getCurrentUser();
        SearchParameters<CellarShare> searchParameters = searchParametersUtil.getSearchBuilder(first, count, filters, orders, CellarShare.class) //
                .distinct() //
                .disjunction() //
                .on(CellarShare_.cellar).to(Cellar_.owner).equalsTo(user) //
                .on(CellarShare_.cellar).to(Cellar_.shares).to(CellarShare_.email).equalsTo(user.getEmail()) //
                .and().build();
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
    @Path("cellarShares/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public CellarShare getCellarShareById(@PathParam("id") int cellarShareId) {
        User user = currentUserService.getCurrentUser();
        CellarShare cellarShare = stockServiceFacade.getCellarShareById(cellarShareId);
        if ((cellarShare != null) && !user.equals(cellarShare.getCellar().getOwner())) {
            throw new AccessDeniedException("No access to this cellar share.");
        }
        return cellarShare;
    }

    @DELETE
    @Path("cellarShares/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void deleteCellarShareById(@PathParam("id") int cellarShareId) throws BusinessException {
        CellarShare cellarShare = getCellarShareById(cellarShareId);
        stockServiceFacade.deleteCellarShare(cellarShare);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares/{id}")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public CellarShare saveCellarShare(@PathParam("id") int id, CellarShare cellarShare) throws BusinessException {
        CellarShare inBase = getCellarShareById(id);
        if ((id == cellarShare.getId()) && (inBase != null)) {
            return stockServiceFacade.saveCellarShare(cellarShare);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException {
        User user = currentUserService.getCurrentUser();
        if (!user.equals(cellarShare.getCellar().getOwner())) {
            throw new AccessDeniedException("Cannot modify a cellar share which is not owned by user.");
        }
        if (cellarShare.getId() == null) {
            return stockServiceFacade.saveCellarShare(cellarShare);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellarShare")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void validateCellarShare(CellarShare cellarShare) throws BusinessException {
        User user = currentUserService.getCurrentUser();
        if (!user.equals(cellarShare.getCellar().getOwner())) {
            throw new AccessDeniedException("Cannot modify a cellar share which is not owned by user.");
        }
        stockServiceFacade.validateCellarShare(cellarShare);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("arrival")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void arrival(Arrival arrival) throws BusinessException {
        if (!stockServiceFacade.hasModifyRight(arrival.getCellar().getId(), currentUserService.getCurrentUserEmail())) {
            throw new AccessDeniedException("Current user isn't the owner of the cellar.");
        }
        stockServiceFacade.arrival(arrival);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("drink")
    @PreAuthorize("hasRole('ROLE_CELLAR')")
    public void drink(Drink drink) throws BusinessException {
        for (DrinkBottle drinkBottle : drink.getDrinkBottles()) {
            if (!stockServiceFacade.hasModifyRight(drinkBottle.getCellar().getId(), currentUserService.getCurrentUserEmail())) {
                throw new AccessDeniedException("Current user isn't the owner of the cellar.");
            }
        }
        stockServiceFacade.drink(drink);
    }

    // BEANS

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

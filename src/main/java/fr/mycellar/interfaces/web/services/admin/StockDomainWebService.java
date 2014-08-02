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

import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Stock;
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
@Path("/admin/domain/stock")
public class StockDomainWebService {

    private StockServiceFacade stockServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // CELLAR
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars")
    public ListWithCount<Cellar> getCellars( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters<Cellar> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, Cellar.class);
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
    @Path("cellars/{id}")
    public Cellar getCellarById(@PathParam("id") int cellarId) {
        return stockServiceFacade.getCellarById(cellarId);
    }

    @DELETE
    @Path("cellars/{id}")
    public void deleteCellarById(@PathParam("id") int cellarId) throws BusinessException {
        stockServiceFacade.deleteCellar(stockServiceFacade.getCellarById(cellarId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars/{id}")
    public Cellar saveCellar(@PathParam("id") Integer id, Cellar cellar) throws BusinessException {
        if ((id == cellar.getId()) && (stockServiceFacade.getCellarById(id) != null)) {
            return stockServiceFacade.saveCellar(cellar);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars")
    public Cellar saveCellar(Cellar cellar) throws BusinessException {
        if (cellar.getId() == null) {
            return stockServiceFacade.saveCellar(cellar);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellar")
    public void validateCellar(Cellar cellar) throws BusinessException {
        stockServiceFacade.validateCellar(cellar);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellars/like")
    public ListWithCount<Cellar> getCellarsLike( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("input") String input, //
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters<Cellar> searchParameters = searchParametersUtil.getSearchParameters(first, count, new ArrayList<FilterCouple>(), orders, Cellar.class);
        List<Cellar> cellars;
        if (count == 0) {
            cellars = new ArrayList<>();
        } else {
            cellars = stockServiceFacade.getCellarsLike(input, searchParameters);
        }
        return new ListWithCount<>(stockServiceFacade.countCellarsLike(input, searchParameters), cellars);
    }

    // --------------
    // CELLAR SHARE
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares")
    public ListWithCount<CellarShare> getCellarShares(//
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters<CellarShare> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, CellarShare.class);
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
    public CellarShare getCellarShareById(@PathParam("id") int cellarShareId) {
        return stockServiceFacade.getCellarShareById(cellarShareId);
    }

    @DELETE
    @Path("cellarShares/{id}")
    public void deleteCellarShareById(@PathParam("id") int cellarShareId) throws BusinessException {
        stockServiceFacade.deleteCellarShare(stockServiceFacade.getCellarShareById(cellarShareId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares/{id}")
    public CellarShare saveCellarShare(@PathParam("id") Integer id, CellarShare cellarShare) throws BusinessException {
        if ((id == cellarShare.getId()) && (stockServiceFacade.getCellarShareById(id) != null)) {
            return stockServiceFacade.saveCellarShare(cellarShare);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cellarShares")
    public CellarShare saveCellarShare(CellarShare cellarShare) throws BusinessException {
        if (cellarShare.getId() == null) {
            return stockServiceFacade.saveCellarShare(cellarShare);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCellarShare")
    public void validateCellarShare(CellarShare cellarShare) throws BusinessException {
        stockServiceFacade.validateCellarShare(cellarShare);
    }

    // --------------
    // STOCK
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stocks")
    public ListWithCount<Stock> getStocks( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters<Stock> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, Stock.class);
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
    @Path("stocks/{id}")
    public Stock getStockById(@PathParam("id") int stockId) {
        return stockServiceFacade.getStockById(stockId);
    }

    @DELETE
    @Path("stocks/{id}")
    public void deleteStockById(@PathParam("id") int stockId) throws BusinessException {
        stockServiceFacade.deleteStock(stockServiceFacade.getStockById(stockId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stocks/{id}")
    public Stock saveStock(@PathParam("id") Integer id, Stock stock) throws BusinessException {
        if ((id == stock.getId()) && (stockServiceFacade.getStockById(id) != null)) {
            return stockServiceFacade.saveStock(stock);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stocks")
    public Stock saveStock(Stock stock) throws BusinessException {
        if (stock.getId() == null) {
            return stockServiceFacade.saveStock(stock);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateStock")
    public void validateStock(Stock stock) throws BusinessException {
        stockServiceFacade.validateStock(stock);
    }

    // --------------
    // Movement
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movements")
    public ListWithCount<Movement> getMovements( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters<Movement> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, Movement.class);
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
    @Path("movements/{id}")
    public Movement getMovementById(@PathParam("id") int movementId) {
        return stockServiceFacade.getMovementById(movementId);
    }

    @DELETE
    @Path("movements/{id}")
    public void deleteMovementById(@PathParam("id") int movementId) throws BusinessException {
        stockServiceFacade.deleteMovement(stockServiceFacade.getMovementById(movementId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movements/{id}")
    public Movement saveMovement(@PathParam("id") Integer id, Movement movement) throws BusinessException {
        if ((id == movement.getId()) && (stockServiceFacade.getMovementById(id) != null)) {
            return stockServiceFacade.saveMovement(movement);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movements")
    public Movement saveMovement(Movement movement) throws BusinessException {
        if (movement.getId() == null) {
            return stockServiceFacade.saveMovement(movement);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateMovement")
    public void validateMovement(Movement movement) throws BusinessException {
        stockServiceFacade.validateMovement(movement);
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

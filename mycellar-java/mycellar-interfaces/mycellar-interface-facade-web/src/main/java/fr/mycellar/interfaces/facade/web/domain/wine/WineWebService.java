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
package fr.mycellar.interfaces.facade.web.domain.wine;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.facade.web.domain.OrderCouple;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.repository.CountryOrder;
import fr.peralta.mycellar.domain.wine.repository.CountryOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
@Service
@Path("/domain/wine")
public class WineWebService {

    private WineServiceFacade wineServiceFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wines/count")
    public long countWines() {
        return wineServiceFacade.countWines(new SearchForm());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wines/list")
    public List<Wine> getWines(@QueryParam("first") long first, @QueryParam("count") long count,
            @QueryParam("sort") List<WineOrderCouple> orders) {
        WineOrder wineOrder = new WineOrder();
        for (OrderCouple<WineOrderEnum> order : orders) {
            wineOrder.add(order.getOrder(), order.getWay());
        }
        return wineServiceFacade.getWines(new SearchForm(), wineOrder, first, count);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wine/{id}")
    public Wine getWineById(@PathParam("id") int countryId) {
        return wineServiceFacade.getWineById(countryId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("wine")
    public void saveWine(Wine wine) throws BusinessException {
        wineServiceFacade.saveWine(wine);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("countries/count")
    public long countCountries() {
        return wineServiceFacade.countCountries(new SearchForm());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("countries/list")
    public List<Country> getCountries(@QueryParam("first") long first,
            @QueryParam("count") long count, @QueryParam("sort") List<CountryOrderCouple> orders) {
        CountryOrder countryOrder = new CountryOrder();
        for (OrderCouple<CountryOrderEnum> order : orders) {
            countryOrder.add(order.getOrder(), order.getWay());
        }
        return wineServiceFacade.getCountries(new SearchForm(), countryOrder, first, count);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("country/{id}")
    public Country getCountryById(@PathParam("id") int countryId) {
        return wineServiceFacade.getCountryById(countryId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("country")
    public void saveCountry(Country country) throws BusinessException {
        wineServiceFacade.saveCountry(country);
    }

    /**
     * @param wineServiceFacade
     *            the wineServiceFacade to set
     */
    @Autowired
    public void setWineServiceFacade(WineServiceFacade wineServiceFacade) {
        this.wineServiceFacade = wineServiceFacade;
    }

}

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
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.wine.WineServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/wine")
public class WineDomainWebService {

    private WineServiceFacade wineServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // COUNTRY
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("countries")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Country> getCountries(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Country.class);
        List<Country> countries;
        if (count == 0) {
            countries = new ArrayList<>();
        } else {
            countries = wineServiceFacade.getCountries(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countCountries(searchParameters), countries);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Country getCountryById(@PathParam("id") int countryId) {
        return wineServiceFacade.getCountryById(countryId);
    }

    @DELETE
    @Path("country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCountryById(@PathParam("id") int countryId) throws BusinessException {
        wineServiceFacade.deleteCountry(wineServiceFacade.getCountryById(countryId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Country saveCountry(Country country) throws BusinessException {
        return wineServiceFacade.saveCountry(country);
    }

    // --------------
    // APPELLATION
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Appellation> getAppellation(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Appellation.class);
        List<Appellation> appellations;
        if (count == 0) {
            appellations = new ArrayList<>();
        } else {
            appellations = wineServiceFacade.getAppellations(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countAppellations(searchParameters), appellations);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellation/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Appellation getAppellationById(@PathParam("id") int appellationId) {
        return wineServiceFacade.getAppellationById(appellationId);
    }

    @DELETE
    @Path("appellation/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAppellationById(@PathParam("id") int appellationId) throws BusinessException {
        wineServiceFacade.deleteAppellation(wineServiceFacade.getAppellationById(appellationId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Appellation saveAppellation(Appellation appellation) throws BusinessException {
        return wineServiceFacade.saveAppellation(appellation);
    }

    // --------------
    // PRODUCER
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("producers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Producer> getProducers(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Producer.class);
        List<Producer> producers;
        if (count == 0) {
            producers = new ArrayList<>();
        } else {
            producers = wineServiceFacade.getProducers(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countProducers(searchParameters), producers);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("producer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Producer getProducerById(@PathParam("id") int producerId) {
        return wineServiceFacade.getProducerById(producerId);
    }

    @DELETE
    @Path("producer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProducerById(@PathParam("id") int producerId) throws BusinessException {
        wineServiceFacade.deleteProducer(wineServiceFacade.getProducerById(producerId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("producer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Producer saveProducer(Producer producer) throws BusinessException {
        return wineServiceFacade.saveProducer(producer);
    }

    // --------------
    // REGION
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("regions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Region> getRegions(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Region.class);
        List<Region> regions;
        if (count == 0) {
            regions = new ArrayList<>();
        } else {
            regions = wineServiceFacade.getRegions(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countRegions(searchParameters), regions);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("region/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Region getRegionById(@PathParam("id") int regionId) {
        return wineServiceFacade.getRegionById(regionId);
    }

    @DELETE
    @Path("region/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRegionById(@PathParam("id") int regionId) throws BusinessException {
        wineServiceFacade.deleteRegion(wineServiceFacade.getRegionById(regionId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("region")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveRegion(Region region) throws BusinessException {
        wineServiceFacade.saveRegion(region);
    }

    // --------------
    // WINE
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wines")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Wine> getWines(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Wine.class);
        List<Wine> wines;
        if (count == 0) {
            wines = new ArrayList<>();
        } else {
            wines = wineServiceFacade.getWines(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countWines(searchParameters), wines);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wine/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Wine getWineById(@PathParam("id") int countryId) {
        return wineServiceFacade.getWineById(countryId);
    }

    @DELETE
    @Path("wine/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteWineById(@PathParam("id") int wineId) throws BusinessException {
        wineServiceFacade.deleteWine(wineServiceFacade.getWineById(wineId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("wine")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveWine(Wine wine) throws BusinessException {
        wineServiceFacade.saveWine(wine);
    }

    // BEANS Methods

    /**
     * @param wineServiceFacade
     *            the wineServiceFacade to set
     */
    @Inject
    public void setWineServiceFacade(WineServiceFacade wineServiceFacade) {
        this.wineServiceFacade = wineServiceFacade;
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

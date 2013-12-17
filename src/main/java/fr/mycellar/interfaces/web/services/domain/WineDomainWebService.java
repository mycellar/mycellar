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
import fr.mycellar.domain.wine.Format;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateCountry(Country country) throws BusinessException {
        wineServiceFacade.validateCountry(country);
    }

    // --------------
    // FORMAT
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Format> getFormats(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Format.class);
        List<Format> formats;
        if (count == 0) {
            formats = new ArrayList<>();
        } else {
            formats = wineServiceFacade.getFormats(searchParameters);
        }
        return new ListWithCount<>(wineServiceFacade.countFormats(searchParameters), formats);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("format/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Format getFormatById(@PathParam("id") int formatId) {
        return wineServiceFacade.getFormatById(formatId);
    }

    @DELETE
    @Path("format/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteFormatById(@PathParam("id") int formatId) throws BusinessException {
        wineServiceFacade.deleteFormat(wineServiceFacade.getFormatById(formatId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("format")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Format saveFormat(Format format) throws BusinessException {
        return wineServiceFacade.saveFormat(format);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateFormat")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateFormat(Format format) throws BusinessException {
        wineServiceFacade.validateFormat(format);
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateAppellation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateAppellation(Appellation appellation) throws BusinessException {
        wineServiceFacade.validateAppellation(appellation);
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateProducer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateProducer(Producer producer) throws BusinessException {
        wineServiceFacade.validateProducer(producer);
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("region")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Region saveRegion(Region region) throws BusinessException {
        return wineServiceFacade.saveRegion(region);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateRegion")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateRegion(Region region) throws BusinessException {
        wineServiceFacade.validateRegion(region);
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("wine")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Wine saveWine(Wine wine) throws BusinessException {
        return wineServiceFacade.saveWine(wine);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validateWine")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validateWine(Wine wine) throws BusinessException {
        wineServiceFacade.validateWine(wine);
    }

    // BEANS Methods

    @Inject
    public void setWineServiceFacade(WineServiceFacade wineServiceFacade) {
        this.wineServiceFacade = wineServiceFacade;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}

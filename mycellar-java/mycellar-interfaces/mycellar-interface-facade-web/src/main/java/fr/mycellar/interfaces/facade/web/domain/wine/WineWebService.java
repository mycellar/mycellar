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

import static fr.mycellar.interfaces.facade.web.domain.MetamodelUtil.*;
import static fr.peralta.mycellar.domain.shared.repository.PropertySelector.*;

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

import fr.mycellar.interfaces.facade.web.domain.FilterCouple;
import fr.mycellar.interfaces.facade.web.domain.ListWithCount;
import fr.mycellar.interfaces.facade.web.domain.MetamodelUtil;
import fr.mycellar.interfaces.facade.web.domain.OrderCouple;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.OrderBy;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
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
    @Path("wines")
    public ListWithCount<Wine> getWines(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters, @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(newPropertySelector(filter.getFilter(), MetamodelUtil.toAttributes(filter.getProperty(), Wine.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), Wine.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countWines(searchParameters), wineServiceFacade.getWines(searchParameters));
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
    @Path("producers")
    public ListWithCount<Producer> getProducers(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(newPropertySelector(filter.getFilter(), MetamodelUtil.toAttributes(filter.getProperty(), Producer.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), Producer.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countProducers(searchParameters), wineServiceFacade.getProducers(searchParameters));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("producer/{id}")
    public Producer getProducerById(@PathParam("id") int producerId) {
        return wineServiceFacade.getProducerById(producerId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("producer")
    public Producer saveProducer(Producer producer) throws BusinessException {
        return wineServiceFacade.saveProducer(producer);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("countries")
    public ListWithCount<Country> getCountries(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(newPropertySelector(filter.getFilter(), toAttributes(filter.getProperty(), Country.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), Country.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countCountries(searchParameters), wineServiceFacade.getCountries(searchParameters));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("country/{id}")
    public Country getCountryById(@PathParam("id") int countryId) {
        return wineServiceFacade.getCountryById(countryId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("country")
    public Country saveCountry(Country country) throws BusinessException {
        return wineServiceFacade.saveCountry(country);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellations")
    public ListWithCount<Appellation> getAppellation(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(newPropertySelector(filter.getFilter(), toAttributes(filter.getProperty(), Appellation.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), Appellation.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countAppellations(searchParameters), wineServiceFacade.getAppellations(searchParameters));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellation/{id}")
    public Appellation getAppellationById(@PathParam("id") int appellationId) {
        return wineServiceFacade.getAppellationById(appellationId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("appellation")
    public Appellation saveAppellation(Appellation appellation) throws BusinessException {
        return wineServiceFacade.saveAppellation(appellation);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("regions")
    public ListWithCount<Region> getRegions(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (filter.isFilterSet()) {
                searchParameters.property(newPropertySelector(filter.getFilter(), MetamodelUtil.toAttributes(filter.getProperty(), Region.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), toAttributes(order.getProperty(), Region.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countRegions(searchParameters), wineServiceFacade.getRegions(searchParameters));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("region/{id}")
    public Region getRegionById(@PathParam("id") int regionId) {
        return wineServiceFacade.getRegionById(regionId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("region")
    public void saveRegion(Region region) throws BusinessException {
        wineServiceFacade.saveRegion(region);
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

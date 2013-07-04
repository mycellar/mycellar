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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.facade.web.domain.FilterCouple;
import fr.mycellar.interfaces.facade.web.domain.ListWithCount;
import fr.mycellar.interfaces.facade.web.domain.MetamodelUtil;
import fr.mycellar.interfaces.facade.web.domain.OrderCouple;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.OrderBy;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Country;
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
    public ListWithCount<Wine> getWines(@QueryParam("first") int first,
            @QueryParam("count") int count, @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), MetamodelUtil
                    .toMetamodelPath(order.getProperty(), Wine.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countWines(searchParameters),
                wineServiceFacade.getWines(searchParameters));
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
    @Path("countries")
    public ListWithCount<Country> getCountries(@QueryParam("first") int first,
            @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (StringUtils.isNotEmpty(filter.getFilter())) {
                searchParameters.property(PropertySelector.newPropertySelector(filter.getFilter(),
                        Country.class,
                        MetamodelUtil.toMetamodelPath(filter.getProperty(), Country.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), MetamodelUtil
                    .toMetamodelPath(order.getProperty(), Country.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countCountries(searchParameters),
                wineServiceFacade.getCountries(searchParameters));
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("regions")
    public ListWithCount<Region> getRegions(@QueryParam("first") int first,
            @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.anywhere();
        for (FilterCouple filter : filters) {
            if (StringUtils.isNotEmpty(filter.getFilter())) {
                searchParameters.property(PropertySelector.newPropertySelector(filter.getFilter(),
                        Region.class,
                        MetamodelUtil.toMetamodelPath(filter.getProperty(), Region.class)));
            }
        }
        searchParameters.setFirstResult(first);
        searchParameters.setMaxResults(count);
        for (OrderCouple order : orders) {
            searchParameters.addOrderBy(new OrderBy(order.getDirection(), MetamodelUtil
                    .toMetamodelPath(order.getProperty(), Region.class)));
        }
        return new ListWithCount<>(wineServiceFacade.countRegions(searchParameters),
                wineServiceFacade.getRegions(searchParameters));
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

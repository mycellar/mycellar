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
package fr.peralta.mycellar.interfaces.client.web.components.wine.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.repository.RegionOrder;
import fr.peralta.mycellar.domain.wine.repository.RegionOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionDataProvider extends
        MultipleSortableDataProvider<Region, RegionOrderEnum, RegionOrder> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final IModel<SearchForm> searchFormModel;

    /**
     * @param searchFormModel
     */
    public RegionDataProvider(IModel<SearchForm> searchFormModel) {
        super(new RegionOrder().add(RegionOrderEnum.COUNTRY_NAME, OrderWayEnum.ASC).add(
                RegionOrderEnum.NAME, OrderWayEnum.ASC));
        Injector.get().inject(this);
        this.searchFormModel = searchFormModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach() {
        searchFormModel.detach();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Region> iterator(long first, long count) {
        return wineServiceFacade.getRegions(searchFormModel.getObject(), getState().getOrders(),
                first, count).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return wineServiceFacade.countRegions(searchFormModel.getObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Region> model(Region object) {
        return new Model<Region>(object);
    }

}

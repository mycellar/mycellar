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
package fr.peralta.mycellar.interfaces.client.web.components.stock.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.StockOrder;
import fr.peralta.mycellar.domain.stock.repository.StockOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class StockDataProvider extends
        MultipleSortableDataProvider<Stock, StockOrderEnum, StockOrder> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    private final IModel<SearchForm> searchFormModel;

    /**
     * @param searchFormModel
     */
    public StockDataProvider(IModel<SearchForm> searchFormModel) {
        super(new StockOrder().add(StockOrderEnum.COUNTRY_NAME, OrderWayEnum.ASC)
                .add(StockOrderEnum.REGION_NAME, OrderWayEnum.ASC)
                .add(StockOrderEnum.APPELLATION_NAME, OrderWayEnum.ASC));
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
    public Iterator<? extends Stock> iterator(int first, int count) {
        return stockServiceFacade.getStocks(searchFormModel.getObject(), getState().getOrders(),
                first, count).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return (int) stockServiceFacade.countStocks(searchFormModel.getObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Stock> model(Stock object) {
        return new Model<Stock>(object);
    }

}

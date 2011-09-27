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
package fr.peralta.mycellar.interfaces.client.web.components.stock.multiple;

import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.multiple.MultiplePanel;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarMultiplePanel extends MultiplePanel<Cellar> {

    private static final long serialVersionUID = 201109272049L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    private final IModel<SearchForm> searchFormModel;
    private final CountEnum count;

    /**
     * @param id
     * @param searchFormModel
     * @param count
     */
    public CellarMultiplePanel(String id, IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id);
        this.searchFormModel = searchFormModel;
        this.count = count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Cellar, Long> getData() {
        return stockServiceFacade.getCellars(searchFormModel.getObject(), count);
    }

}

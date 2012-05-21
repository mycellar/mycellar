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
package fr.peralta.mycellar.interfaces.client.web.components.stock.cloud;

import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.form.CellarForm;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarComplexTagCloud extends ComplexTagCloud<Cellar> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public CellarComplexTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count, FilterEnum... filters) {
        super(id, label, searchFormModel, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Cellar> createForm(String id, IModel<SearchForm> searchFormModel) {
        return new CellarForm(id, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Cellar createObject() {
        Cellar cellar = new Cellar();
        cellar.setOwner(UserKey.getUserLoggedIn());
        return cellar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Cellar, Long> getChoices(SearchForm searchForm, CountEnum count,
            FilterEnum[] filters) {
        return stockServiceFacade.getCellars(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return true;
    }

}

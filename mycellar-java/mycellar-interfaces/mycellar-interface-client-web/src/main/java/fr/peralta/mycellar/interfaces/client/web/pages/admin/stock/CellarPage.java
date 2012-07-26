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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.stock;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.form.CellarForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarPage extends AbstractEditPage<Cellar> {

    private static final long serialVersionUID = 201203270918L;

    private static final String CELLAR_ID_PARAMETER = "cellar";

    /**
     * @param cellar
     * @return
     */
    public static PageParameters getPageParameters(Cellar cellar) {
        return new PageParameters().add(CELLAR_ID_PARAMETER, cellar.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(CELLAR_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public CellarPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Cellar getObjectById(Integer objectId) {
        return stockServiceFacade.getCellarById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Cellar createNewObject() {
        return new Cellar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return CELLAR_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Cellar object) throws BusinessException {
        stockServiceFacade.saveCellar(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Cellar> createObjectForm(String id, IModel<SearchForm> searchFormModel,
            Cellar cellar) {
        return new CellarForm(id, searchFormModel, cellar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Cellar, ?, ?>> getListPageClass() {
        return CellarsPage.class;
    }

}

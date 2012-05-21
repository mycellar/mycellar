/*
 * Copyright 2011, MyCellarShare
 *
 * This file is part of MyCellarShare.
 *
 * MyCellarShare is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCellarShare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCellarShare. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.stock;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.ValidationError;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.form.CellarShareForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarSharePage extends AbstractEditPage<CellarShare> {

    private static final long serialVersionUID = 201203270918L;

    private static final String CELLAR_SHARE_ID_PARAMETER = "cellarShare";

    /**
     * @param country
     * @return
     */
    public static PageParameters getPageParameters(CellarShare country) {
        return new PageParameters().add(CELLAR_SHARE_ID_PARAMETER, country.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(CELLAR_SHARE_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public CellarSharePage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellarShare getObjectById(Integer objectId) {
        return stockServiceFacade.getCellarShareById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellarShare createNewObject() {
        return new CellarShare();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return CELLAR_SHARE_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(CellarShare object) {
        try {
            stockServiceFacade.saveCellarShare(object);
        } catch (BusinessException e) {
            get(e.getBusinessError().getProperty()).get(e.getBusinessError().getProperty()).error(
                    new ValidationError().addMessageKey(e.getBusinessError().getKey()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<CellarShare> getObjectForm(String id, IModel<SearchForm> searchFormModel,
            CellarShare cellarShare) {
        return new CellarShareForm(id, searchFormModel, cellarShare, CountEnum.WINE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<CellarShare, ?, ?>> getListPageClass() {
        return CellarSharesPage.class;
    }

}

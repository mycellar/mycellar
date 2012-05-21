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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.wine;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.ValidationError;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.AppellationForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class AppellationPage extends AbstractEditPage<Appellation> {

    private static final long serialVersionUID = 201203270918L;

    private static final String APPELLATION_ID_PARAMETER = "appellation";

    /**
     * @param appellation
     * @return
     */
    public static PageParameters getPageParameters(Appellation appellation) {
        return new PageParameters().add(APPELLATION_ID_PARAMETER, appellation.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(APPELLATION_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public AppellationPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Appellation getObjectById(Integer objectId) {
        return wineServiceFacade.getAppellationById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Appellation createNewObject() {
        return new Appellation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return APPELLATION_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Appellation object) {
        try {
            wineServiceFacade.saveAppellation(object);
        } catch (BusinessException e) {
            get(e.getBusinessError().getProperty()).get(e.getBusinessError().getProperty()).error(
                    new ValidationError().addMessageKey(e.getBusinessError().getKey()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Appellation> getObjectForm(String id, IModel<SearchForm> searchFormModel,
            Appellation appellation) {
        return new AppellationForm(id, searchFormModel, appellation, CountEnum.WINE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Appellation, ?, ?>> getListPageClass() {
        return AppellationsPage.class;
    }

}

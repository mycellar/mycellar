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

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.FormatForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class FormatPage extends AbstractEditPage<Format> {

    private static final long serialVersionUID = 201203270918L;

    private static final String FORMAT_ID_PARAMETER = "format";

    /**
     * @param format
     * @return
     */
    public static PageParameters getPageParameters(Format format) {
        return new PageParameters().add(FORMAT_ID_PARAMETER, format.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(FORMAT_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public FormatPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Format getObjectById(Integer objectId) {
        return wineServiceFacade.getFormatById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Format createNewObject() {
        return new Format();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return FORMAT_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Format object) throws BusinessException {
        wineServiceFacade.saveFormat(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Format> createObjectForm(String id, IModel<SearchForm> searchFormModel,
            Format format) {
        return new FormatForm(id, searchFormModel, format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Format, ?, ?>> getListPageClass() {
        return FormatsPage.class;
    }

}

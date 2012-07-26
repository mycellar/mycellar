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
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.RegionForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionPage extends AbstractEditPage<Region> {

    private static final long serialVersionUID = 201203270918L;

    private static final String REGION_ID_PARAMETER = "region";

    /**
     * @param region
     * @return
     */
    public static PageParameters getPageParameters(Region region) {
        return new PageParameters().add(REGION_ID_PARAMETER, region.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(REGION_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public RegionPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Region getObjectById(Integer objectId) {
        return wineServiceFacade.getRegionById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Region createNewObject() {
        return new Region();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return REGION_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Region object) throws BusinessException {
        wineServiceFacade.saveRegion(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Region> createObjectForm(String id, IModel<SearchForm> searchFormModel,
            Region object) {
        return new RegionForm(id, searchFormModel, object, CountEnum.WINE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Region, ?, ?>> getListPageClass() {
        return RegionsPage.class;
    }

}

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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrder;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.stock.data.CellarShareDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarSharesPage extends
        AbstractListPage<CellarShare, CellarShareOrderEnum, CellarShareOrder> {

    private static final long serialVersionUID = 201203262250L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public CellarSharesPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MultipleSortableDataProvider<CellarShare, CellarShareOrderEnum, CellarShareOrder> getDataProvider() {
        return new CellarShareDataProvider(new SearchFormModel(new SearchForm()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<IColumn<CellarShare, CellarShareOrderEnum>> getColumns() {
        List<IColumn<CellarShare, CellarShareOrderEnum>> columns = new ArrayList<IColumn<CellarShare, CellarShareOrderEnum>>();
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(new ResourceModel(
                "cellar.owner.email"), CellarShareOrderEnum.OWNER_EMAIL, "cellar.owner.email"));
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(new ResourceModel(
                "cellar.name"), CellarShareOrderEnum.CELLAR_NAME, "cellar.name"));
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(
                new ResourceModel("email"), CellarShareOrderEnum.EMAIL, "email"));
        columns.add(new PropertyColumn<CellarShare, CellarShareOrderEnum>(new ResourceModel(
                "accessRight"), CellarShareOrderEnum.ACCESS_RIGHT, "accessRight"));
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageParameters getEditPageParameters(CellarShare object) {
        if (object == null) {
            return CellarSharePage.getPageParametersForCreation();
        } else {
            return CellarSharePage.getPageParameters(object);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditPage<CellarShare>> getEditPageClass() {
        return CellarSharePage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteObject(CellarShare object) throws BusinessException {
        stockServiceFacade.deleteCellarShare(object);
    }

}

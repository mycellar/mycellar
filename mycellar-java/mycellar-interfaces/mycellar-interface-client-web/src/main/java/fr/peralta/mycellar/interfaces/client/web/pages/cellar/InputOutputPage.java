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
package fr.peralta.mycellar.interfaces.client.web.pages.cellar;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.multiple.MultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.data.MovementDataView;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class InputOutputPage extends CellarSuperPage {

    private static final long serialVersionUID = 201108170920L;
    private static Logger logger = LoggerFactory.getLogger(InputOutputPage.class);

    private static final String CELLARS_COMPONENT_ID = "cellars";

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public InputOutputPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        SearchFormModel searchFormModel = new SearchFormModel(new SearchForm().addToSet(
                FilterEnum.USER, UserKey.getUserLoggedIn()));
        setDefaultModel(searchFormModel);
        add(new MultiplePanel<Cellar>(CELLARS_COMPONENT_ID, stockServiceFacade.getCellars(
                new SearchForm().addToSet(FilterEnum.USER, UserKey.getUserLoggedIn()),
                CountEnum.STOCK_QUANTITY)));
        add(new ActionLink("clearFilters", Action.CANCEL));

        MovementDataView movementDataView = new MovementDataView("movements", searchFormModel);
        movementDataView.setItemsPerPage(25);
        add(new WebMarkupContainer("noMovements").setVisible(movementDataView.getViewSize() == 0));
        add(movementDataView);
        add(new AjaxPagingNavigator("movementsTopNav", movementDataView));
        add(new AjaxPagingNavigator("movementsBottomNav", movementDataView));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case MODEL_CHANGED:
                break;
            case CANCEL:
                break;
            }
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }
}

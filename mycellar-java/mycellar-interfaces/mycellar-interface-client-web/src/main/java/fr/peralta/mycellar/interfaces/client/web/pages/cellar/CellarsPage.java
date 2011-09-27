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
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.data.StockDataView;
import fr.peralta.mycellar.interfaces.client.web.components.stock.multiple.CellarMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.AppellationMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.CountryMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.FormatMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.RegionMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.WineColorEnumMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.WineTypeEnumMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public class CellarsPage extends CellarSuperPage {

    private static final long serialVersionUID = 201108170919L;
    private static Logger logger = LoggerFactory.getLogger(CellarsPage.class);

    private static final String CELLARS_COMPONENT_ID = "cellars";
    private static final String COUNTRIES_COMPONENT_ID = "countries";
    private static final String REGIONS_COMPONENT_ID = "regions";
    private static final String APPELLATIONS_COMPONENT_ID = "appellations";
    private static final String TYPES_COMPONENT_ID = "types";
    private static final String COLORS_COMPONENT_ID = "colors";
    private static final String FORMATS_COMPONENT_ID = "formats";

    /**
     * @param parameters
     */
    public CellarsPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        SearchFormModel searchFormModel = new SearchFormModel(new SearchForm().addToSet(
                FilterEnum.USER, UserKey.getUserLoggedIn()));
        setDefaultModel(searchFormModel);

        add(new BookmarkablePageLink<NewCellarPage>("newCellar", NewCellarPage.class));
        add(new CellarMultiplePanel(CELLARS_COMPONENT_ID, searchFormModel, CountEnum.STOCK_QUANTITY));
        add(new CountryMultiplePanel(COUNTRIES_COMPONENT_ID, searchFormModel,
                CountEnum.STOCK_QUANTITY));
        add(new RegionMultiplePanel(REGIONS_COMPONENT_ID, searchFormModel, CountEnum.STOCK_QUANTITY));
        add(new AppellationMultiplePanel(APPELLATIONS_COMPONENT_ID, searchFormModel,
                CountEnum.STOCK_QUANTITY));
        add(new WineTypeEnumMultiplePanel(TYPES_COMPONENT_ID, searchFormModel,
                CountEnum.STOCK_QUANTITY));
        add(new WineColorEnumMultiplePanel(COLORS_COMPONENT_ID, searchFormModel,
                CountEnum.STOCK_QUANTITY));
        add(new FormatMultiplePanel(FORMATS_COMPONENT_ID, searchFormModel, CountEnum.STOCK_QUANTITY));
        add(new ActionLink("clearFilters", Action.CANCEL));

        StockDataView stockDataView = new StockDataView("stocks", searchFormModel);
        stockDataView.setItemsPerPage(25);
        add(new WebMarkupContainer("noStocks").setVisible(stockDataView.getViewSize() == 0));
        add(stockDataView);
        add(new AjaxPagingNavigator("stocksTopNav", stockDataView));
        add(new AjaxPagingNavigator("stocksBottomNav", stockDataView));
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
                setDefaultModelObject(new SearchForm().addToSet(FilterEnum.USER,
                        UserKey.getUserLoggedIn()));
                break;
            }
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

}

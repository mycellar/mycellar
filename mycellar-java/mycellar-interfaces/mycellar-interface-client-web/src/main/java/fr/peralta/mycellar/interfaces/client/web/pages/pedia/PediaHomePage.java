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
package fr.peralta.mycellar.interfaces.client.web.pages.pedia;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.data.WineDataView;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.AppellationMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.CountryMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.RegionMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.WineColorEnumMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.multiple.WineTypeEnumMultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.PediaSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public class PediaHomePage extends PediaSuperPage {

    private static final long serialVersionUID = 201108102315L;
    private static Logger logger = LoggerFactory.getLogger(PediaHomePage.class);

    private static final String COUNTRIES_COMPONENT_ID = "countries";
    private static final String REGIONS_COMPONENT_ID = "regions";
    private static final String APPELLATIONS_COMPONENT_ID = "appellations";
    private static final String TYPES_COMPONENT_ID = "types";
    private static final String COLORS_COMPONENT_ID = "colors";

    /**
     * @param parameters
     */
    public PediaHomePage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        SearchFormModel searchFormModel = new SearchFormModel(new SearchForm());
        setDefaultModel(searchFormModel);
        add(new CountryMultiplePanel(COUNTRIES_COMPONENT_ID, searchFormModel, CountEnum.WINE));
        add(new RegionMultiplePanel(REGIONS_COMPONENT_ID, searchFormModel, CountEnum.WINE));
        add(new AppellationMultiplePanel(APPELLATIONS_COMPONENT_ID, searchFormModel, CountEnum.WINE));
        add(new WineTypeEnumMultiplePanel(TYPES_COMPONENT_ID, searchFormModel, CountEnum.WINE));
        add(new WineColorEnumMultiplePanel(COLORS_COMPONENT_ID, searchFormModel, CountEnum.WINE));
        add(new ActionLink("clearFilters", Action.CANCEL));

        WineDataView wineDataView = new WineDataView("wines", searchFormModel);
        wineDataView.setItemsPerPage(25);
        add(new WebMarkupContainer("noWines").setVisible(wineDataView.getViewSize() == 0));
        add(wineDataView);
        add(new AjaxPagingNavigator("winesTopNav", wineDataView));
        add(new AjaxPagingNavigator("winesBottomNav", wineDataView));
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
                setDefaultModelObject(new SearchForm());
                break;
            }
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }
}

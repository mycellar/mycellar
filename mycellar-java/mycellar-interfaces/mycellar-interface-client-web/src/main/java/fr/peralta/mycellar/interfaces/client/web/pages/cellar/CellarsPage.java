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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.repository.StockSearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationCountEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationSearchForm;
import fr.peralta.mycellar.domain.wine.repository.CountryCountEnum;
import fr.peralta.mycellar.domain.wine.repository.CountrySearchForm;
import fr.peralta.mycellar.domain.wine.repository.RegionCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionSearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.multiple.MultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.data.StockDataView;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

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

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public CellarsPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        add(new BookmarkablePageLink<NewCellarPage>("newCellar", NewCellarPage.class));
        add(new MultiplePanel<Cellar>(CELLARS_COMPONENT_ID,
                stockServiceFacade.getAllCellarsWithCountsFromUser(UserKey.getUserLoggedIn())));
        add(new MultiplePanel<Country>(COUNTRIES_COMPONENT_ID, wineServiceFacade.getCountries(
                new CountrySearchForm(UserKey.getUserLoggedIn()), CountryCountEnum.STOCK_QUANTITY)));
        add(new MultiplePanel<Region>(REGIONS_COMPONENT_ID, wineServiceFacade.getRegions(
                new RegionSearchForm(UserKey.getUserLoggedIn()), RegionCountEnum.STOCK_QUANTITY)));
        add(new MultiplePanel<Appellation>(APPELLATIONS_COMPONENT_ID,
                wineServiceFacade.getAppellations(
                        new AppellationSearchForm(UserKey.getUserLoggedIn()),
                        AppellationCountEnum.STOCK_QUANTITY)));
        add(new MultiplePanel<WineTypeEnum>(TYPES_COMPONENT_ID,
                wineServiceFacade.getTypesWithCounts()));
        add(new MultiplePanel<WineColorEnum>(COLORS_COMPONENT_ID,
                wineServiceFacade.getColorsWithCounts()));
        add(new MultiplePanel<Format>(FORMATS_COMPONENT_ID, wineServiceFacade.getFormatWithCounts()));
        setDefaultModel(new CompoundPropertyModel<StockSearchForm>(new StockSearchForm(
                UserKey.getUserLoggedIn())));

        StockDataView stockDataView = new StockDataView("stocks", new Model<StockSearchForm>(
                (StockSearchForm) getDefaultModelObject()));
        stockDataView.setItemsPerPage(25);
        add(new WebMarkupContainer("noStocks").setVisible(stockDataView.getViewSize() == 0));
        add(stockDataView);
        add(new AjaxPagingNavigator("stocksTopNav", stockDataView));
        add(new AjaxPagingNavigator("stocksBottomNav", stockDataView));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case MODEL_CHANGED:
                if (event.getSource() instanceof MultiplePanel) {
                    MultiplePanel<?> component = (MultiplePanel<?>) event.getSource();
                    if (CELLARS_COMPONENT_ID.equals(component.getId())) {
                        List<Cellar> cellars = (List<Cellar>) component.getDefaultModelObject();
                        CountrySearchForm countrySearchForm = new CountrySearchForm(
                                UserKey.getUserLoggedIn());
                        countrySearchForm.getCellars().addAll(cellars);
                        Map<Country, Long> countriesMap = wineServiceFacade.getCountries(
                                countrySearchForm, CountryCountEnum.STOCK_QUANTITY);
                        ((MultiplePanel<Country>) get(COUNTRIES_COMPONENT_ID))
                                .setChoices(countriesMap);
                        RegionSearchForm regionSearchForm = new RegionSearchForm(
                                UserKey.getUserLoggedIn());
                        regionSearchForm.getCellars().addAll(cellars);
                        Map<Region, Long> regionsMap = wineServiceFacade.getRegions(
                                regionSearchForm, RegionCountEnum.STOCK_QUANTITY);
                        ((MultiplePanel<Region>) get(REGIONS_COMPONENT_ID)).setChoices(regionsMap);
                        AppellationSearchForm appellationSearchForm = new AppellationSearchForm(
                                UserKey.getUserLoggedIn());
                        appellationSearchForm.getCellars().addAll(cellars);
                        ((MultiplePanel<Appellation>) get(APPELLATIONS_COMPONENT_ID))
                                .setChoices(wineServiceFacade.getAppellations(
                                        appellationSearchForm, AppellationCountEnum.STOCK_QUANTITY));
                    } else if (COUNTRIES_COMPONENT_ID.equals(component.getId())) {
                        List<Country> countries = (List<Country>) component.getDefaultModelObject();
                        RegionSearchForm regionSearchForm = new RegionSearchForm(
                                UserKey.getUserLoggedIn());
                        regionSearchForm.getCountries().addAll(countries);
                        Map<Region, Long> regionsMap = wineServiceFacade.getRegions(
                                regionSearchForm, RegionCountEnum.STOCK_QUANTITY);
                        ((MultiplePanel<Region>) get(REGIONS_COMPONENT_ID)).setChoices(regionsMap);
                        Set<Region> regions = regionsMap.keySet();
                        AppellationSearchForm appellationSearchForm = new AppellationSearchForm(
                                UserKey.getUserLoggedIn());
                        appellationSearchForm.getRegions().addAll(regions);
                        appellationSearchForm.getCountries().addAll(countries);
                        ((MultiplePanel<Appellation>) get(APPELLATIONS_COMPONENT_ID))
                                .setChoices(wineServiceFacade.getAppellations(
                                        appellationSearchForm, AppellationCountEnum.STOCK_QUANTITY));
                    } else if (REGIONS_COMPONENT_ID.equals(component.getId())) {
                        List<Region> regions = (List<Region>) component.getDefaultModelObject();
                        AppellationSearchForm appellationSearchForm = new AppellationSearchForm(
                                UserKey.getUserLoggedIn());
                        appellationSearchForm.getRegions().addAll(regions);
                        ((MultiplePanel<Appellation>) get(APPELLATIONS_COMPONENT_ID))
                                .setChoices(wineServiceFacade.getAppellations(
                                        appellationSearchForm, AppellationCountEnum.STOCK_QUANTITY));
                    }
                }
                break;
            }
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

}

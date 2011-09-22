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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationCountEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationSearchForm;
import fr.peralta.mycellar.domain.wine.repository.CountryCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionSearchForm;
import fr.peralta.mycellar.domain.wine.repository.WineSearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.multiple.MultiplePanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.data.WineDataView;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.PediaSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

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

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public PediaHomePage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        add(new MultiplePanel<Country>(COUNTRIES_COMPONENT_ID,
                wineServiceFacade.getCountries(CountryCountEnum.WINE)));
        add(new MultiplePanel<Region>(REGIONS_COMPONENT_ID, wineServiceFacade.getRegions(
                new RegionSearchForm(), RegionCountEnum.WINE)));
        add(new MultiplePanel<Appellation>(APPELLATIONS_COMPONENT_ID,
                wineServiceFacade.getAppellations(new AppellationSearchForm(),
                        AppellationCountEnum.WINE)));
        add(new MultiplePanel<WineTypeEnum>(TYPES_COMPONENT_ID,
                wineServiceFacade.getTypesWithCounts()));
        add(new MultiplePanel<WineColorEnum>(COLORS_COMPONENT_ID,
                wineServiceFacade.getColorsWithCounts()));
        setDefaultModel(new CompoundPropertyModel<WineSearchForm>(new WineSearchForm()));

        WineDataView wineDataView = new WineDataView("wines", new Model<WineSearchForm>(
                (WineSearchForm) getDefaultModelObject()));
        wineDataView.setItemsPerPage(25);
        add(new WebMarkupContainer("noWines").setVisible(wineDataView.getViewSize() == 0));
        add(wineDataView);
        add(new AjaxPagingNavigator("winesTopNav", wineDataView));
        add(new AjaxPagingNavigator("winesBottomNav", wineDataView));
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
                    if (COUNTRIES_COMPONENT_ID.equals(component.getId())) {
                        List<Country> countries = (List<Country>) component.getDefaultModelObject();
                        RegionSearchForm regionSearchForm = new RegionSearchForm();
                        regionSearchForm.getCountries().addAll(countries);
                        Map<Region, Long> regionsMap = wineServiceFacade.getRegions(
                                regionSearchForm, RegionCountEnum.WINE);
                        ((MultiplePanel<Region>) get(REGIONS_COMPONENT_ID)).setChoices(regionsMap);
                        Set<Region> regions = regionsMap.keySet();
                        AppellationSearchForm appellationSearchForm = new AppellationSearchForm();
                        appellationSearchForm.getRegions().addAll(regions);
                        appellationSearchForm.getCountries().addAll(countries);
                        ((MultiplePanel<Appellation>) get(APPELLATIONS_COMPONENT_ID))
                                .setChoices(wineServiceFacade.getAppellations(
                                        appellationSearchForm, AppellationCountEnum.WINE));
                    } else if (REGIONS_COMPONENT_ID.equals(component.getId())) {
                        List<Region> regions = (List<Region>) component.getDefaultModelObject();
                        AppellationSearchForm appellationSearchForm = new AppellationSearchForm();
                        appellationSearchForm.getRegions().addAll(regions);
                        ((MultiplePanel<Appellation>) get(APPELLATIONS_COMPONENT_ID))
                                .setChoices(wineServiceFacade.getAppellations(
                                        appellationSearchForm, AppellationCountEnum.WINE));
                    }
                }
                break;
            }
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }
}

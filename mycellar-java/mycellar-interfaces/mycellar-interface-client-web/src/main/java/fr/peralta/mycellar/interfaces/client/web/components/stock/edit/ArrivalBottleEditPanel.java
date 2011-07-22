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
package fr.peralta.mycellar.interfaces.client.web.components.stock.edit;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexAutocomplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.CountryComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.FormatComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.RegionComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ArrivalBottleEditPanel extends Panel {

    private static final long serialVersionUID = 201107211818L;

    private static final String COUNTRY_COMPONENT_ID = "bottle.wine.appellation.region.country";
    private static final String REGION_COMPONENT_ID = "bottle.wine.appellation.region";
    private static final String APPELLATION_COMPONENT_ID = "bottle.wine.appellation";
    private static final String PRODUCER_COMPONENT_ID = "bottle.wine.producer";
    private static final String TYPE_COMPONENT_ID = "bottle.wine.type";
    private static final String COLOR_COMPONENT_ID = "bottle.wine.color";
    private static final String FORMAT_COMPONENT_ID = "bottle.format";

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     */
    public ArrivalBottleEditPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new CountryComplexTagCloud(COUNTRY_COMPONENT_ID, new StringResourceModel("country",
                this, null), wineServiceFacade.getCountriesWithCounts(), this.getClass()));
        add(new EmptyPanel(REGION_COMPONENT_ID).setOutputMarkupId(true).setVisibilityAllowed(false));
        add(new EmptyPanel(APPELLATION_COMPONENT_ID).setOutputMarkupId(true).setVisibilityAllowed(
                false));
        add(new EmptyPanel(PRODUCER_COMPONENT_ID).setOutputMarkupId(true).setVisibilityAllowed(
                false));
        add(new EmptyPanel(TYPE_COMPONENT_ID).setOutputMarkupId(true).setVisibilityAllowed(false));
        add(new EmptyPanel(COLOR_COMPONENT_ID).setOutputMarkupId(true).setVisibilityAllowed(false));
        add(new FormatComplexTagCloud(FORMAT_COMPONENT_ID, new StringResourceModel("format", this,
                null), wineServiceFacade.getFormatWithCounts(), this.getClass()));
        add(new TextField<Integer>("quantity").setRequired(true));
    }

    /**
     * @return
     */
    private void replaceRegionPanel() {
        Country country = (Country) get(COUNTRY_COMPONENT_ID).getDefaultModelObject();
        replace(new RegionComplexTagCloud(REGION_COMPONENT_ID, new StringResourceModel("region",
                this, null), wineServiceFacade.getRegionsWithCounts(country), this.getClass(),
                country));
    }

    /**
     * @return
     */
    private void replaceAppellationPanel() {
        Region region = (Region) get(REGION_COMPONENT_ID).getDefaultModelObject();
        replace(new AppellationComplexTagCloud(APPELLATION_COMPONENT_ID, new StringResourceModel(
                "appellation", this, null), wineServiceFacade.getAppellationsWithCounts(region),
                this.getClass(), region));
    }

    /**
     * @return
     */
    private void replaceProducerPanel() {
        replace(new ProducerComplexAutocomplete(PRODUCER_COMPONENT_ID, new StringResourceModel(
                "producer", this, null), this.getClass()));
    }

    /**
     * @return
     */
    private void replaceTypePanel() {
        Producer producer = (Producer) get(PRODUCER_COMPONENT_ID).getDefaultModelObject();
        replace(new WineTypeEnumTagCloud(TYPE_COMPONENT_ID, new StringResourceModel("type", this,
                null), wineServiceFacade.getTypeWithCounts(producer), this.getClass()));
    }

    /**
     * @return
     */
    private void replaceColorPanel() {
        Producer producer = (Producer) get(PRODUCER_COMPONENT_ID).getDefaultModelObject();
        WineTypeEnum type = (WineTypeEnum) get(TYPE_COMPONENT_ID).getDefaultModelObject();
        replace(new WineColorEnumTagCloud(COLOR_COMPONENT_ID, new StringResourceModel("color",
                this, null), wineServiceFacade.getColorWithCounts(producer, type), this.getClass()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof CountryComplexTagCloud) {
                    replaceRegionPanel();
                } else if (event.getSource() instanceof RegionComplexTagCloud) {
                    replaceAppellationPanel();
                } else if (event.getSource() instanceof AppellationComplexTagCloud) {
                    replaceProducerPanel();
                } else if (event.getSource() instanceof ProducerComplexAutocomplete) {
                    replaceTypePanel();
                } else if (event.getSource() instanceof WineTypeEnumTagCloud) {
                    replaceColorPanel();
                }
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
        }
    }

}

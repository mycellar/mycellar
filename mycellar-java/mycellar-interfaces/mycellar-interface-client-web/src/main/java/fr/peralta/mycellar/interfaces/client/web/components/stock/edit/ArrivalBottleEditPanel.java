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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexAutocomplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.FormatComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumFromProducerAndTypeTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumFromProducerTagCloud;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public class ArrivalBottleEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;

    private static final String APPELLATION_COMPONENT_ID = "bottle.wine.appellation";
    private static final String PRODUCER_COMPONENT_ID = "bottle.wine.producer";
    private static final String TYPE_COMPONENT_ID = "bottle.wine.type";
    private static final String COLOR_COMPONENT_ID = "bottle.wine.color";
    private static final String VINTAGE_COMPONENT_ID = "bottle.wine.vintage";
    private static final String FORMAT_COMPONENT_ID = "bottle.format";

    private final Logger logger = LoggerFactory.getLogger(ArrivalBottleEditPanel.class);

    /**
     * @param id
     */
    public ArrivalBottleEditPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new AppellationComplexTagCloud(APPELLATION_COMPONENT_ID, new StringResourceModel(
                "appellation", this, null)));
        add(new ProducerComplexAutocomplete(PRODUCER_COMPONENT_ID, new StringResourceModel(
                "producer", this, null)));
        add(new EmptyPanel(TYPE_COMPONENT_ID).setOutputMarkupId(true));
        add(new EmptyPanel(COLOR_COMPONENT_ID).setOutputMarkupId(true));
        add(new FormatComplexTagCloud(FORMAT_COMPONENT_ID, new StringResourceModel("format", this,
                null)));
        add(new TextField<Integer>(VINTAGE_COMPONENT_ID));
        add(new TextField<Integer>("quantity").setRequired(true));
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
                if (event.getSource() instanceof ProducerComplexAutocomplete) {
                    ProducerComplexAutocomplete source = (ProducerComplexAutocomplete) event
                            .getSource();
                    Producer sourceObject = source.getModelObject();
                    if ((sourceObject != null) && source.isValid(sourceObject)) {
                        replace(new WineTypeEnumFromProducerTagCloud(TYPE_COMPONENT_ID,
                                new StringResourceModel("type", this, null),
                                (IModel<Producer>) source.getDefaultModel()));
                    } else {
                        get(TYPE_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                                new EmptyPanel(TYPE_COMPONENT_ID));
                        get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                                new EmptyPanel(COLOR_COMPONENT_ID));
                    }
                } else if (event.getSource() instanceof WineTypeEnumFromProducerTagCloud) {
                    WineTypeEnumFromProducerTagCloud source = (WineTypeEnumFromProducerTagCloud) event
                            .getSource();
                    WineTypeEnum sourceObject = source.getModelObject();
                    if ((sourceObject != null) && source.isValid(sourceObject)) {
                        replace(new WineColorEnumFromProducerAndTypeTagCloud(COLOR_COMPONENT_ID,
                                new StringResourceModel("color", this, null),
                                (IModel<Producer>) get(PRODUCER_COMPONENT_ID).getDefaultModel(),
                                (IModel<WineTypeEnum>) source.getDefaultModel()));
                    } else {
                        get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                                new EmptyPanel(COLOR_COMPONENT_ID));
                    }
                }
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            if (action.isAjax()) {
                action.getAjaxRequestTarget().add(this);
            }
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

}

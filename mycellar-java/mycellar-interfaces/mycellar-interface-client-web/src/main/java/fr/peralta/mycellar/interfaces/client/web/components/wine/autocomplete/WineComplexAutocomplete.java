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
package fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteAjaxComponent;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.ComplexAutocomplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumFromProducerAndTypeTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumFromProducerTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.WineEditPanel;

/**
 * @author speralta
 */
public class WineComplexAutocomplete extends ComplexAutocomplete<Wine> {

    private static final long serialVersionUID = 201109081829L;

    private static final String APPELLATION_COMPONENT_ID = "appellation";
    private static final String PRODUCER_COMPONENT_ID = "producer";
    private static final String TYPE_COMPONENT_ID = "type";
    private static final String COLOR_COMPONENT_ID = "color";
    private static final String VINTAGE_COMPONENT_ID = "vintage";

    /**
     * @param id
     * @param label
     */
    public WineComplexAutocomplete(String id, IModel<String> label) {
        super(id, label);
        setOutputMarkupId(true);
        add(new AppellationComplexTagCloud(APPELLATION_COMPONENT_ID, new StringResourceModel(
                "appellation", this, null)));
        add(new ProducerComplexAutocomplete(PRODUCER_COMPONENT_ID, new StringResourceModel(
                "producer", this, null)));
        add(new EmptyPanel(TYPE_COMPONENT_ID).setOutputMarkupId(true));
        add(new EmptyPanel(COLOR_COMPONENT_ID).setOutputMarkupId(true));
        add(new TextField<Integer>(VINTAGE_COMPONENT_ID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalConfigureComponent(Wine modelObject, boolean isValidModelObject) {
        super.internalConfigureComponent(modelObject, isValidModelObject);
        if (modelObject == null) {
            setModelObject(createObject());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new WineEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Wine createObject() {
        Wine wine = new Wine();
        IModel<Producer> producerModel = (IModel<Producer>) get(PRODUCER_COMPONENT_ID)
                .getDefaultModel();
        if (producerModel != null) {
            wine.setProducer(producerModel.getObject());
        }
        IModel<Appellation> appellationModel = (IModel<Appellation>) get(APPELLATION_COMPONENT_ID)
                .getDefaultModel();
        if (appellationModel != null) {
            wine.setAppellation(appellationModel.getObject());
        }
        IModel<WineTypeEnum> typeModel = (IModel<WineTypeEnum>) get(TYPE_COMPONENT_ID)
                .getDefaultModel();
        if (typeModel != null) {
            wine.setType(typeModel.getObject());
        }
        IModel<WineColorEnum> colorModel = (IModel<WineColorEnum>) get(COLOR_COMPONENT_ID)
                .getDefaultModel();
        if (colorModel != null) {
            wine.setColor(colorModel.getObject());
        }
        IModel<Integer> vintageModel = (IModel<Integer>) get(VINTAGE_COMPONENT_ID)
                .getDefaultModel();
        if (vintageModel != null) {
            wine.setVintage(vintageModel.getObject());
        }
        return wine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AutocompleteAjaxComponent<Wine> createAutocomplete(String id) {
        return new WineAutocompleteAjaxComponent(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return ((ProducerComplexAutocomplete) get(PRODUCER_COMPONENT_ID)).isValued()
                && ((WineTypeEnumFromProducerTagCloud) get(TYPE_COMPONENT_ID)).isValued()
                && ((WineColorEnumFromProducerAndTypeTagCloud) get(COLOR_COMPONENT_ID)).isValued()
                && ((AppellationComplexTagCloud) get(APPELLATION_COMPONENT_ID)).isValued();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getValueLabelFor(Wine object) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotEmpty(object.getName())) {
            builder.append(object.getName());
        }
        if (object.getVintage() != null) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(object.getVintage());
        }
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        if (source instanceof ProducerComplexAutocomplete) {
            ProducerComplexAutocomplete producerComplexAutocomplete = (ProducerComplexAutocomplete) source;
            setDefaultModelObject(createObject());
            Producer sourceObject = producerComplexAutocomplete.getModelObject();
            if ((sourceObject != null) && producerComplexAutocomplete.isValued()) {
                replace(new WineTypeEnumFromProducerTagCloud(TYPE_COMPONENT_ID,
                        new StringResourceModel("type", this, null),
                        (IModel<Producer>) producerComplexAutocomplete.getDefaultModel()));
            } else {
                get(TYPE_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(TYPE_COMPONENT_ID));
                get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(COLOR_COMPONENT_ID));
            }
        } else if (source instanceof WineTypeEnumFromProducerTagCloud) {
            WineTypeEnumFromProducerTagCloud wineTypeEnumFromProducerTagCloud = (WineTypeEnumFromProducerTagCloud) source;
            setDefaultModelObject(createObject());
            WineTypeEnum sourceObject = wineTypeEnumFromProducerTagCloud.getModelObject();
            if ((sourceObject != null) && wineTypeEnumFromProducerTagCloud.isValued()) {
                replace(new WineColorEnumFromProducerAndTypeTagCloud(COLOR_COMPONENT_ID,
                        new StringResourceModel("color", this, null), (IModel<Producer>) get(
                                PRODUCER_COMPONENT_ID).getDefaultModel(),
                        (IModel<WineTypeEnum>) wineTypeEnumFromProducerTagCloud.getDefaultModel()));
            } else {
                get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(COLOR_COMPONENT_ID));
            }
        } else if (source instanceof WineColorEnumFromProducerAndTypeTagCloud) {
            setDefaultModelObject(createObject());
        } else if (source instanceof AppellationComplexTagCloud) {
            setDefaultModelObject(createObject());
        } else {
            super.onModelChanged(source, action);
        }
    }
}

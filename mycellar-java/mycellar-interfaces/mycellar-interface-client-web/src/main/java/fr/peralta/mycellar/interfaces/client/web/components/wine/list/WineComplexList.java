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
package fr.peralta.mycellar.interfaces.client.web.components.wine.list;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.OnBlurDefaultAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.shared.list.ComplexList;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexAutocomplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumFromProducerAndTypeTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumFromProducerTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.WineEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class WineComplexList extends ComplexList<Wine> {

    private static final long serialVersionUID = 201109101937L;

    private static final String APPELLATION_COMPONENT_ID = "appellation";
    private static final String PRODUCER_COMPONENT_ID = "producer";
    private static final String TYPE_COMPONENT_ID = "type";
    private static final String COLOR_COMPONENT_ID = "color";
    private static final String VINTAGE_COMPONENT_ID = "vintage";

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     */
    public WineComplexList(String id, IModel<String> label) {
        super(id, label);
        setOutputMarkupId(true);
        add(new AppellationComplexTagCloud(APPELLATION_COMPONENT_ID, new StringResourceModel(
                "appellation", this, null), CountEnum.STOCK_QUANTITY));
        add(new ProducerComplexAutocomplete(PRODUCER_COMPONENT_ID, new StringResourceModel(
                "producer", this, null)));
        add(new EmptyPanel(TYPE_COMPONENT_ID).setOutputMarkupId(true));
        add(new EmptyPanel(COLOR_COMPONENT_ID).setOutputMarkupId(true));
        add(new NumberTextField<Integer>(VINTAGE_COMPONENT_ID).setMinimum(1800)
                .setMaximum(new LocalDate().getYear()).add(new OnBlurDefaultAjaxBehavior()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalConfigureComponent(Wine modelObject) {
        super.internalConfigureComponent(modelObject);
        if (modelObject == null) {
            setModelObject(createObject());
        }
        get(APPELLATION_COMPONENT_ID).setVisibilityAllowed(!isValued());
        get(PRODUCER_COMPONENT_ID).setVisibilityAllowed(!isValued());
        get(TYPE_COMPONENT_ID).setVisibilityAllowed(!isValued());
        get(COLOR_COMPONENT_ID).setVisibilityAllowed(!isValued());
        get(VINTAGE_COMPONENT_ID).setVisibilityAllowed(!isValued());
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
    protected boolean isReadyToSelect() {
        return ((ProducerComplexAutocomplete) get(PRODUCER_COMPONENT_ID)).isValued()
                || ((AppellationComplexTagCloud) get(APPELLATION_COMPONENT_ID)).isValued();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Wine> getList() {
        return wineServiceFacade.getWinesLike(getModelObject());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        if (source instanceof ProducerComplexAutocomplete) {
            ProducerComplexAutocomplete producerComplexAutocomplete = (ProducerComplexAutocomplete) source;
            Producer sourceObject = producerComplexAutocomplete.getModelObject();
            if ((sourceObject != null) && producerComplexAutocomplete.isValued()) {
                replace(new WineTypeEnumFromProducerTagCloud(TYPE_COMPONENT_ID,
                        new StringResourceModel("type", this, null),
                        (IModel<Producer>) producerComplexAutocomplete.getDefaultModel(),
                        CountEnum.WINE));
            } else {
                get(TYPE_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(TYPE_COMPONENT_ID));
                get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(COLOR_COMPONENT_ID));
            }
            refreshList();
        } else if (source instanceof WineTypeEnumFromProducerTagCloud) {
            WineTypeEnumFromProducerTagCloud wineTypeEnumFromProducerTagCloud = (WineTypeEnumFromProducerTagCloud) source;
            WineTypeEnum sourceObject = wineTypeEnumFromProducerTagCloud.getModelObject();
            if ((sourceObject != null) && wineTypeEnumFromProducerTagCloud.isValued()) {
                replace(new WineColorEnumFromProducerAndTypeTagCloud(COLOR_COMPONENT_ID,
                        new StringResourceModel("color", this, null), (IModel<Producer>) get(
                                PRODUCER_COMPONENT_ID).getDefaultModel(),
                        (IModel<WineTypeEnum>) wineTypeEnumFromProducerTagCloud.getDefaultModel(),
                        CountEnum.WINE));
            } else {
                get(COLOR_COMPONENT_ID).setDefaultModelObject(null).replaceWith(
                        new EmptyPanel(COLOR_COMPONENT_ID));
            }
            refreshList();
        } else if (source instanceof WineColorEnumFromProducerAndTypeTagCloud) {
            refreshList();
        } else if (source instanceof AppellationComplexTagCloud) {
            refreshList();
        } else if (source instanceof NumberTextField) {
            refreshList();
        } else {
            super.onModelChanged(source, action);
        }
    }

}

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

import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.behaviors.OnEventModelChangedAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.list.ComplexList;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexTypeahead;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.WineForm;
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

    private final AppellationComplexTagCloud appellationComplexTagCloud;
    private final ProducerComplexTypeahead producerComplexTypeahead;
    private final WineTypeEnumSimpleTagCloud wineTypeEnumSimpleTagCloud;
    private final WineColorEnumSimpleTagCloud wineColorEnumSimpleTagCloud;
    private final NumberTextField<Integer> vintageTextField;
    private final FormComponentFeedbackBorder vintageBorder;

    private final CountEnum count;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     */
    public WineComplexList(String id, IModel<String> label, IModel<SearchForm> searchFormModel,
            CountEnum count) {
        super(id, label, searchFormModel);
        this.count = count;
        add(appellationComplexTagCloud = new AppellationComplexTagCloud(APPELLATION_COMPONENT_ID,
                new StringResourceModel("appellation", this, null), searchFormModel, count,
                FilterEnum.COUNTRY, FilterEnum.REGION, FilterEnum.APPELLATION));
        add(producerComplexTypeahead = new ProducerComplexTypeahead(PRODUCER_COMPONENT_ID,
                new StringResourceModel("producer", this, null), searchFormModel));
        add(wineTypeEnumSimpleTagCloud = new WineTypeEnumSimpleTagCloud(TYPE_COMPONENT_ID,
                new StringResourceModel("type", this, null), searchFormModel, count));
        add(wineColorEnumSimpleTagCloud = new WineColorEnumSimpleTagCloud(COLOR_COMPONENT_ID,
                new StringResourceModel("color", this, null), searchFormModel, count));
        add((vintageBorder = new FormComponentFeedbackBorder(VINTAGE_COMPONENT_ID))
                .add((vintageTextField = new NumberTextField<Integer>(VINTAGE_COMPONENT_ID))
                        .setMinimum(1800).setMaximum(new LocalDate().getYear())
                        .add(new OnEventModelChangedAjaxBehavior("onblur"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setOtherComponentsVisibilityAllowed(boolean allowed) {
        appellationComplexTagCloud.setVisibilityAllowed(allowed);
        producerComplexTypeahead.setVisibilityAllowed(allowed);
        wineTypeEnumSimpleTagCloud.setVisibilityAllowed(allowed);
        wineColorEnumSimpleTagCloud.setVisibilityAllowed(allowed);
        vintageBorder.setVisibilityAllowed(allowed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getFilteredIdsForFeedback() {
        return new String[] { PRODUCER_COMPONENT_ID, TYPE_COMPONENT_ID, COLOR_COMPONENT_ID };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Wine> createForm(String id, IModel<SearchForm> searchFormModel) {
        return new WineForm(id, searchFormModel, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Wine createObject() {
        Wine wine = new Wine();
        wine.setProducer(producerComplexTypeahead.getModelObject());
        wine.setAppellation(appellationComplexTagCloud.getModelObject());
        wine.setType(wineTypeEnumSimpleTagCloud.getModelObject());
        wine.setColor(wineColorEnumSimpleTagCloud.getModelObject());
        wine.setVintage(vintageTextField.getModelObject());
        return wine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return producerComplexTypeahead.isValued() && appellationComplexTagCloud.isValued();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Wine> getChoices() {
        return wineServiceFacade.getWines(getSearchFormModel().getObject(),
                new WineOrder().add(WineOrderEnum.VINTAGE, OrderWayEnum.DESC), 0, 10);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged(IEvent<?> event) {
        IEventSource source = event.getSource();
        if (source == producerComplexTypeahead) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.PRODUCER,
                    producerComplexTypeahead.getModelObject());
        } else if (source == wineTypeEnumSimpleTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.TYPE,
                    wineTypeEnumSimpleTagCloud.getModelObject());
        } else if (source == wineColorEnumSimpleTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.COLOR,
                    wineColorEnumSimpleTagCloud.getModelObject());
        } else if (source == appellationComplexTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.APPELLATION,
                    appellationComplexTagCloud.getModelObject());
        } else if (source == vintageTextField) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.VINTAGE,
                    vintageTextField.getModelObject());
        }
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToReplace() {
        return FilterEnum.WINE;
    }
}

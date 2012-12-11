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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.behaviors.OnEventModelChangedAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.list.ComplexList;
import fr.peralta.mycellar.interfaces.client.web.components.shared.select.SelectRenderer;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.AppellationComplexTypeahead;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexTypeahead;
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

    private final AppellationComplexTypeahead appellationComplexTypeahead;
    private final ProducerComplexTypeahead producerComplexTypeahead;
    private final Select<WineTypeEnum> wineTypeEnumSelect;
    private final FormComponentFeedbackBorder wineTypeEnumBorder;
    private final Select<WineColorEnum> wineColorEnumSelect;
    private final FormComponentFeedbackBorder wineColorEnumBorder;
    private final NumberTextField<Integer> vintageTextField;
    private final FormComponentFeedbackBorder vintageBorder;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public WineComplexList(String id, IModel<String> label, IModel<SearchForm> searchFormModel) {
        super(id, label, searchFormModel);
        add(appellationComplexTypeahead = new AppellationComplexTypeahead(APPELLATION_COMPONENT_ID,
                new StringResourceModel("appellation", this, null), searchFormModel,
                FilterEnum.COUNTRY, FilterEnum.REGION, FilterEnum.APPELLATION));
        add(producerComplexTypeahead = new ProducerComplexTypeahead(PRODUCER_COMPONENT_ID,
                new StringResourceModel("producer", this, null), searchFormModel));
        add((wineTypeEnumBorder = new FormComponentFeedbackBorder(TYPE_COMPONENT_ID))
                .add((wineTypeEnumSelect = new Select<WineTypeEnum>(TYPE_COMPONENT_ID))
                        .add(new SelectOptions<WineTypeEnum>("options", Arrays.asList(WineTypeEnum
                                .values()), new SelectRenderer<WineTypeEnum>()))));
        add((wineColorEnumBorder = new FormComponentFeedbackBorder(COLOR_COMPONENT_ID))
                .add((wineColorEnumSelect = new Select<WineColorEnum>(COLOR_COMPONENT_ID))
                        .add(new SelectOptions<WineColorEnum>("options", Arrays
                                .asList(WineColorEnum.values()),
                                new SelectRenderer<WineColorEnum>()))));
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
        appellationComplexTypeahead.setVisibilityAllowed(allowed);
        producerComplexTypeahead.setVisibilityAllowed(allowed);
        wineTypeEnumBorder.setVisibilityAllowed(allowed);
        wineColorEnumBorder.setVisibilityAllowed(allowed);
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
        return new WineForm(id, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Wine createObject() {
        Wine wine = new Wine();
        wine.setProducer(producerComplexTypeahead.getModelObject());
        wine.setAppellation(appellationComplexTypeahead.getModelObject());
        wine.setType(wineTypeEnumSelect.getModelObject());
        wine.setColor(wineColorEnumSelect.getModelObject());
        wine.setVintage(vintageTextField.getModelObject());
        return wine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return producerComplexTypeahead.isValued() && appellationComplexTypeahead.isValued();
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
        } else if (source == wineTypeEnumSelect) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.TYPE,
                    wineTypeEnumSelect.getModelObject());
        } else if (source == wineColorEnumSelect) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.COLOR,
                    wineColorEnumSelect.getModelObject());
        } else if (source == appellationComplexTypeahead) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.APPELLATION,
                    appellationComplexTypeahead.getModelObject());
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

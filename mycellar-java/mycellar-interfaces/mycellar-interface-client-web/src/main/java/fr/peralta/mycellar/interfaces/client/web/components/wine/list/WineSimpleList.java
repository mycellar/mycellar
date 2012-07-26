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
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.interfaces.client.web.behaviors.OnEventModelChangedAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.list.SimpleList;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerSimpleAutoComplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumSimpleTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class WineSimpleList extends SimpleList<Wine> {

    private static final long serialVersionUID = 201109101937L;

    private static final String APPELLATION_COMPONENT_ID = "appellation";
    private static final String PRODUCER_COMPONENT_ID = "producer";
    private static final String TYPE_COMPONENT_ID = "type";
    private static final String COLOR_COMPONENT_ID = "color";
    private static final String VINTAGE_COMPONENT_ID = "vintage";

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final AppellationSimpleTagCloud appellationSimpleTagCloud;
    private final ProducerSimpleAutoComplete producerSimpleAutoComplete;
    private final WineTypeEnumSimpleTagCloud wineTypeEnumSimpleTagCloud;
    private final WineColorEnumSimpleTagCloud wineColorEnumSimpleTagCloud;
    private final NumberTextField<Integer> vintageTextField;
    private final FormComponentFeedbackBorder vintageBorder;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public WineSimpleList(String id, IModel<String> label, IModel<SearchForm> searchFormModel) {
        super(id, label, searchFormModel);
        setOutputMarkupId(true);
        add(appellationSimpleTagCloud = new AppellationSimpleTagCloud(APPELLATION_COMPONENT_ID,
                new StringResourceModel("appellation", this, null), searchFormModel,
                CountEnum.STOCK_QUANTITY));
        add(producerSimpleAutoComplete = new ProducerSimpleAutoComplete(PRODUCER_COMPONENT_ID,
                new StringResourceModel("producer", this, null), searchFormModel));
        add(wineTypeEnumSimpleTagCloud = new WineTypeEnumSimpleTagCloud(TYPE_COMPONENT_ID,
                new StringResourceModel("type", this, null), searchFormModel, CountEnum.WINE));
        add(wineColorEnumSimpleTagCloud = new WineColorEnumSimpleTagCloud(COLOR_COMPONENT_ID,
                new StringResourceModel("color", this, null), searchFormModel, CountEnum.WINE));
        add((vintageBorder = new FormComponentFeedbackBorder(VINTAGE_COMPONENT_ID))
                .add((vintageTextField = new NumberTextField<Integer>(VINTAGE_COMPONENT_ID))
                        .setMinimum(1800).setMaximum(new LocalDate().getYear())
                        .add(new OnEventModelChangedAjaxBehavior("onblur"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalOnConfigure() {
        super.internalOnConfigure();
        boolean isValued = isValued();
        appellationSimpleTagCloud.setVisibilityAllowed(!isValued);
        producerSimpleAutoComplete.setVisibilityAllowed(!isValued);
        wineTypeEnumSimpleTagCloud.setVisibilityAllowed(!isValued);
        wineColorEnumSimpleTagCloud.setVisibilityAllowed(!isValued);
        vintageBorder.setVisibilityAllowed(!isValued);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return producerSimpleAutoComplete.isValued() || appellationSimpleTagCloud.isValued();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Wine> getChoices(SearchForm searchForm) {
        return wineServiceFacade.getWines(searchForm, new WineOrder(), 0, 10);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged(IEvent<?> event) {
        IEventSource source = event.getSource();
        if (source == producerSimpleAutoComplete) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.PRODUCER,
                    producerSimpleAutoComplete.getModelObject());
        } else if (source == wineTypeEnumSimpleTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.TYPE,
                    wineTypeEnumSimpleTagCloud.getModelObject());
        } else if (source == wineColorEnumSimpleTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.COLOR,
                    wineColorEnumSimpleTagCloud.getModelObject());
        } else if (source == appellationSimpleTagCloud) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.APPELLATION,
                    appellationSimpleTagCloud.getModelObject());
        } else if (source == vintageTextField) {
            getSearchFormModel().getObject().replaceSet(FilterEnum.VINTAGE,
                    vintageTextField.getModelObject());
        }
        initializeIfUnique();
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Wine createDefaultObject() {
        Wine wine = new Wine();
        wine.setProducer(producerSimpleAutoComplete.getModelObject());
        wine.setAppellation(appellationSimpleTagCloud.getModelObject());
        wine.setType(wineTypeEnumSimpleTagCloud.getModelObject());
        wine.setColor(wineColorEnumSimpleTagCloud.getModelObject());
        wine.setVintage(vintageTextField.getModelObject());
        return wine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToReplace() {
        return FilterEnum.WINE;
    }

}

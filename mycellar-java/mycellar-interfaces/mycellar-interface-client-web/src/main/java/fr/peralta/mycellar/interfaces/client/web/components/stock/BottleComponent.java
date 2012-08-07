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
package fr.peralta.mycellar.interfaces.client.web.components.stock;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.CompoundPropertyPanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.FormatComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.list.WineComplexList;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class BottleComponent extends CompoundPropertyPanel<Bottle> {

    private static final long serialVersionUID = 201109081922L;
    private static final Logger logger = LoggerFactory.getLogger(BottleComponent.class);

    private static final String WINE_COMPONENT_ID = "wine";
    private static final String FORMAT_COMPONENT_ID = "format";

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    private final WineComplexList wineComplexList;
    private final FormatComplexTagCloud formatComplexTagCloud;

    private final IModel<SearchForm> searchFormModel;

    /**
     * @param id
     * @param searchFormModel
     * @param count
     */
    public BottleComponent(String id, IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id);
        this.searchFormModel = searchFormModel;
        setOutputMarkupId(true);
        add(wineComplexList = new WineComplexList(WINE_COMPONENT_ID, new StringResourceModel(
                "wine", this, null), searchFormModel, count));
        add(formatComplexTagCloud = new FormatComplexTagCloud(FORMAT_COMPONENT_ID,
                new StringResourceModel("format", this, null), searchFormModel, count,
                FilterEnum.FORMAT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachModels() {
        searchFormModel.detach();
        super.detachModels();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case MODEL_CHANGED:
                if ((event.getSource() == wineComplexList)
                        || (event.getSource() == formatComplexTagCloud)) {
                    Wine wine = wineComplexList.getModelObject();
                    Format format = formatComplexTagCloud.getModelObject();
                    if (event.getSource() == wineComplexList) {
                        searchFormModel.getObject().replaceSet(FilterEnum.WINE, wine);
                    } else if (event.getSource() == formatComplexTagCloud) {
                        searchFormModel.getObject().replaceSet(FilterEnum.FORMAT, format);
                    }
                    if ((wine != null) && (format != null)) {
                        Bottle bottle = stockServiceFacade.findBottle(wine, format);
                        if (bottle == null) {
                            bottle = new Bottle();
                            bottle.setWine(wine);
                            bottle.setFormat(format);
                        }
                        setModelObject(bottle);
                    }
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Bottle createDefaultObject() {
        Bottle bottle = new Bottle();
        bottle.setFormat(formatComplexTagCloud.getModelObject());
        bottle.setWine(wineComplexList.getModelObject());
        return bottle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void convertInput() {
        setConvertedInput(getModelObject());
    }

}

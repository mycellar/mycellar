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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.CompoundPropertyPanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.FormatFromWineTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.list.WineSimpleList;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class BottleSelectComponent extends CompoundPropertyPanel<Bottle> {

    private static final long serialVersionUID = 201109081922L;
    private static final Logger logger = LoggerFactory.getLogger(BottleSelectComponent.class);

    private static final String WINE_COMPONENT_ID = "wine";
    private static final String FORMAT_COMPONENT_ID = "format";

    private final IModel<SearchForm> searchFormModel;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param id
     * @param searchFormModel
     */
    public BottleSelectComponent(String id, IModel<SearchForm> searchFormModel) {
        super(id);
        setOutputMarkupId(true);
        this.searchFormModel = searchFormModel;
        add(new WineSimpleList(WINE_COMPONENT_ID, new StringResourceModel("wine", this, null),
                searchFormModel));
        add(new FormatFromWineTagCloud(FORMAT_COMPONENT_ID, new StringResourceModel("format", this,
                null), CountEnum.STOCK_QUANTITY));
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
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case MODEL_CHANGED:
                Wine wine = (Wine) get(WINE_COMPONENT_ID).getDefaultModelObject();
                if (event.getSource() instanceof WineSimpleList) {
                    ((FormatFromWineTagCloud) get(FORMAT_COMPONENT_ID)).setWine(wine);
                } else if (event.getSource() instanceof FormatFromWineTagCloud) {
                    Format format = (Format) get(FORMAT_COMPONENT_ID).getDefaultModelObject();
                    if ((wine != null) && (format != null)) {
                        Bottle bottle = stockServiceFacade.findBottle(wine, format);
                        if (bottle != null) {
                            setDefaultModelObject(bottle);
                        }
                    }
                }
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Bottle createDefaultObject() {
        return new Bottle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void convertInput() {
        setConvertedInput(getModelObject());
    }

}

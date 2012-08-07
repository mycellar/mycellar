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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.validator.MinimumValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.stock.BottleSelectComponent;
import fr.peralta.mycellar.interfaces.client.web.components.stock.cloud.CellarSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class DrinkBottleEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;
    private static final Logger logger = LoggerFactory.getLogger(DrinkBottleEditPanel.class);

    private final CellarSimpleTagCloud cellarSimpleTagCloud;

    private final IModel<SearchForm> searchFormModel;

    /**
     * @param id
     * @param searchFormModel
     */
    public DrinkBottleEditPanel(String id, IModel<SearchForm> searchFormModel) {
        super(id);
        this.searchFormModel = searchFormModel;
        add(new BottleSelectComponent("bottle", searchFormModel, CountEnum.STOCK_QUANTITY));
        add(new FormComponentFeedbackBorder("quantity").add(new TextField<Integer>("quantity")
                .setRequired(true).add(new MinimumValidator<Integer>(1))));
        add(cellarSimpleTagCloud = new CellarSimpleTagCloud("cellar", new StringResourceModel(
                "cellar", this, null), searchFormModel, CountEnum.STOCK_QUANTITY));
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
                if (event.getSource() == cellarSimpleTagCloud) {
                    searchFormModel.getObject().replaceSet(FilterEnum.CELLAR,
                            cellarSimpleTagCloud.getModelObject());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            default:
                break;
            }
        } else if (event.getPayload() instanceof AjaxRequestTarget) {
            // FIXME reRender needed when searchForm is changed by some other
            // component
            AjaxTool.ajaxReRender(cellarSimpleTagCloud);
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}

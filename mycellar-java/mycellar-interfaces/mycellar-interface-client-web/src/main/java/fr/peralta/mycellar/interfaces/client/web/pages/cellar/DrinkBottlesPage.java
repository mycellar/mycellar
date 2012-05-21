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
package fr.peralta.mycellar.interfaces.client.web.pages.cellar;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Drink;
import fr.peralta.mycellar.domain.stock.DrinkBottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FeedbackPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FilteredContainerVisibleFeedbackMessageFilter;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.LocalDateTextField;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.DrinkBottlesEditPanel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.form.DrinkBottleForm;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class DrinkBottlesPage extends CellarSuperPage {

    private static class DrinkForm extends Form<Drink> {

        private static final long serialVersionUID = 201108091934L;

        @SpringBean
        private StockServiceFacade stockServiceFacade;

        /**
         * @param id
         */
        public DrinkForm(String id) {
            super(id, new CompoundPropertyModel<Drink>(new Drink()));
            add(new FeedbackPanel("feedback", new FilteredContainerVisibleFeedbackMessageFilter(
                    this, DRINK_BOTTLE_COMPONENT_ID)));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            stockServiceFacade.drink(getModelObject());
            setResponsePage(InputOutputPage.class);
        }

    }

    private static final long serialVersionUID = 201108091851L;
    private static final Logger logger = LoggerFactory.getLogger(DrinkBottlesPage.class);

    private static final String FORM_COMPONENT_ID = "form";
    private static final String DRINK_BOTTLES_COMPONENT_ID = "drinkBottles";
    private static final String DRINK_BOTTLE_COMPONENT_ID = "drinkBottle";

    private DrinkBottleForm drinkBottleForm;
    private final DrinkForm drinkForm;
    private final DrinkBottlesEditPanel drinkBottlesEditPanel;

    /**
     * @param parameters
     */
    public DrinkBottlesPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        drinkForm = new DrinkForm(FORM_COMPONENT_ID);
        drinkForm.add(new FormComponentFeedbackBorder("date").add(new LocalDateTextField("date")
                .setRequired(true)));
        drinkForm.add(new FormComponentFeedbackBorder("drinkWith").add(new TextField<String>(
                "drinkWith")));
        drinkForm
                .add(drinkBottlesEditPanel = new DrinkBottlesEditPanel(DRINK_BOTTLES_COMPONENT_ID));
        drinkForm.add(createHiddenBottleForm());
        add(drinkForm);
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
            case ADD:
                if (drinkBottlesEditPanel.isAddBottle(event.getSource())) {
                    drinkBottleForm.displayForm();
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case SAVE:
                if (drinkBottleForm == event.getSource()) {
                    drinkBottlesEditPanel.getModelObject().add(drinkBottleForm.getModelObject());
                    drinkForm.replace(createHiddenBottleForm());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case CANCEL:
                if (drinkBottleForm.isCancelButton(event.getSource())) {
                    drinkForm.replace(createHiddenBottleForm());
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
     * @return
     */
    private Component createHiddenBottleForm() {
        return (drinkBottleForm = new DrinkBottleForm(DRINK_BOTTLE_COMPONENT_ID,
                new Model<SearchForm>(new SearchForm().addToSet(FilterEnum.USER,
                        UserKey.getUserLoggedIn())), new DrinkBottle())).hideForm();
    }

}

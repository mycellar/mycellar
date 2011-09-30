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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import org.odlabs.wiquery.ui.datepicker.DatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Drink;
import fr.peralta.mycellar.domain.stock.DrinkBottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.cloud.CellarComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.DrinkBottleEditPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class DrinkBottlesPage extends CellarSuperPage {

    private static class DrinkBottlesEditPanel extends WebMarkupContainer {

        private static final long serialVersionUID = 201107252130L;

        private static final String NO_BOTTLES_COMPONENT_ID = "noBottles";

        /**
         * @param id
         */
        public DrinkBottlesEditPanel(String id) {
            super(id);
            add(new DrinkBottlesView("drinkBottles"));
            add(new ActionLink("addBottle", Action.ADD));
            add(new WebMarkupContainer(NO_BOTTLES_COMPONENT_ID) {
                private static final long serialVersionUID = 201108082329L;

                /**
                 * {@inheritDoc}
                 */
                @SuppressWarnings("unchecked")
                @Override
                public boolean isVisible() {
                    return ((List<DrinkBottle>) DrinkBottlesEditPanel.this.getDefaultModelObject())
                            .size() == 0;
                }
            });
        }

    }

    private static class DrinkBottlesView extends PropertyListView<DrinkBottle> {

        private static final long serialVersionUID = 201108082321L;

        /**
         * @param id
         */
        public DrinkBottlesView(String id) {
            super(id);
            setReuseItems(true);
        }

        /**
         * @param id
         * @param model
         */
        public DrinkBottlesView(String id, IModel<? extends List<? extends DrinkBottle>> model) {
            super(id, model);
            setReuseItems(true);
        }

        /**
         * @param id
         * @param list
         */
        public DrinkBottlesView(String id, List<? extends DrinkBottle> list) {
            super(id, list);
            setReuseItems(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(ListItem<DrinkBottle> item) {
            item.add(new Label("bottle.wine.appellation.region.country.name"));
            item.add(new Label("bottle.wine.appellation.region.name"));
            item.add(new Label("bottle.wine.appellation.name"));
            item.add(new Label("bottle.wine.producer.name"));
            item.add(new Label("bottle.wine.name"));
            item.add(new Label("bottle.wine.vintage"));
            item.add(new Label("bottle.format.name"));
            item.add(new Label("quantity"));
            item.add(new WebMarkupContainer("remove").add(removeLink("removeBottle", item)));
        }

    }

    private static class DrinkForm extends Form<Drink> {

        private static final long serialVersionUID = 201108091934L;

        @SpringBean
        private StockServiceFacade stockServiceFacade;

        /**
         * @param id
         */
        public DrinkForm(String id) {
            super(id, new CompoundPropertyModel<Drink>(new Drink()));
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
    private static final String CELLAR_COMPONENT_ID = "cellar";

    private final IModel<SearchForm> searchFormModel;

    /**
     * @param parameters
     */
    public DrinkBottlesPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        searchFormModel = new Model<SearchForm>(new SearchForm().addToSet(FilterEnum.USER,
                UserKey.getUserLoggedIn()));
        DrinkForm form = new DrinkForm(FORM_COMPONENT_ID);
        form.add(new DatePicker<LocalDate>("date"));
        form.add(new TextField<String>("drinkWith"));
        form.add(new DrinkBottlesEditPanel(DRINK_BOTTLES_COMPONENT_ID));
        form.add(new CellarComplexTagCloud(CELLAR_COMPONENT_ID, new StringResourceModel("cellar",
                this, null)));
        form.add(createHiddenBottleForm());
        add(form);
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
            case ADD:
                displayBottleForm();
                break;
            case SAVE:
                ((List<DrinkBottle>) get(
                        FORM_COMPONENT_ID + PATH_SEPARATOR + DRINK_BOTTLES_COMPONENT_ID)
                        .getDefaultModelObject()).add((DrinkBottle) get(
                        FORM_COMPONENT_ID + PATH_SEPARATOR + DRINK_BOTTLE_COMPONENT_ID)
                        .getDefaultModelObject());
                get(FORM_COMPONENT_ID + PATH_SEPARATOR + DRINK_BOTTLE_COMPONENT_ID).replaceWith(
                        createHiddenBottleForm());
                break;
            case MODEL_CHANGED:
                if (event.getSource() instanceof CellarComplexTagCloud) {
                    searchFormModel.getObject().replaceSet(
                            FilterEnum.CELLAR,
                            get(FORM_COMPONENT_ID + PATH_SEPARATOR + CELLAR_COMPONENT_ID)
                                    .getDefaultModelObject());
                }
                break;
            case CANCEL:
                replace(createHiddenBottleForm());
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
     * @return
     */
    private Component createHiddenBottleForm() {
        return new ObjectForm<DrinkBottle>(DRINK_BOTTLE_COMPONENT_ID, new DrinkBottle()).replace(
                new DrinkBottleEditPanel(ObjectForm.EDIT_PANEL_COMPONENT_ID, searchFormModel))
                .setVisibilityAllowed(false);
    }

    /**
     * @return
     */
    private Component displayBottleForm() {
        return get(FORM_COMPONENT_ID + PATH_SEPARATOR + DRINK_BOTTLE_COMPONENT_ID)
                .setVisibilityAllowed(true);
    }

}

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
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import org.odlabs.wiquery.ui.datepicker.DatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.stock.Arrival;
import fr.peralta.mycellar.domain.stock.ArrivalBottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.cloud.CellarComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.ArrivalBottleEditPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class PackageArrivalPage extends CellarSuperPage {

    private static class ArrivalBottlesEditPanel extends WebMarkupContainer {

        private static final long serialVersionUID = 201107252130L;

        private static final String NO_BOTTLES_COMPONENT_ID = "noBottles";

        /**
         * @param id
         */
        public ArrivalBottlesEditPanel(String id) {
            super(id);
            add(new ArrivalBottlesView("arrivalBottles"));
            add(new ActionLink("addBottle", Action.ADD));
            add(new WebMarkupContainer(NO_BOTTLES_COMPONENT_ID) {
                private static final long serialVersionUID = 201108082329L;

                /**
                 * {@inheritDoc}
                 */
                @SuppressWarnings("unchecked")
                @Override
                public boolean isVisible() {
                    return ((List<ArrivalBottle>) ArrivalBottlesEditPanel.this
                            .getDefaultModelObject()).size() == 0;
                }
            });
        }

    }

    private static class ArrivalBottlesView extends PropertyListView<ArrivalBottle> {

        private static final long serialVersionUID = 201108082321L;

        /**
         * @param id
         */
        public ArrivalBottlesView(String id) {
            super(id);
            setReuseItems(true);
        }

        /**
         * @param id
         * @param model
         */
        public ArrivalBottlesView(String id, IModel<? extends List<? extends ArrivalBottle>> model) {
            super(id, model);
            setReuseItems(true);
        }

        /**
         * @param id
         * @param list
         */
        public ArrivalBottlesView(String id, List<? extends ArrivalBottle> list) {
            super(id, list);
            setReuseItems(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(ListItem<ArrivalBottle> item) {
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

    private static class ArrivalForm extends Form<Arrival> {

        private static final long serialVersionUID = 201108091934L;

        @SpringBean
        private StockServiceFacade stockServiceFacade;

        /**
         * @param id
         */
        public ArrivalForm(String id) {
            super(id, new CompoundPropertyModel<Arrival>(new Arrival()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            stockServiceFacade.arrival(getModelObject());
            setResponsePage(InputOutputPage.class);
        }

    }

    private static final long serialVersionUID = 201108091851L;
    private static final Logger logger = LoggerFactory.getLogger(PackageArrivalPage.class);

    private static final String FORM_COMPONENT_ID = "form";
    private static final String ARRIVAL_BOTTLES_COMPONENT_ID = "arrivalBottles";
    private static final String ARRIVAL_BOTTLE_COMPONENT_ID = "arrivalBottle";

    /**
     * @param parameters
     */
    public PackageArrivalPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        ArrivalForm form = new ArrivalForm(FORM_COMPONENT_ID);
        form.add(new DatePicker<LocalDate>("date"));
        form.add(new TextField<String>("source"));
        form.add(new TextField<Float>("otherCharges"));
        form.add(new ArrivalBottlesEditPanel(ARRIVAL_BOTTLES_COMPONENT_ID));
        form.add(new CellarComplexTagCloud("cellar", new StringResourceModel("cellar", this, null)));
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
                ((List<ArrivalBottle>) get(
                        FORM_COMPONENT_ID + PATH_SEPARATOR + ARRIVAL_BOTTLES_COMPONENT_ID)
                        .getDefaultModelObject()).add((ArrivalBottle) get(
                        FORM_COMPONENT_ID + PATH_SEPARATOR + ARRIVAL_BOTTLE_COMPONENT_ID)
                        .getDefaultModelObject());
                get(FORM_COMPONENT_ID + PATH_SEPARATOR + ARRIVAL_BOTTLE_COMPONENT_ID).replaceWith(
                        createHiddenBottleForm());
                break;
            case MODEL_CHANGED:

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
        return new ObjectForm<ArrivalBottle>(ARRIVAL_BOTTLE_COMPONENT_ID, new ArrivalBottle())
                .replace(new ArrivalBottleEditPanel(ObjectForm.EDIT_PANEL_COMPONENT_ID))
                .setVisibilityAllowed(false);
    }

    /**
     * @return
     */
    private Component displayBottleForm() {
        return get(FORM_COMPONENT_ID + PATH_SEPARATOR + ARRIVAL_BOTTLE_COMPONENT_ID)
                .setVisibilityAllowed(true);
    }

}

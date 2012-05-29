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
package fr.peralta.mycellar.interfaces.client.web.components.booking.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.interfaces.client.web.behaviors.OnEventModelChangedAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.booking.autocomplete.BookingEventSimpleAutoComplete;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ToStringRenderer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.map.AbstractMapView;
import fr.peralta.mycellar.interfaces.client.web.components.user.autocomplete.UserSimpleAutoComplete;
import fr.peralta.mycellar.interfaces.client.web.renderers.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class BookingEditPanel extends Panel {

    private static class QuantitiesView extends AbstractMapView<BookingBottle, Integer> {

        private static final long serialVersionUID = 201108082321L;
        private static final Logger logger = LoggerFactory.getLogger(QuantitiesView.class);

        @SpringBean
        private RendererServiceFacade rendererServiceFacade;

        private final boolean readonly;

        /**
         * @param id
         * @param readonly
         */
        public QuantitiesView(String id, boolean readonly) {
            super(id);
            this.readonly = readonly;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(final Item<Entry<BookingBottle, Integer>> item) {
            item.add(new ExternalLink("key.bottle", item.getModelObject().getKey().getUrl(),
                    rendererServiceFacade.render(item.getModelObject().getKey().getBottle())));
            item.add(new Label("key.price", rendererServiceFacade.render(item.getModelObject()
                    .getKey().getPrice())));
            boolean maxSet = item.getModelObject().getKey().getMax() > 0;
            item.add(new NumberTextField<Integer>("valueInput", newValueModel(item))
                    .setType(Integer.class).setVisibilityAllowed(!readonly && !maxSet)
                    .add(new OnEventModelChangedAjaxBehavior("onkeyup")));
            Select<Integer> select = new Select<Integer>("valueSelect", newValueModel(item));
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i <= item.getModelObject().getKey().getMax(); i++) {
                numbers.add(i);
            }
            select.add(new SelectOptions<Integer>("options", numbers,
                    new ToStringRenderer<Integer>()));
            select.add(new OnEventModelChangedAjaxBehavior("onclick"));
            item.add(select.setType(Integer.class).setVisibilityAllowed(!readonly && maxSet));
            item.add(new Label("value", newValueModel(item)).setVisibilityAllowed(readonly));
            item.add(new Label("linePrice", new AbstractReadOnlyModel<String>() {
                private static final long serialVersionUID = 201205291631L;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public String getObject() {
                    return rendererServiceFacade.render(item.getModelObject().getKey().getPrice()
                            * item.getModelObject().getValue());
                }

            }).setOutputMarkupId(true));
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
                    if (event.getSource() instanceof Component) {
                        Component source = (Component) event.getSource();
                        if ("valueInput".equals(source.getId())
                                || "valueSelect".equals(source.getId())) {
                            AjaxTool.ajaxReRender(source.getParent().get("linePrice"));
                        }
                    }
                    break;
                default:
                    break;
                }
            }
            LoggingHelper.logEventProcessed(logger, event);
        }

    }

    private static final long serialVersionUID = 201107252130L;
    private static final Logger logger = LoggerFactory.getLogger(BookingEditPanel.class);

    private BookingEventSimpleAutoComplete bookingEventSimpleAutoComplete;
    private UserSimpleAutoComplete userSimpleAutoComplete;
    private QuantitiesView quantitiesView;

    /**
     * @param id
     */
    public BookingEditPanel(String id) {
        this(id, false);
    }

    /**
     * @param id
     * @param readonly
     */
    public BookingEditPanel(String id, boolean readonly) {
        super(id);
        add(userSimpleAutoComplete = new UserSimpleAutoComplete("customer",
                new StringResourceModel("customer", null), null));
        add(bookingEventSimpleAutoComplete = new BookingEventSimpleAutoComplete("bookingEvent",
                new StringResourceModel("bookingEvent", null)));
        add(quantitiesView = new QuantitiesView("quantities", readonly));
        add(new WebMarkupContainer("noBottles") {
            private static final long serialVersionUID = 201108082329L;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isVisible() {
                return (bookingEventSimpleAutoComplete.getModelObject() == null)
                        || (bookingEventSimpleAutoComplete.getModelObject().getBottles().size() == 0);
            }
        });
    }

    public BookingEditPanel setCustomerView() {
        userSimpleAutoComplete.setVisibilityAllowed(false);
        bookingEventSimpleAutoComplete.setVisibilityAllowed(false);
        return this;
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
                if (event.getSource() == bookingEventSimpleAutoComplete) {
                    @SuppressWarnings("unchecked")
                    Map<BookingBottle, Integer> quantities = ((Map<BookingBottle, Integer>) quantitiesView
                            .getDefaultModelObject());
                    quantities.clear();
                    if (bookingEventSimpleAutoComplete.getModelObject() != null) {
                        for (BookingBottle bottle : bookingEventSimpleAutoComplete.getModelObject()
                                .getBottles()) {
                            quantities.put(bottle, 0);
                        }
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

}

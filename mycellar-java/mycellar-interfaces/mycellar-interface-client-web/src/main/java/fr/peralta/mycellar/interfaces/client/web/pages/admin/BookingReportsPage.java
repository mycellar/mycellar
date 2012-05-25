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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.interfaces.client.web.components.booking.data.BookingEventDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.ImageAjaxPagingNavigator;
import fr.peralta.mycellar.interfaces.client.web.components.shared.map.EntryIteratorModel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.renderers.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingReportsPage extends AdminSuperPage {

    /**
     * @author speralta
     */
    private final class PeriodLabel extends AbstractReadOnlyModel<String> {
        private static final long serialVersionUID = 201205221511L;

        /**
         * {@inheritDoc}
         */
        @Override
        public String getObject() {
            BookingEvent bookingEvent = (BookingEvent) getDefaultModelObject();
            if (bookingEvent != null) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormat.longDate().withLocale(
                        Locale.getDefault());
                return new StringResourceModel("period", BookingReportsPage.this, null,
                        new Object[] { dateTimeFormatter.print(bookingEvent.getStart()),
                                dateTimeFormatter.print(bookingEvent.getEnd()) }).getObject();
            } else {
                return "";
            }
        }
    }

    private class BookingEventsView extends DataView<BookingEvent> {

        private static final long serialVersionUID = 201205250739L;

        /**
         * @param id
         * @param dataProvider
         * @param itemsPerPage
         */
        public BookingEventsView(String id, IDataProvider<BookingEvent> dataProvider,
                int itemsPerPage) {
            super(id, dataProvider, itemsPerPage);
        }

        /**
         * @param id
         * @param dataProvider
         */
        public BookingEventsView(String id, IDataProvider<BookingEvent> dataProvider) {
            super(id, dataProvider);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(Item<BookingEvent> item) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.longDate().withLocale(
                    Locale.getDefault());
            WebMarkupContainer li = new WebMarkupContainer("li");
            li.add(new ActionLink("link", Action.SELECT).add(new Label("name", item
                    .getModelObject().getName())));
            item.add(new Label("date", new StringResourceModel("period", null, new Object[] {
                    dateTimeFormatter.print(item.getModelObject().getStart()),
                    dateTimeFormatter.print(item.getModelObject().getEnd()) })));
            if ((BookingReportsPage.this.getDefaultModelObject() != null)
                    && BookingReportsPage.this.getDefaultModelObject()
                            .equals(item.getModelObject())) {
                li.add(new AttributeModifier("class", "active"));
            }
            item.add(li);
        }

    }

    private class BookingBottlesView extends RefreshingView<Entry<BookingBottle, Long>> {

        private static final long serialVersionUID = 201205251449L;

        @SpringBean
        private RendererServiceFacade rendererServiceFacade;

        @SpringBean
        private BookingServiceFacade bookingServiceFacade;

        /**
         * @param id
         */
        public BookingBottlesView(String id) {
            super(id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Iterator<IModel<Entry<BookingBottle, Long>>> getItemModels() {
            return new EntryIteratorModel<BookingBottle, Long>(bookingServiceFacade
                    .getBookingsQuantities(
                            (BookingEvent) BookingReportsPage.this.getDefaultModelObject())
                    .entrySet().iterator());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(Item<Entry<BookingBottle, Long>> item) {
            item.add(new Label("bottle", rendererServiceFacade.render(item.getModelObject()
                    .getKey().getBottle())));
            item.add(new Label("unitPrice", rendererServiceFacade.render(item.getModelObject()
                    .getKey().getPrice())));
            item.add(new Label("quantity", Long.toString(item.getModelObject().getValue())));
            item.add(new Label("linePrice", rendererServiceFacade.render(item.getModelObject()
                    .getKey().getPrice()
                    * item.getModelObject().getValue())));
        }
    }

    private static final long serialVersionUID = 201205250713L;
    private static final Logger logger = LoggerFactory.getLogger(BookingReportsPage.class);

    private final BookingEventsView bookingEventsView;

    /**
     * @param parameters
     */
    public BookingReportsPage(PageParameters parameters) {
        super(parameters);
        BookingEventDataProvider dataProvider = new BookingEventDataProvider();
        boolean events = dataProvider.size() != 0;
        if (events) {
            setDefaultModel(new CompoundPropertyModel<BookingEvent>(dataProvider.iterator(0, 1)
                    .next()));
        }
        add(bookingEventsView = new BookingEventsView("bookingEvents", dataProvider, 6));
        add(new ImageAjaxPagingNavigator("pagingNavigator", bookingEventsView, 3));
        add(new Label("name").setVisibilityAllowed(events));
        add(new Label("period", new PeriodLabel()).setVisibilityAllowed(events));
        add(new BookingBottlesView("quantities").setVisibilityAllowed(events));
        add(new Label("noBookingEvent", new StringResourceModel("noBookingEvent", null))
                .setVisibilityAllowed(!events));
        add(new WebMarkupContainer("noBottles") {
            private static final long serialVersionUID = 201108082329L;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isVisible() {
                return (BookingReportsPage.this.getDefaultModelObject() != null)
                        && (((BookingEvent) BookingReportsPage.this.getDefaultModelObject())
                                .getBottles().size() == 0);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof ActionLink) {
                    setDefaultModel(new CompoundPropertyModel<BookingEvent>(
                            ((ListItem<BookingEvent>) ((ActionLink) event.getSource()).getParent()
                                    .getParent()).getModelObject()));
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case MODEL_CHANGED:
                AjaxTool.ajaxReRender(this);
                event.stop();
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}

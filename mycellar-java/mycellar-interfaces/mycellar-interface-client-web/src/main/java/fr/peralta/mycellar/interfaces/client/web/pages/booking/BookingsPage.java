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
package fr.peralta.mycellar.interfaces.client.web.pages.booking;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.interfaces.client.web.components.booking.TotalPanel;
import fr.peralta.mycellar.interfaces.client.web.components.booking.edit.BookingEditPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ParentComponentModel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.BookingSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingsPage extends BookingSuperPage {

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
            Booking booking = ((Booking) getDefaultModelObject());
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.longDate().withLocale(
                    Locale.getDefault());
            return new StringResourceModel("period", BookingsPage.this, null, new Object[] {
                    dateTimeFormatter.print(booking.getBookingEvent().getStart()),
                    dateTimeFormatter.print(booking.getBookingEvent().getEnd()) }).getObject();
        }
    }

    /**
     * @author speralta
     */
    private final class BookingsView extends ListView<Booking> {
        private static final long serialVersionUID = 201205231418L;

        /**
         * @param id
         * @param list
         */
        private BookingsView(String id, List<? extends Booking> list) {
            super(id, list);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(ListItem<Booking> item) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.longDate().withLocale(
                    Locale.getDefault());
            WebMarkupContainer li = new WebMarkupContainer("li");
            li.add(new ActionLink("link", Action.SELECT).add(new Label("name", item
                    .getModelObject().getBookingEvent().getName())));
            item.add(new Label("date", new StringResourceModel("period", null, new Object[] {
                    dateTimeFormatter.print(item.getModelObject().getBookingEvent().getStart()),
                    dateTimeFormatter.print(item.getModelObject().getBookingEvent().getEnd()) })));
            if ((BookingsPage.this.getDefaultModelObject() != null)
                    && item.getModelObject().equals(BookingsPage.this.getDefaultModelObject())) {
                li.add(new AttributeModifier("class", "active"));
            }
            item.add(li);
        }
    }

    private static final long serialVersionUID = 201205211732L;
    private static final Logger logger = LoggerFactory.getLogger(BookingsPage.class);

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * @param parameters
     */
    public BookingsPage(PageParameters parameters) {
        super(parameters);
        Injector.get().inject(this);
        List<Booking> bookings = bookingServiceFacade.getBookings(UserKey.getUserLoggedIn());
        if (bookings.size() > 0) {
            setDefaultModel(new CompoundPropertyModel<Booking>(bookings.get(0)));
        }
        add(new BookingsView("bookings", bookings));
        add(new Label("bookingEvent.name").setVisibilityAllowed(bookings.size() != 0));
        add(new Label("period", new PeriodLabel()).setVisibilityAllowed(bookings.size() != 0));
        add(new BookingEditPanel("booking", true).setCustomerView().setVisibilityAllowed(
                bookings.size() != 0));
        add(new TotalPanel("totalPanel", new ParentComponentModel<Booking>())
                .setVisibilityAllowed(bookings.size() != 0));
        add(new Label("noBooking", new StringResourceModel("noBooking", null))
                .setVisibilityAllowed(bookings.size() == 0));
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
                    setDefaultModel(new CompoundPropertyModel<Booking>(
                            ((ListItem<Booking>) ((ActionLink) event.getSource()).getParent()
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

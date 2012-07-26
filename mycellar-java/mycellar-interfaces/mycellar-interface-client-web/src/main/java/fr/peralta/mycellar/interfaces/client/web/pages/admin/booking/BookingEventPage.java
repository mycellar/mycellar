/*
 * Copyright 2011, MyBookingEvent
 *
 * This file is part of MyBookingEvent.
 *
 * MyBookingEvent is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBookingEvent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBookingEvent. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.booking;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.booking.form.BookingEventForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingEventPage extends AbstractEditPage<BookingEvent> {

    private static final long serialVersionUID = 201203270918L;

    private static final String BOOKING_EVENT_ID_PARAMETER = "bookingEvent";

    /**
     * @param bookingEvent
     * @return
     */
    public static PageParameters getPageParameters(BookingEvent bookingEvent) {
        return new PageParameters().add(BOOKING_EVENT_ID_PARAMETER, bookingEvent.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(BOOKING_EVENT_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * @param parameters
     */
    public BookingEventPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookingEvent getObjectById(Integer objectId) {
        return bookingServiceFacade.getBookingEventById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookingEvent createNewObject() {
        return new BookingEvent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return BOOKING_EVENT_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(BookingEvent object) throws BusinessException {
        bookingServiceFacade.saveBookingEvent(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<BookingEvent> createObjectForm(String id,
            IModel<SearchForm> searchFormModel, BookingEvent object) {
        return new BookingEventForm(id, searchFormModel, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<BookingEvent, ?, ?>> getListPageClass() {
        return BookingEventsPage.class;
    }

}

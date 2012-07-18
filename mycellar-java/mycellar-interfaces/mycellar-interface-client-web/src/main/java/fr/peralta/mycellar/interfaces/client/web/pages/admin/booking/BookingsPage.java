/*
 * Copyright 2011, MyBooking
 *
 * This file is part of MyBooking.
 *
 * MyBooking is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBooking is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBooking. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.booking;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.repository.BookingOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingOrderEnum;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.interfaces.client.web.components.booking.data.BookingDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingsPage extends AbstractListPage<Booking, BookingOrderEnum, BookingOrder> {

    private static final long serialVersionUID = 201203262250L;

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * @param parameters
     */
    public BookingsPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MultipleSortableDataProvider<Booking, BookingOrderEnum, BookingOrder> getDataProvider() {
        return new BookingDataProvider();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<IColumn<Booking>> getColumns() {
        List<IColumn<Booking>> columns = new ArrayList<IColumn<Booking>>();
        columns.add(new PropertyColumn<Booking>(new ResourceModel("customer.email"),
                BookingOrderEnum.CUSTOMER_EMAIL.name(), "customer.email"));
        columns.add(new PropertyColumn<Booking>(new ResourceModel("bookingEvent.name"),
                BookingOrderEnum.EVENT_NAME.name(), "bookingEvent.name"));
        columns.add(new PropertyColumn<Booking>(new ResourceModel("bookingEvent.start"),
                BookingOrderEnum.EVENT_START.name(), "bookingEvent.start"));
        columns.add(new PropertyColumn<Booking>(new ResourceModel("bookingEvent.end"),
                BookingOrderEnum.EVENT_END.name(), "bookingEvent.end"));
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageParameters getEditPageParameters(Booking bookingEvent) {
        if (bookingEvent == null) {
            return BookingPage.getPageParametersForCreation();
        } else {
            return BookingPage.getPageParameters(bookingEvent);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditPage<Booking>> getEditPageClass() {
        return BookingPage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteObject(Booking object) throws BusinessException {
        bookingServiceFacade.deleteBooking(object);
    }

}

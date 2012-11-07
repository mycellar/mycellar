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
package fr.peralta.mycellar.interfaces.client.web.components.booking.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrderEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingEventDataProvider extends
        MultipleSortableDataProvider<BookingEvent, BookingEventOrderEnum, BookingEventOrder> {

    private static final long serialVersionUID = 201111081450L;

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * Default constructor.
     */
    public BookingEventDataProvider() {
        super(new BookingEventOrder().add(BookingEventOrderEnum.END, OrderWayEnum.DESC).add(
                BookingEventOrderEnum.NAME, OrderWayEnum.ASC));
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends BookingEvent> iterator(long first, long count) {
        return bookingServiceFacade.getBookingEvents(getState().getOrders(), first, count)
                .iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return bookingServiceFacade.countBookingEvents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<BookingEvent> model(BookingEvent object) {
        return new Model<BookingEvent>(object);
    }
}

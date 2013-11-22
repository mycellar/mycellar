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
package fr.mycellar.application.booking.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.booking.BookingService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.booking.Booking_;
import fr.mycellar.domain.booking.repository.BookingRepository;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.user.User_;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingServiceImpl extends AbstractSimpleService<Booking, BookingRepository> implements BookingService {

    private BookingRepository bookingRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent) {
        return bookingRepository.getQuantities(bookingEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking saveOrDelete(Booking booking) throws BusinessException {
        int sum = 0;
        for (Entry<BookingBottle, Integer> bottle : booking.getQuantities().entrySet()) {
            if (bottle.getValue() != null) {
                sum += bottle.getValue();
            } else {
                bottle.setValue(0);
            }
        }
        if (sum > 0) {
            return save(booking);
        } else if ((booking.getId() != null) && (booking.getId() > 0)) {
            delete(booking);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Booking> getBookings(User customer) {
        return bookingRepository.find(new SearchParametersBuilder() //
                .propertyWithValue(customer.getId(), Booking_.customer, User_.id) //
                .toSearchParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking getBooking(BookingEvent bookingEvent, User customer) {
        Booking booking = bookingRepository.findUniqueOrNone(new SearchParametersBuilder() //
                .propertyWithValue(bookingEvent.getId(), Booking_.bookingEvent, BookingEvent_.id) //
                .propertyWithValue(customer.getId(), Booking_.customer, User_.id) //
                .toSearchParameters());
        if (booking == null) {
            booking = new Booking();
            booking.setCustomer(customer);
            booking.setBookingEvent(bookingEvent);
        }
        return booking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Booking entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookingRepository getRepository() {
        return bookingRepository;
    }

    /**
     * @param bookingRepository
     *            the bookingRepository to set
     */
    @Inject
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

}

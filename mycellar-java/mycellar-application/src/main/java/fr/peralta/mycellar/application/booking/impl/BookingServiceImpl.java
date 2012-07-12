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
package fr.peralta.mycellar.application.booking.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.booking.BookingService;
import fr.peralta.mycellar.application.shared.AbstractEntityService;
import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.repository.BookingOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingOrderEnum;
import fr.peralta.mycellar.domain.booking.repository.BookingRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Service
public class BookingServiceImpl extends
        AbstractEntityService<Booking, BookingOrderEnum, BookingOrder, BookingRepository> implements
        BookingService {

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
    public void saveOrDelete(Booking booking) throws BusinessException {
        int sum = 0;
        for (Entry<BookingBottle, Integer> bottle : booking.getQuantities().entrySet()) {
            if (bottle.getValue() != null) {
                sum += bottle.getValue();
            } else {
                bottle.setValue(0);
            }
        }
        if (sum > 0) {
            save(booking);
        } else if ((booking.getId() != null) && (booking.getId() > 0)) {
            delete(booking);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Booking> getBookings(User customer) {
        return bookingRepository.getBookings(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking getBooking(BookingEvent bookingEvent, User customer) {
        Booking booking = bookingRepository.getBookingByEventAndCustomer(bookingEvent, customer);
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
    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

}

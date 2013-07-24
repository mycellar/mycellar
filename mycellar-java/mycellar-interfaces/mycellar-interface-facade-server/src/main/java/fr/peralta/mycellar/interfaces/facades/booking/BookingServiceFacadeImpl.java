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
package fr.peralta.mycellar.interfaces.facades.booking;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.booking.BookingEventService;
import fr.peralta.mycellar.application.booking.BookingService;
import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Service
public class BookingServiceFacadeImpl implements BookingServiceFacade {

    private BookingEventService bookingEventService;

    private BookingService bookingService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<BookingBottle, Long> getBookingsQuantities(BookingEvent bookingEvent) {
        return bookingService.getQuantities(bookingEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Booking getBooking(BookingEvent bookingEvent, User customer) {
        Booking booking = bookingService.getBooking(bookingEvent, customer);
        updateBooking(booking);
        return booking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventService.getCurrentBookingEvents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookings(User customer) {
        List<Booking> bookings = bookingService.getBookings(customer);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookingEvent> getBookingEventsLike(String term) {
        return bookingEventService.getAllLike(term);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countBookingEvents(SearchParameters searchParameters) {
        return bookingEventService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countBookings(SearchParameters searchParameters) {
        return bookingService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Booking getBookingById(Integer bookingId) {
        Booking booking = bookingService.getById(bookingId);
        updateBooking(booking);
        return booking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BookingEvent getBookingEventById(Integer bookingEventId) {
        return bookingEventService.getById(bookingEventId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookingEvent> getBookingEvents(SearchParameters searchParameters) {
        return bookingEventService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookings(SearchParameters searchParameters) {
        List<Booking> bookings = bookingService.find(searchParameters);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Booking saveBooking(Booking booking) throws BusinessException {
        return bookingService.saveOrDelete(booking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookingEvent saveBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        return bookingEventService.save(bookingEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteBooking(Booking booking) throws BusinessException {
        bookingService.delete(booking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        bookingEventService.delete(bookingEvent);
    }

    private void updateBooking(Booking booking) {
        // LAZY-INIT
        booking.getQuantities().hashCode();
        // UPDATE BOTTLES
        if (booking.getBookingEvent() != null) {
            for (BookingBottle bookingBottle : booking.getBookingEvent().getBottles()) {
                if (!booking.getQuantities().containsKey(bookingBottle)) {
                    booking.getQuantities().put(bookingBottle, 0);
                }
            }
        }
    }

    /**
     * @param bookingEventService
     *            the bookingEventService to set
     */
    @Autowired
    public void setBookingEventService(BookingEventService bookingEventService) {
        this.bookingEventService = bookingEventService;
    }

    /**
     * @param bookingService
     *            the bookingService to set
     */
    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

}

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
package fr.mycellar.interfaces.facades.booking;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.booking.BookingEventService;
import fr.mycellar.application.booking.BookingService;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@Named("bookingServiceFacade")
@Singleton
public class BookingServiceFacadeImpl implements BookingServiceFacade {

    private BookingEventService bookingEventService;

    private BookingService bookingService;

    @Override
    @Transactional(readOnly = true)
    public Map<BookingBottle, Long> getBookingsQuantities(BookingEvent bookingEvent) {
        return bookingService.getQuantities(bookingEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getBooking(BookingEvent bookingEvent, User customer) {
        Booking booking = bookingService.getBooking(bookingEvent, customer);
        updateBooking(booking);
        return booking;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventService.getCurrentBookingEvents();
    }

    @Override
    @Transactional(readOnly = true)
    public long countBookings(User customer) {
        return bookingService.countBookings(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookings(User customer, int first, int count) {
        List<Booking> bookings = bookingService.getBookings(customer, first, count);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public long countBookingEvents(SearchParameters<BookingEvent> search) {
        return bookingEventService.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBookings(SearchParameters<Booking> search) {
        return bookingService.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getBookingById(Integer bookingId) {
        Booking booking = bookingService.getById(bookingId);
        updateBooking(booking);
        return booking;
    }

    @Override
    @Transactional(readOnly = true)
    public BookingEvent getBookingEventById(Integer bookingEventId) {
        return bookingEventService.getById(bookingEventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingEvent> getBookingEvents(SearchParameters<BookingEvent> search) {
        return bookingEventService.find(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookings(SearchParameters<Booking> search) {
        List<Booking> bookings = bookingService.find(search);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByBookingBottleId(Integer bookingBottleId) {
        List<Booking> bookings = bookingService.getAllByBookingBottleId(bookingBottleId);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByBookingEventId(Integer bookingEventId) {
        List<Booking> bookings = bookingService.getAllByBookingEventId(bookingEventId);
        for (Booking booking : bookings) {
            updateBooking(booking);
        }
        return bookings;
    }

    @Override
    @Transactional
    public Booking saveBooking(Booking booking) throws BusinessException {
        return bookingService.saveOrDelete(booking);
    }

    @Override
    @Transactional
    public BookingEvent saveBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        return bookingEventService.cleanSaveForBottles(bookingEvent);
    }

    @Override
    @Transactional
    public void deleteBooking(Booking booking) throws BusinessException {
        bookingService.delete(booking);
    }

    @Override
    @Transactional
    public void deleteBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        bookingEventService.delete(bookingEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        bookingEventService.validate(bookingEvent);
    }

    @Override
    @Transactional(readOnly = false)
    public List<BookingEvent> getBookingEventsLike(String term, SearchParameters<BookingEvent> search) {
        return bookingEventService.getAllLike(term, search);
    }

    @Override
    @Transactional(readOnly = false)
    public long countBookingEventsLike(String term, SearchParameters<BookingEvent> search) {
        return bookingEventService.countAllLike(term, search);
    }

    private void updateBooking(Booking booking) {
        if (booking != null) {
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
    }

    @Inject
    public void setBookingEventService(BookingEventService bookingEventService) {
        this.bookingEventService = bookingEventService;
    }

    @Inject
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

}

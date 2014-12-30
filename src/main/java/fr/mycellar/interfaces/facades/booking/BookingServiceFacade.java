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

import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface BookingServiceFacade {

    long countBookingEvents(SearchParameters<BookingEvent> search);

    long countBookingEventsLike(String term, SearchParameters<BookingEvent> search);

    long countBookings(SearchParameters<Booking> search);

    void deleteBooking(Booking booking) throws BusinessException;

    void deleteBookingEvent(BookingEvent bookingEvent) throws BusinessException;

    Booking getBooking(Integer bookingEventId, User customer);

    Booking getBookingById(Integer bookingId);

    BookingEvent getBookingEventById(Integer bookingEventId);

    List<BookingEvent> getBookingEvents(SearchParameters<BookingEvent> search);

    List<BookingEvent> getBookingEventsLike(String term, SearchParameters<BookingEvent> search);

    List<Booking> getBookings(SearchParameters<Booking> search);

    List<Booking> getBookingsByBookingBottleId(Integer bookingBottleId);

    List<Booking> getBookingsByBookingEventId(Integer bookingEventId);

    Map<BookingBottle, Long> getBookingsQuantities(BookingEvent bookingEvent);

    List<BookingEvent> getCurrentBookingEvents();

    Booking saveBooking(Booking booking) throws BusinessException;

    BookingEvent saveBookingEvent(BookingEvent bookingEvent) throws BusinessException;

    void validateBookingEvent(BookingEvent bookingEvent) throws BusinessException;

    BookingEvent nextBookingEvent(Integer id) throws BusinessException;

}

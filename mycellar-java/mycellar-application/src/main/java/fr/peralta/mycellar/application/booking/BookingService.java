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
package fr.peralta.mycellar.application.booking;

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.application.shared.SimpleService;
import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface BookingService extends SimpleService<Booking> {

    /**
     * @param bookingEvent
     * @param customer
     * @return
     */
    Booking getBooking(BookingEvent bookingEvent, User customer);

    /**
     * @param customer
     * @return
     */
    List<Booking> getBookings(User customer);

    /**
     * @param booking
     * @throws BusinessException
     */
    void saveOrDelete(Booking booking) throws BusinessException;

    /**
     * @param bookingEvent
     * @return
     */
    Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent);

}

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
package fr.mycellar.infrastructure.booking.repository;

import java.util.List;
import java.util.Map;

import jpasearch.repository.SimpleRepository;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;

/**
 * @author speralta
 */
public interface BookingRepository extends SimpleRepository<Booking> {

    /**
     * @param bookingEvent
     * @return
     */
    Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent);

    /**
     * @param bookingBottleId
     * @return
     */
    List<Booking> getAllByBookingBottleId(Integer bookingBottleId);

}

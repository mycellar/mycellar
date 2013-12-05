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

import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaBookingEventRepository extends JpaSimpleRepository<BookingEvent> implements BookingEventRepository {

    /**
     * Default constructor.
     */
    public JpaBookingEventRepository() {
        super(BookingEvent.class);
    }

    /**
     * Hack to change position from bottles with the unique key on position and
     * bookingEvent.
     */
    @Override
    public BookingEvent cleanSaveForBottles(BookingEvent bookingEvent) {
        BookingEvent bookingEventInDb = getById(bookingEvent.getId());
        int offset = bookingEventInDb.getBottles().size() + bookingEvent.getBottles().size();

        // we move bottles to position out of range
        for (BookingBottle bottle : bookingEvent.getBottles()) {
            bottle.setPosition(bottle.getPosition() + offset);
        }

        // we save the booking event and flush the modifications
        BookingEvent saved = save(bookingEvent);
        getEntityManager().flush();

        // then we move the bottles to the real positions
        for (BookingBottle bottle : saved.getBottles()) {
            bottle.setPosition(bottle.getPosition() - offset);
        }
        return saved;
    }

}

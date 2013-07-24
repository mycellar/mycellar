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
package fr.mycellar.interfaces.web.json;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingBottleKeyDeserializer extends KeyDeserializer {

    private BookingServiceFacade bookingServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String[] values = key.split("-");
        if ((values == null) || (values.length != 2)) {
            throw ctxt.weirdKeyException(String.class, key, "Cannot understand key.");
        }
        Integer bookingEventId = Integer.parseInt(values[0]);
        Integer bookingBottleId = Integer.parseInt(values[1]);
        BookingEvent bookingEvent = bookingServiceFacade.getBookingEventById(bookingEventId);
        for (BookingBottle bookingBottle : bookingEvent.getBottles()) {
            if (bookingBottle.getId().equals(bookingBottleId)) {
                return bookingBottle;
            }
        }
        throw ctxt.mappingException("Cannot deserialize booking bottle key.");
    }

    /**
     * @param bookingServiceFacade
     *            the bookingServiceFacade to set
     */
    @Inject
    public void setBookingServiceFacade(BookingServiceFacade bookingServiceFacade) {
        this.bookingServiceFacade = bookingServiceFacade;
    }

}

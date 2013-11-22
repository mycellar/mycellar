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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.interfaces.facades.booking.BookingServiceFacade;
import fr.mycellar.test.FieldUtils;

/**
 * @author speralta
 */
public class BookingBottleKeyDeserializerTest {

    private BookingBottleKeyDeserializer deserializer;

    @Before
    public void setUp() {
        deserializer = new BookingBottleKeyDeserializer();
    }

    @Test
    public void test() throws Exception {
        BookingServiceFacade bookingServiceFacade = Mockito.mock(BookingServiceFacade.class);
        deserializer.setBookingServiceFacade(bookingServiceFacade);

        BookingEvent bookingEvent = new BookingEvent();
        FieldUtils.setId(bookingEvent, 2);
        BookingBottle bookingBottle = new BookingBottle();
        FieldUtils.setId(bookingBottle, 6);
        bookingEvent.getBottles().add(bookingBottle);

        Mockito.doReturn(bookingEvent).when(bookingServiceFacade).getBookingEventById(2);

        assertEquals(deserializer.deserializeKey("2-6", null), bookingBottle);

        Mockito.verify(bookingServiceFacade).getBookingEventById(2);
    }

}

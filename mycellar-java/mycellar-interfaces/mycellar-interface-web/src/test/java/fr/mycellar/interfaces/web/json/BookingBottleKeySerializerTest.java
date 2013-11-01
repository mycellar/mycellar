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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonGenerator;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.interfaces.web.FieldUtils;

/**
 * @author speralta
 */
public class BookingBottleKeySerializerTest {

    private BookingBottleKeySerializer serializer;

    @Before
    public void setUp() {
        serializer = new BookingBottleKeySerializer();
    }

    @Test
    public void test() throws Exception {
        BookingBottle bookingBottle = new BookingBottle();
        FieldUtils.setId(bookingBottle, 6);
        BookingEvent bookingEvent = new BookingEvent();
        FieldUtils.setId(bookingEvent, 2);
        bookingBottle.setBookingEvent(bookingEvent);
        JsonGenerator jgen = Mockito.mock(JsonGenerator.class);

        serializer.serialize(bookingBottle, jgen, null);

        Mockito.verify(jgen).writeFieldName("2-6");
    }

}

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

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import fr.peralta.mycellar.application.FieldUtils;
import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.repository.BookingRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
public class BookingServiceImplTest {

    private BookingServiceImpl bookingServiceImpl;

    @Mock
    private BookingRepository bookingRepository;

    @Before
    public void setUp() {
        initMocks(this);
        bookingServiceImpl = new BookingServiceImpl();
        bookingServiceImpl.setBookingRepository(bookingRepository);
    }

    @Test
    public void testSaveOrDelete_save() throws BusinessException {
        Booking booking = new Booking();
        booking.getQuantities().put(new BookingBottle(), 1);
        bookingServiceImpl.saveOrDelete(booking);

        verify(bookingRepository, only()).save(booking);
    }

    @Test
    public void testSaveOrDelete_delete() throws BusinessException {
        Booking booking = new Booking();
        FieldUtils.setId(booking, 1);
        bookingServiceImpl.saveOrDelete(booking);

        verify(bookingRepository, only()).delete(booking);
    }

    @Test
    public void testSaveOrDelete_nothing() throws BusinessException {
        Booking booking = new Booking();
        bookingServiceImpl.saveOrDelete(booking);

        verifyZeroInteractions(bookingRepository);
    }

}

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
package fr.mycellar.application.booking.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.inject.Inject;

import jpasearch.repository.query.builder.SearchBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.booking.BookingService;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.infrastructure.booking.repository.BookingEventRepository;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class BookingEventServiceImplIT {

    private BookingEventServiceImpl bookingEventServiceImpl;

    @Inject
    private BookingService bookingService;
    @Inject
    private BookingEventRepository bookingEventRepository;
    @Mock
    private ConfigurationService configurationService;

    @Before
    public void setUp() {
        initMocks(this);
        bookingEventServiceImpl = new BookingEventServiceImpl();
        bookingEventServiceImpl.setBookingEventRepository(bookingEventRepository);
        bookingEventServiceImpl.setBookingService(bookingService);
        bookingEventServiceImpl.setConfigurationService(configurationService);
    }

    @Test
    public void test() {
        when(configurationService.getDefaultSearchSimilarity()).thenReturn(2);

        assertEquals(2, bookingEventServiceImpl.countAllLike("Campag", new SearchBuilder<BookingEvent>().build()));
        assertEquals(2, bookingEventServiceImpl.countAllLike("test", new SearchBuilder<BookingEvent>().build()));
        assertEquals(0, bookingEventServiceImpl.countAllLike("carnage", new SearchBuilder<BookingEvent>().build()));

        verify(configurationService, only());
    }
}

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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.inject.Inject;

import jpasearch.repository.query.builder.SearchBuilder;

import org.joda.time.LocalDate;
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
import fr.mycellar.application.wine.WineService;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;
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
    private WineService wineService;
    @Inject
    private BookingEventRepository bookingEventRepository;
    @Mock
    private ConfigurationService configurationService;

    @Before
    public void setUp() {
        initMocks(this);
        bookingEventServiceImpl = new BookingEventServiceImpl();
        bookingEventServiceImpl.setBookingEventRepository(bookingEventRepository);
        bookingEventServiceImpl.setConfigurationService(configurationService);
        bookingEventServiceImpl.setWineService(wineService);
    }

    @Test
    public void test() {
        when(configurationService.getDefaultSearchSimilarity()).thenReturn(2);

        assertEquals(2, bookingEventServiceImpl.countAllLike("Campag", new SearchBuilder<BookingEvent>().build()));
        assertEquals(2, bookingEventServiceImpl.countAllLike("test", new SearchBuilder<BookingEvent>().build()));
        assertEquals(0, bookingEventServiceImpl.countAllLike("carnage", new SearchBuilder<BookingEvent>().build()));

        verify(configurationService, atLeastOnce()).getDefaultSearchSimilarity();
    }

    @Test
    public void test_next() throws Exception {
        Format bottleFormat = new Format();
        bottleFormat.setName("Format1");
        bottleFormat.setCapacity(1f);
        Format magnumFormat = new Format();
        magnumFormat.setName("Format2");
        magnumFormat.setCapacity(2f);

        BookingEvent original = new BookingEvent();
        original.setName("Name");
        original.setStart(new LocalDate());
        original.setEnd(new LocalDate().plusWeeks(1));
        BookingBottle bookingBottle = new BookingBottle();
        bookingBottle.setBookingEvent(original);
        bookingBottle.setBottle(new Bottle());
        bookingBottle.getBottle().setFormat(bottleFormat);
        bookingBottle.getBottle().setWine(wineService.getById(1));
        bookingBottle.setMax(0);
        bookingBottle.setPosition(1);
        bookingBottle.setPrice(10f);
        bookingBottle.setUrl("");
        original.getBottles().add(bookingBottle);
        bookingBottle = new BookingBottle();
        bookingBottle.setBookingEvent(original);
        bookingBottle.setBottle(new Bottle());
        bookingBottle.getBottle().setFormat(magnumFormat);
        bookingBottle.getBottle().setWine(wineService.getById(1));
        bookingBottle.setMax(0);
        bookingBottle.setPosition(2);
        bookingBottle.setPrice(10f);
        bookingBottle.setUrl("");
        original.getBottles().add(bookingBottle);
        original = bookingEventRepository.save(original);

        BookingEvent bookingEvent = bookingEventServiceImpl.nextBookingEvent(original.getId());
        assertEquals(2, bookingEvent.getBottles().size());
        Wine wine1 = null;
        Wine wine2 = null;
        for (BookingBottle bottle : bookingEvent.getBottles()) {
            if (wine1 == null) {
                wine1 = bottle.getBottle().getWine();
            }
            wine2 = bottle.getBottle().getWine();
            assertEquals(wine1, wine2);
        }

    }
}

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

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class JpaBookingRepositoryIT {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaBookingRepository jpaBookingRepository;

    @Test
    @Rollback
    public void getQuantities_test() {
        BookingEvent bookingEvent = new BookingEvent();
        BookingBottle bookingBottle1 = new BookingBottle();
        bookingBottle1.setBookingEvent(bookingEvent);
        bookingBottle1.setBottle(new Bottle());
        bookingBottle1.getBottle().setFormat(entityManager.find(Format.class, 1));
        bookingBottle1.getBottle().setWine(entityManager.find(Wine.class, 1));
        bookingBottle1.setMax(0);
        bookingBottle1.setPosition(0);
        bookingBottle1.setPrice(30f);
        bookingBottle1.setUrl("http://www.url.com");
        BookingBottle bookingBottle2 = new BookingBottle();
        bookingBottle2.setBookingEvent(bookingEvent);
        bookingBottle2.setBottle(new Bottle());
        bookingBottle2.getBottle().setFormat(entityManager.find(Format.class, 1));
        bookingBottle2.getBottle().setWine(entityManager.find(Wine.class, 2));
        bookingBottle2.setMax(0);
        bookingBottle2.setPosition(1);
        bookingBottle2.setPrice(30f);
        bookingBottle2.setUrl("http://www.url.com");
        bookingEvent.setStart(new LocalDate().minusDays(5));
        bookingEvent.setEnd(new LocalDate().plusDays(5));
        bookingEvent.setName("Test");
        bookingEvent.getBottles().add(bookingBottle1);
        bookingEvent.getBottles().add(bookingBottle2);
        entityManager.persist(bookingEvent);

        Iterator<BookingBottle> iterator = bookingEvent.getBottles().iterator();
        bookingBottle1 = iterator.next();
        bookingBottle2 = iterator.next();

        Booking booking = new Booking();
        booking.setBookingEvent(bookingEvent);
        booking.setCustomer(entityManager.find(User.class, 1));
        booking.getQuantities().put(bookingBottle1, 1);
        booking.getQuantities().put(bookingBottle2, 1);
        entityManager.persist(booking);
        booking = new Booking();
        booking.setBookingEvent(bookingEvent);
        booking.setCustomer(entityManager.find(User.class, 2));
        booking.getQuantities().put(bookingBottle1, 1);
        booking.getQuantities().put(bookingBottle2, 3);
        entityManager.persist(booking);

        entityManager.flush();

        Map<BookingBottle, Long> expected = new HashMap<>();
        expected.put(bookingBottle1, 2l);
        expected.put(bookingBottle2, 4l);

        assertEquals(expected, jpaBookingRepository.getQuantities(bookingEvent));
    }

}

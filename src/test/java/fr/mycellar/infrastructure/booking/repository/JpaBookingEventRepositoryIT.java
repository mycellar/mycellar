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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context-infrastructure-test.xml" })
@Transactional
public class JpaBookingEventRepositoryIT {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaBookingEventRepository jpaBookingEventRepository;

    @Test
    @Rollback
    public void cleanSave() {
        BookingEvent bookingEvent = jpaBookingEventRepository.getById(1);

        entityManager.detach(bookingEvent);

        for (BookingBottle bottle : bookingEvent.getBottles()) {
            entityManager.detach(bottle);
            switch (bottle.getPosition()) {
            case 0:
                bottle.setPosition(1);
                break;
            case 1:
                bottle.setPosition(0);
                break;
            default:
                // do nothing
                break;
            }
        }
        entityManager.flush();
        jpaBookingEventRepository.cleanSaveForBottles(bookingEvent);
        entityManager.flush();
    }

}

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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;

import jpasearch.repository.JpaSimpleRepository;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.Booking_;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaBookingRepository extends JpaSimpleRepository<Booking, Integer> implements BookingRepository {

    /**
     * Default constructor.
     */
    public JpaBookingRepository() {
        super(Booking.class);
    }

    @Override
    public Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Booking> query = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);
        List<Booking> bookings = getEntityManager().createQuery(query.where(criteriaBuilder.equal(root.get(Booking_.bookingEvent), bookingEvent))).getResultList();
        Map<BookingBottle, Long> result = new LinkedHashMap<BookingBottle, Long>();
        for (BookingBottle bottle : bookingEvent.getBottles()) {
            result.put(bottle, 0l);
        }
        for (Booking booking : bookings) {
            for (Entry<BookingBottle, Integer> quantities : booking.getQuantities().entrySet()) {
                result.put(quantities.getKey(), result.get(quantities.getKey()) + quantities.getValue());
            }
        }
        return result;
    }

    @Override
    public List<Booking> getAllByBookingBottleId(Integer bookingBottleId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Booking> query = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        BookingBottle bookingBottle = getEntityManager().find(BookingBottle.class, bookingBottleId);
        MapJoin<Booking, BookingBottle, Integer> quantities = root.join(Booking_.quantities);
        return getEntityManager().createQuery(query.where( //
                criteriaBuilder.equal(quantities.key(), bookingBottle), //
                criteriaBuilder.notEqual(quantities.value(), 0) //
                )).getResultList();
    }
}

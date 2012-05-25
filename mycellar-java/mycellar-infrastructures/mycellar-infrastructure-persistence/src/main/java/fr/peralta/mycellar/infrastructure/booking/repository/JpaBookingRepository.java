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
package fr.peralta.mycellar.infrastructure.booking.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.repository.BookingOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingOrderEnum;
import fr.peralta.mycellar.domain.booking.repository.BookingRepository;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaBookingRepository extends
        JpaEntityRepository<Booking, BookingOrderEnum, BookingOrder> implements BookingRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Booking> query = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);
        List<Booking> bookings = getEntityManager().createQuery(
                query.where(criteriaBuilder.equal(root.get("bookingEvent"), bookingEvent)))
                .getResultList();
        Map<BookingBottle, Long> result = new HashMap<BookingBottle, Long>();
        for (BookingBottle bottle : bookingEvent.getBottles()) {
            result.put(bottle, 0l);
        }
        for (Booking booking : bookings) {
            for (Entry<BookingBottle, Integer> quantities : booking.getQuantities().entrySet()) {
                result.put(quantities.getKey(),
                        result.get(quantities.getKey()) + quantities.getValue());
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking getBookingByEventAndCustomer(BookingEvent bookingEvent, User customer) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Booking> query = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(
                            criteriaBuilder.equal(root.get("bookingEvent"), bookingEvent),
                            criteriaBuilder.equal(root.get("customer"), customer)))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Booking> getBookings(User customer) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Booking> query = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = query.from(Booking.class);

        return getEntityManager().createQuery(
                orderBy(query.select(root).where(
                        criteriaBuilder.equal(root.get("customer"), customer)),
                        root,
                        new BookingOrder().add(BookingOrderEnum.EVENT_END, OrderWayEnum.DESC).add(
                                BookingOrderEnum.EVENT_NAME, OrderWayEnum.ASC), criteriaBuilder,
                        JoinType.LEFT)).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Booking> getEntityClass() {
        return Booking.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Booking> root, BookingOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case CUSTOMER_EMAIL:
            return root.get("customer").get("email");
        case EVENT_END:
            return root.get("bookingEvent").get("end");
        case EVENT_NAME:
            return root.get("bookingEvent").get("name");
        case EVENT_START:
            return root.get("bookingEvent").get("start");
        default:
            throw new IllegalStateException("Unknown " + BookingOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

}

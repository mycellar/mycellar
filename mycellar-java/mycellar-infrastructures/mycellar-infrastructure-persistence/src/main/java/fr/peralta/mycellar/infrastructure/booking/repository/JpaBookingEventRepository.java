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

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrderEnum;
import fr.peralta.mycellar.domain.booking.repository.BookingEventRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaBookingEventRepository extends
        JpaEntityRepository<BookingEvent, BookingEventOrderEnum, BookingEventOrder> implements
        BookingEventRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getCurrentBookingEvents() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BookingEvent> query = criteriaBuilder.createQuery(BookingEvent.class);
        Root<BookingEvent> root = query.from(BookingEvent.class);
        return getEntityManager().createQuery(
                query.select(root)
                        .where(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate> get("start"),
                                new LocalDate()),
                                criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate> get("end"),
                                        new LocalDate()))
                        .orderBy(criteriaBuilder.asc(root.get("end")),
                                criteriaBuilder.asc(root.get("name")))).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getAllLike(String term) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BookingEvent> query = criteriaBuilder.createQuery(BookingEvent.class);
        Root<BookingEvent> root = query.from(BookingEvent.class);
        return getEntityManager().createQuery(
                query.select(root).where(
                        criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("name")), "%"
                                + term.toLowerCase() + "%"))).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<BookingEvent> getEntityClass() {
        return BookingEvent.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<BookingEvent> root, BookingEventOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case END:
            return root.get("end");
        case NAME:
            return root.get("name");
        case START:
            return root.get("start");
        default:
            throw new IllegalStateException("Unknown "
                    + BookingEventOrderEnum.class.getSimpleName() + " value [" + order + "].");
        }
    }

}

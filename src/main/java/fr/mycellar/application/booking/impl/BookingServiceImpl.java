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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.SearchBuilder;
import fr.mycellar.application.booking.BookingService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.booking.Booking_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.booking.repository.BookingRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingServiceImpl extends AbstractSimpleService<Booking, BookingRepository> implements BookingService {

    private BookingRepository bookingRepository;

    @Override
    public Map<BookingBottle, Long> getQuantities(BookingEvent bookingEvent) {
        return bookingRepository.getQuantities(bookingEvent);
    }

    @Override
    public Booking saveOrDelete(Booking booking) throws BusinessException {
        int sum = 0;
        for (Entry<BookingBottle, Integer> bottle : booking.getQuantities().entrySet()) {
            if (bottle.getValue() != null) {
                sum += bottle.getValue();
            } else {
                bottle.setValue(0);
            }
        }
        if (sum > 0) {
            return save(booking);
        } else if ((booking.getId() != null) && (booking.getId() > 0)) {
            delete(booking);
        }
        return null;
    }

    @Override
    public long countBookings(User customer) {
        return bookingRepository.findCount(new SearchBuilder<Booking>() //
                .on(Booking_.customer).equalsTo(customer).build());
    }

    @Override
    public List<Booking> getBookings(User customer, int first, int count) {
        return bookingRepository.find(new SearchBuilder<Booking>() //
                .on(Booking_.customer).equalsTo(customer).and() //
                .orderBy(Booking_.bookingEvent).and(BookingEvent_.start).desc() //
                .paginate(first, count).build());
    }

    @Override
    public Booking getBooking(BookingEvent bookingEvent, User customer) {
        Booking booking = bookingRepository.findUniqueOrNone(new SearchBuilder<Booking>() //
                .on(Booking_.bookingEvent).equalsTo(bookingEvent) //
                .on(Booking_.customer).equalsTo(customer).build());
        if (booking == null) {
            booking = new Booking();
            booking.setCustomer(customer);
            booking.setBookingEvent(bookingEvent);
        }
        return booking;
    }

    @Override
    public List<Booking> getAllByBookingBottleId(Integer bookingBottleId) {
        return bookingRepository.getAllByBookingBottleId(bookingBottleId);
    }

    @Override
    public List<Booking> getAllByBookingEventId(Integer bookingEventId) {
        return bookingRepository.find(new SearchBuilder<Booking>() //
                .on(Booking_.bookingEvent).to(BookingEvent_.id).equalsTo(bookingEventId).build());
    }

    @Override
    public void validate(Booking entity) throws BusinessException {
        Booking existing = bookingRepository.findUniqueOrNone(new SearchBuilder<Booking>() //
                .on(Booking_.bookingEvent).equalsTo(entity.getBookingEvent()) //
                .on(Booking_.customer).equalsTo(entity.getCustomer()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.BOOKING_00001);
        }
    }

    @Override
    protected BookingRepository getRepository() {
        return bookingRepository;
    }

    @Inject
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

}

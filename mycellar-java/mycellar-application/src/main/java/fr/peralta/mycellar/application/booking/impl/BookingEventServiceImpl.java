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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.booking.BookingEventService;
import fr.peralta.mycellar.application.shared.AbstractEntityService;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrder;
import fr.peralta.mycellar.domain.booking.repository.BookingEventOrderEnum;
import fr.peralta.mycellar.domain.booking.repository.BookingEventRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
@Service
public class BookingEventServiceImpl
        extends
        AbstractEntityService<BookingEvent, BookingEventOrderEnum, BookingEventOrder, BookingEventRepository>
        implements BookingEventService {

    private BookingEventRepository bookingEventRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventRepository.getCurrentBookingEvents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getAllLike(String term) {
        return bookingEventRepository.getAllLike(term);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(BookingEvent entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookingEventRepository getRepository() {
        return bookingEventRepository;
    }

    /**
     * @param bookingEventRepository
     *            the bookingEventRepository to set
     */
    @Autowired
    public void setBookingEventRepository(BookingEventRepository bookingEventRepository) {
        this.bookingEventRepository = bookingEventRepository;
    }

}

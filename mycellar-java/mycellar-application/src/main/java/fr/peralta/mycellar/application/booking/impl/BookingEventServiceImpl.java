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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.joda.time.LocalDate;

import fr.peralta.mycellar.application.booking.BookingEventService;
import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.booking.BookingEvent_;
import fr.peralta.mycellar.domain.booking.repository.BookingEventRepository;
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.Range;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingEventServiceImpl extends
        AbstractSimpleService<BookingEvent, BookingEventRepository> implements BookingEventService {

    private BookingEventRepository bookingEventRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventRepository.find(new SearchParameters() //
                .range(new Range<BookingEvent, LocalDate>( //
                        BookingEvent_.start, null, new LocalDate())) //
                .range(new Range<BookingEvent, LocalDate>( //
                        BookingEvent_.end, new LocalDate(), null)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingEvent> getAllLike(String term) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setName(term);
        return bookingEventRepository.find(new SearchParameters().term(term, NamedEntity_.name));
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
    @Inject
    public void setBookingEventRepository(BookingEventRepository bookingEventRepository) {
        this.bookingEventRepository = bookingEventRepository;
    }

}

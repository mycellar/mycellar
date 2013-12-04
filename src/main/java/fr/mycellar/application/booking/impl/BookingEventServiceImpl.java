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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.joda.time.LocalDate;

import fr.mycellar.application.booking.BookingEventService;
import fr.mycellar.application.booking.BookingService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.booking.Booking_;
import fr.mycellar.domain.booking.repository.BookingEventRepository;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.shared.repository.Range;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingEventServiceImpl extends AbstractSimpleService<BookingEvent, BookingEventRepository> implements BookingEventService {

    private BookingEventRepository bookingEventRepository;

    private BookingService bookingService;

    @Override
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventRepository.find(new SearchParameters() //
                .range(new Range<BookingEvent, LocalDate>(null, new LocalDate(), BookingEvent_.start), //
                        new Range<BookingEvent, LocalDate>(new LocalDate(), null, BookingEvent_.end)));
    }

    @Override
    public List<BookingEvent> getAllLike(String term) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setName(term);
        return bookingEventRepository.find(new SearchParameters().term(NamedEntity_.name, term));
    }

    @Override
    public void validate(BookingEvent entity) throws BusinessException {

    }

    @Override
    protected void validateDelete(BookingEvent entity) throws BusinessException {
        if (bookingService.count(new SearchParameters() //
                .property(Booking_.bookingEvent, entity)) > 0) {
            throw new BusinessException(BusinessError.BOOKINGEVENT_00001);
        }
    }

    @Override
    protected BookingEventRepository getRepository() {
        return bookingEventRepository;
    }

    @Inject
    public void setBookingEventRepository(BookingEventRepository bookingEventRepository) {
        this.bookingEventRepository = bookingEventRepository;
    }

    @Inject
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

}

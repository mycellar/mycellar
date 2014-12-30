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

import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.SearchBuilder;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.booking.BookingEventService;
import fr.mycellar.application.booking.BookingService;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.wine.WineService;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.booking.Booking_;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.infrastructure.booking.repository.BookingEventRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class BookingEventServiceImpl extends AbstractSearchableService<BookingEvent, BookingEventRepository> implements BookingEventService {

    private BookingEventRepository bookingEventRepository;

    private WineService wineService;
    private BookingService bookingService;
    private ConfigurationService configurationService;

    @Override
    public BookingEvent nextBookingEvent(Integer id) throws BusinessException {
        BookingEvent bookingEvent = getById(id);
        if (bookingEvent == null) {
            throw new BusinessException(BusinessError.OTHER_00003);
        }

        BookingEvent next = new BookingEvent();
        next.setName(bookingEvent.getName());

        LocalDate today = new LocalDate();
        LocalDate friday = today.withDayOfWeek(DateTimeConstants.FRIDAY);
        if (!friday.isAfter(today)) {
            friday = friday.plusWeeks(1);
        }
        next.setStart(friday);
        next.setEnd(friday.plusWeeks(1).withDayOfWeek(DateTimeConstants.WEDNESDAY));

        for (BookingBottle bookingBottle : bookingEvent.getBottles()) {
            BookingBottle copy = new BookingBottle();
            copy.setBookingEvent(next);
            copy.setBottle(new Bottle());
            copy.getBottle().setFormat(bookingBottle.getBottle().getFormat());
            Wine wine;
            Wine original = bookingBottle.getBottle().getWine();
            if (original.getVintage() == null) {
                wine = original;
            } else {
                wine = wineService.find(original.getProducer(), original.getAppellation(), original.getType(), original.getColor(), original.getName(), original.getVintage() + 1);
                if (wine == null) {
                    wine = wineService.createVintages(original, original.getVintage() + 1, original.getVintage() + 1).get(0);
                }
            }
            copy.getBottle().setWine(wine);
            copy.setMax(bookingBottle.getMax());
            copy.setPosition(bookingBottle.getPosition());
            copy.setPrice(bookingBottle.getPrice());
            copy.setUrl(bookingBottle.getUrl());
            next.getBottles().add(copy);
        }
        return save(next);
    }

    @Override
    public List<BookingEvent> getCurrentBookingEvents() {
        return bookingEventRepository.find(new SearchBuilder<BookingEvent>() //
                .rangeOn(BookingEvent_.start).lessThan(new LocalDate()).and() //
                .rangeOn(BookingEvent_.end).moreThan(new LocalDate()).build());
    }

    @Override
    public BookingEvent cleanSaveForBottles(BookingEvent bookingEvent) throws BusinessException {
        validate(bookingEvent);
        return bookingEventRepository.cleanSaveForBottles(bookingEvent);
    }

    @Override
    public void validate(BookingEvent entity) throws BusinessException {

    }

    @Override
    protected void validateDelete(BookingEvent entity) throws BusinessException {
        if (bookingService.count(new SearchBuilder<Booking>() //
                .on(Booking_.bookingEvent).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.BOOKINGEVENT_00001);
        }
    }

    @Override
    protected SearchParameters<BookingEvent> addTermToSearchParametersParameters(String term, SearchParameters<BookingEvent> search) {
        return new SearchBuilder<BookingEvent>(search) //
                .fullText(NamedEntity_.name) //
                .searchSimilarity(configurationService.getDefaultSearchSimilarity()) //
                .andMode().search(term).build();
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

    @Inject
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    @Inject
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}

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
package fr.mycellar.application.wine.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.query.SearchParameters;
import jpasearch.repository.query.builder.SearchBuilder;
import fr.mycellar.application.booking.BookingEventService;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.application.wine.FormatService;
import fr.mycellar.domain.booking.BookingBottle_;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Bottle_;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Format_;
import fr.mycellar.infrastructure.wine.repository.FormatRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class FormatServiceImpl extends AbstractSearchableService<Format, FormatRepository> implements FormatService {

    private FormatRepository formatRepository;

    private BookingEventService bookingEventService;
    private StockService stockService;

    @Override
    public void validate(Format entity) throws BusinessException {
        Format existing = formatRepository.findUniqueOrNone(new SearchBuilder<Format>() //
                .on(Format_.capacity).equalsTo(entity.getCapacity()) //
                .on(NamedEntity_.name).equalsTo(entity.getName()).build());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.FORMAT_00001);
        }
    }

    @Override
    protected void validateDelete(Format entity) throws BusinessException {
        if (stockService.count(new SearchBuilder<Stock>() //
                .on(Stock_.bottle).to(Bottle_.format).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.FORMAT_00003);
        }
        if (bookingEventService.count(new SearchBuilder<BookingEvent>() //
                .on(BookingEvent_.bottles).to(BookingBottle_.bottle).to(Bottle_.format).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.FORMAT_00002);
        }
    }

    @Override
    protected SearchParameters<Format> addTermToSearchParametersParameters(String term, SearchParameters<Format> searchParameters) {
        return new SearchBuilder<>(searchParameters).fullText(NamedEntity_.name).andMode().search(term).build();
    }

    @Override
    protected FormatRepository getRepository() {
        return formatRepository;
    }

    @Inject
    public void setFormatRepository(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

    @Inject
    public void setBookingEventService(BookingEventService bookingEventService) {
        this.bookingEventService = bookingEventService;
    }

    @Inject
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.booking.BookingEventService;
import fr.mycellar.application.shared.AbstractSearchableService;
import fr.mycellar.application.stock.StockService;
import fr.mycellar.application.wine.WineService;
import fr.mycellar.domain.booking.BookingBottle_;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Bottle_;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.Stock_;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.WineColorEnum;
import fr.mycellar.domain.wine.WineTypeEnum;
import fr.mycellar.domain.wine.Wine_;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.wine.repository.WineRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class WineServiceImpl extends AbstractSearchableService<Wine, WineRepository> implements WineService {

    private WineRepository wineRepository;

    private BookingEventService bookingEventService;
    private StockService stockService;

    @Override
    public Wine find(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage) {
        return wineRepository.findUniqueOrNone(new SearchBuilder<Wine>() //
                .property(Wine_.producer).equalsTo(producer) //
                .property(Wine_.appellation).equalsTo(appellation) //
                .property(Wine_.type).equalsTo(type) //
                .property(Wine_.color).equalsTo(color) //
                .property(NamedEntity_.name).equalsTo(name) //
                .property(Wine_.vintage).equalsTo(vintage).build());
    }

    @Override
    public void validate(Wine entity) throws BusinessException {
        Wine existing = find(entity.getProducer(), entity.getAppellation(), entity.getType(), entity.getColor(), entity.getName(), entity.getVintage());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.WINE_00001);
        }
    }

    @Override
    protected void validateDelete(Wine entity) throws BusinessException {
        if (stockService.count(new SearchBuilder<Stock>() //
                .property(Stock_.bottle).to(Bottle_.wine).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.WINE_00002);
        }
        if (bookingEventService.count(new SearchBuilder<BookingEvent>() //
                .property(BookingEvent_.bottles).to(BookingBottle_.bottle).to(Bottle_.wine).equalsTo(entity).build()) > 0) {
            throw new BusinessException(BusinessError.WINE_00003);
        }
    }

    @Override
    protected SearchParameters<Wine> addTermToSearchParametersParameters(String term, SearchParameters<Wine> search) {
        // TODO add terms
        return search;
    }

    // private void restrictFromHibernateSearch(String input, SearchBuilder
    // searchParameters) {
    // restrictFromHibernateSearch(searchParameters, Country.class,
    // NamedEntity_.name, input, //
    // Wine_.appellation, Appellation_.region, Region_.country, Country_.id);
    // restrictFromHibernateSearch(searchParameters, Region.class,
    // NamedEntity_.name, input, //
    // Wine_.appellation, Appellation_.region, Region_.id);
    // restrictFromHibernateSearch(searchParameters, Appellation.class,
    // NamedEntity_.name, input, //
    // Wine_.appellation, Appellation_.id);
    // restrictFromHibernateSearch(searchParameters, Producer.class,
    // NamedEntity_.name, input, //
    // Wine_.producer, Producer_.id);
    // restrictFromHibernateSearch(searchParameters, Wine.class,
    // NamedEntity_.name, input, //
    // Wine_.id);
    // Scanner vintageScanner = new Scanner(input);
    // String vintageString = vintageScanner.findInLine("[0-9]{4}");
    // if (StringUtils.isNotBlank(vintageString)) {
    // Integer vintage = Integer.parseInt(vintageString);
    // searchParameters.property(Wine_.vintage, vintage);
    // }
    // vintageScanner.close();
    // }
    //
    // private <X> void restrictFromHibernateSearch(SearchBuilder
    // searchParameters, Class<X> from, SingularAttribute<? super X, String>
    // attribute, String input, Attribute<?, ?>... attributes) {
    // SearchBuilder fullTextSearchParameters = new
    // SearchBuilder().term(attribute, input).searchSimilarity(null);
    // List<Serializable> ids = hibernateSearchUtil.findId(from,
    // fullTextSearchParameters);
    // if ((ids == null) || ids.isEmpty()) {
    // ids = hibernateSearchUtil.findId(from,
    // fullTextSearchParameters.searchSimilarity(1));
    // }
    // if ((ids == null) || ids.isEmpty()) {
    // ids = hibernateSearchUtil.findId(from,
    // fullTextSearchParameters.searchSimilarity(2));
    // }
    // if ((ids != null) && (ids.size() > 0)) {
    // List<Integer> realIds = new ArrayList<>();
    // for (Serializable id : ids) {
    // realIds.add((Integer) id);
    // }
    // PropertySelector<Wine, Integer> selector = new
    // PropertySelector<>(attributes);
    // selector.setSelected(realIds);
    // searchParameters.property(selector);
    // }
    // }

    @Override
    public List<Wine> createVintages(Wine wine, int from, int to) throws BusinessException {
        if (from > to) {
            throw new IllegalArgumentException("From (" + from + ") must be before to (" + to + ").");
        }
        List<Wine> wines = new ArrayList<Wine>();
        for (int i = from; i <= to; i++) {
            wines.add(createVintage(wine, i));
        }
        for (Iterator<Wine> iterator = wines.iterator(); iterator.hasNext();) {
            try {
                save(iterator.next());
            } catch (BusinessException e) {
                if (e.getBusinessError() != BusinessError.WINE_00001) {
                    throw e;
                } else {
                    iterator.remove();
                }
            }
        }
        return wines;
    }

    private Wine createVintage(Wine wine, int year) {
        Wine copy = new Wine();
        copy.setAppellation(wine.getAppellation());
        copy.setColor(wine.getColor());
        copy.setDescription(wine.getDescription());
        copy.setName(wine.getName());
        copy.setProducer(wine.getProducer());
        copy.setRanking(wine.getRanking());
        copy.setType(wine.getType());
        copy.setVintage(year);
        return copy;
    }

    @Override
    protected WineRepository getRepository() {
        return wineRepository;
    }

    @Inject
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
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

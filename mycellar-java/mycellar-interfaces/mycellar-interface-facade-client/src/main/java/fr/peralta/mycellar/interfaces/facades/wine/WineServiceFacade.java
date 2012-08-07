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
package fr.peralta.mycellar.interfaces.facades.wine;

import java.util.List;
import java.util.Map;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationOrder;
import fr.peralta.mycellar.domain.wine.repository.CountryOrder;
import fr.peralta.mycellar.domain.wine.repository.FormatOrder;
import fr.peralta.mycellar.domain.wine.repository.ProducerOrder;
import fr.peralta.mycellar.domain.wine.repository.RegionOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @param searchForm
     * @return
     */
    long countAppellations(SearchForm searchForm);

    /**
     * @param searchForm
     * @return
     */
    long countCountries(SearchForm searchForm);

    /**
     * @param searchForm
     * @return
     */
    long countFormats(SearchForm searchForm);

    /**
     * @param searchForm
     * @return
     */
    long countProducers(SearchForm searchForm);

    /**
     * @param searchForm
     * @return
     */
    long countRegions(SearchForm searchForm);

    /**
     * @param searchForm
     * @return
     */
    long countWines(SearchForm searchForm);

    /**
     * @param appellation
     * @throws BusinessException
     */
    void deleteAppellation(Appellation appellation) throws BusinessException;

    /**
     * @param country
     * @throws BusinessException
     */
    void deleteCountry(Country country) throws BusinessException;

    /**
     * @param format
     * @throws BusinessException
     */
    void deleteFormat(Format format) throws BusinessException;

    /**
     * @param producer
     * @throws BusinessException
     */
    void deleteProducer(Producer producer) throws BusinessException;

    /**
     * @param region
     * @throws BusinessException
     */
    void deleteRegion(Region region) throws BusinessException;

    /**
     * @param wine
     * @throws BusinessException
     */
    void deleteWine(Wine wine) throws BusinessException;

    /**
     * @param name
     * @return
     */
    Country findCountry(String name);

    /**
     * @param producer
     * @param appellation
     * @param type
     * @param color
     * @param name
     * @param vintage
     * @return
     */
    Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type,
            WineColorEnum color, String name, Integer vintage);

    /**
     * @param appellationId
     * @return
     */
    Appellation getAppellationById(Integer appellationId);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Appellation> getAppellations(SearchForm searchForm, AppellationOrder order, int first,
            int count);

    /**
     * @param regions
     * @return
     */
    Map<Appellation, Long> getAppellations(SearchForm searchForm, CountEnum count,
            FilterEnum... filters);

    /**
     * @param searchForm
     * @param wine
     * @return
     */
    Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum wine);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<Country, Long> getCountries(SearchForm searchForm, CountEnum count, FilterEnum... filters);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Country> getCountries(SearchForm searchForm, CountryOrder orders, int first, int count);

    /**
     * @param countryId
     * @return
     */
    Country getCountryById(Integer countryId);

    /**
     * @param formatId
     * @return
     */
    Format getFormatById(Integer formatId);

    /**
     * @return
     */
    Map<Format, Long> getFormats(SearchForm searchForm, CountEnum count, FilterEnum... filters);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Format> getFormats(SearchForm searchForm, FormatOrder order, int first, int count);

    /**
     * @param producerId
     * @return
     */
    Producer getProducerById(Integer producerId);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Producer> getProducers(SearchForm searchForm, ProducerOrder order, int first, int count);

    /**
     * @param term
     * @return
     */
    List<Producer> getProducersLike(String term);

    /**
     * @param regionId
     * @return
     */
    Region getRegionById(Integer regionId);

    /**
     * @param countries
     * @return
     */
    Map<Region, Long> getRegions(SearchForm searchForm, CountEnum count, FilterEnum... filters);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Region> getRegions(SearchForm searchForm, RegionOrder order, int first, int count);

    /**
     * @param searchForm
     * @param count
     * @return
     */
    Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum count);

    /**
     * @param wineId
     * @return
     */
    Wine getWineById(Integer wineId);

    /**
     * @param searchForm
     * @param order
     * @param first
     * @param count
     * @return
     */
    List<Wine> getWines(SearchForm searchForm, WineOrder order, int first, int count);

    /**
     * @param appellation
     * @throws BusinessException
     */
    void saveAppellation(Appellation appellation) throws BusinessException;

    /**
     * @param country
     * @throws BusinessException
     */
    void saveCountry(Country country) throws BusinessException;

    /**
     * @param format
     * @throws BusinessException
     */
    void saveFormat(Format format) throws BusinessException;

    /**
     * @param producer
     * @throws BusinessException
     */
    void saveProducer(Producer producer) throws BusinessException;

    /**
     * @param region
     * @throws BusinessException
     */
    void saveRegion(Region region) throws BusinessException;

    /**
     * @param wine
     * @throws BusinessException
     */
    void saveWine(Wine wine) throws BusinessException;

    /**
     * @param country
     * @throws BusinessException
     */
    void validateCountry(Country country) throws BusinessException;

    /**
     * @param toCopy
     * @param from
     * @param to
     * @throws BusinessException
     */
    List<Wine> createVintages(Wine toCopy, int from, int to) throws BusinessException;

}

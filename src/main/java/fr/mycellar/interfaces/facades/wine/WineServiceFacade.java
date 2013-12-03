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
package fr.mycellar.interfaces.facades.wine;

import java.util.List;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.WineColorEnum;
import fr.mycellar.domain.wine.WineTypeEnum;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
public interface WineServiceFacade {

    /**
     * @param searchParameters
     * @return
     */
    long countAppellations(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countCountries(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countFormats(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countProducers(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countRegions(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    long countWines(SearchParameters searchParameters);

    /**
     * @param toCopy
     * @param from
     * @param to
     * @throws BusinessException
     */
    List<Wine> createVintages(Wine toCopy, int from, int to) throws BusinessException;

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
    Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage);

    /**
     * @param appellationId
     * @return
     */
    Appellation getAppellationById(Integer appellationId);

    /**
     * @param searchParameters
     * @return
     */
    List<Appellation> getAppellations(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    List<Country> getCountries(SearchParameters searchParameters);

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
     * @param searchParameters
     * @return
     */
    List<Format> getFormats(SearchParameters searchParameters);

    /**
     * @param producerId
     * @return
     */
    Producer getProducerById(Integer producerId);

    /**
     * @param searchParameters
     * @return
     */
    List<Producer> getProducers(SearchParameters searchParameters);

    /**
     * @param regionId
     * @return
     */
    Region getRegionById(Integer regionId);

    /**
     * @param searchParameters
     * @return
     */
    List<Region> getRegions(SearchParameters searchParameters);

    /**
     * @param wineId
     * @return
     */
    Wine getWineById(Integer wineId);

    /**
     * @param searchParameters
     * @return
     */
    List<Wine> getWines(SearchParameters searchParameters);

    /**
     * @param appellation
     * @throws BusinessException
     */
    Appellation saveAppellation(Appellation appellation) throws BusinessException;

    /**
     * @param country
     * @return
     * @throws BusinessException
     */
    Country saveCountry(Country country) throws BusinessException;

    /**
     * @param format
     * @throws BusinessException
     */
    void saveFormat(Format format) throws BusinessException;

    /**
     * @param producer
     * @throws BusinessException
     */
    Producer saveProducer(Producer producer) throws BusinessException;

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

}

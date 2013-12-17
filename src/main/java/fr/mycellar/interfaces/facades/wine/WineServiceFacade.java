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

    long countAppellations(SearchParameters searchParameters);

    long countCountries(SearchParameters searchParameters);

    long countFormats(SearchParameters searchParameters);

    long countProducers(SearchParameters searchParameters);

    long countRegions(SearchParameters searchParameters);

    long countWines(SearchParameters searchParameters);

    long countWinesLike(String input, SearchParameters searchParameters);

    List<Wine> createVintages(Wine toCopy, int from, int to) throws BusinessException;

    void deleteAppellation(Appellation appellation) throws BusinessException;

    void deleteCountry(Country country) throws BusinessException;

    void deleteFormat(Format format) throws BusinessException;

    void deleteProducer(Producer producer) throws BusinessException;

    void deleteRegion(Region region) throws BusinessException;

    void deleteWine(Wine wine) throws BusinessException;

    Country findCountry(String name);

    Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage);

    Appellation getAppellationById(Integer appellationId);

    List<Appellation> getAppellations(SearchParameters searchParameters);

    List<Country> getCountries(SearchParameters searchParameters);

    Country getCountryById(Integer countryId);

    Format getFormatById(Integer formatId);

    List<Format> getFormats(SearchParameters searchParameters);

    Producer getProducerById(Integer producerId);

    List<Producer> getProducers(SearchParameters searchParameters);

    Region getRegionById(Integer regionId);

    List<Region> getRegions(SearchParameters searchParameters);

    Wine getWineById(Integer wineId);

    List<Wine> getWines(SearchParameters searchParameters);

    List<Wine> getWinesLike(String input, SearchParameters searchParameters);

    Appellation saveAppellation(Appellation appellation) throws BusinessException;

    Country saveCountry(Country country) throws BusinessException;

    Format saveFormat(Format format) throws BusinessException;

    Producer saveProducer(Producer producer) throws BusinessException;

    Region saveRegion(Region region) throws BusinessException;

    Wine saveWine(Wine wine) throws BusinessException;

    void validateAppellation(Appellation appellation) throws BusinessException;

    void validateCountry(Country country) throws BusinessException;

    void validateFormat(Format format) throws BusinessException;

    void validateProducer(Producer producer) throws BusinessException;

    void validateRegion(Region region) throws BusinessException;

    void validateWine(Wine wine) throws BusinessException;

}

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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.wine.AppellationService;
import fr.mycellar.application.wine.CountryService;
import fr.mycellar.application.wine.FormatService;
import fr.mycellar.application.wine.ProducerService;
import fr.mycellar.application.wine.RegionService;
import fr.mycellar.application.wine.WineService;
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
@Named("wineServiceFacade")
@Singleton
public class WineServiceFacadeImpl implements WineServiceFacade {

    private AppellationService appellationService;

    private CountryService countryService;

    private FormatService formatService;

    private ProducerService producerService;

    private RegionService regionService;

    private WineService wineService;

    @Override
    public long countAppellations(SearchParameters searchParameters) {
        return appellationService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCountries(SearchParameters searchParameters) {
        return countryService.count(searchParameters);
    }

    @Override
    public long countFormats(SearchParameters searchParameters) {
        return formatService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countProducers(SearchParameters searchParameters) {
        return producerService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countRegions(SearchParameters searchParameters) {
        return regionService.count(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public long countWines(SearchParameters searchParameters) {
        return wineService.count(searchParameters);
    }

    @Override
    @Transactional
    public void deleteAppellation(Appellation appellation) throws BusinessException {
        appellationService.delete(appellation);
    }

    @Override
    @Transactional
    public void deleteCountry(Country country) throws BusinessException {
        countryService.delete(country);
    }

    @Override
    @Transactional
    public void deleteFormat(Format format) throws BusinessException {
        formatService.delete(format);
    }

    @Override
    @Transactional
    public void deleteProducer(Producer producer) throws BusinessException {
        producerService.delete(producer);
    }

    @Override
    @Transactional
    public void deleteRegion(Region region) throws BusinessException {
        regionService.delete(region);
    }

    @Override
    @Transactional
    public void deleteWine(Wine wine) throws BusinessException {
        wineService.delete(wine);
    }

    @Override
    @Transactional(readOnly = true)
    public Country findCountry(String name) {
        return countryService.find(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage) {
        return wineService.find(producer, appellation, type, color, name, vintage);
    }

    @Override
    @Transactional(readOnly = true)
    public Appellation getAppellationById(Integer appellationId) {
        return appellationService.getById(appellationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appellation> getAppellations(SearchParameters searchParameters) {
        return appellationService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountries(SearchParameters searchParameters) {
        return countryService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public Country getCountryById(Integer countryId) {
        return countryService.getById(countryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Format getFormatById(Integer formatId) {
        return formatService.getById(formatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Format> getFormats(SearchParameters searchParameters) {
        return formatService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public Producer getProducerById(Integer producerId) {
        return producerService.getById(producerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producer> getProducers(SearchParameters searchParameters) {
        return producerService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public Region getRegionById(Integer regionId) {
        return regionService.getById(regionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> getRegions(SearchParameters searchParameters) {
        return regionService.find(searchParameters);
    }

    @Override
    @Transactional(readOnly = true)
    public Wine getWineById(Integer wineId) {
        return wineService.getById(wineId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wine> getWines(SearchParameters searchParameters) {
        return wineService.find(searchParameters);
    }

    @Override
    @Transactional
    public Appellation saveAppellation(Appellation appellation) throws BusinessException {
        return appellationService.save(appellation);
    }

    @Override
    @Transactional
    public Country saveCountry(Country country) throws BusinessException {
        return countryService.save(country);
    }

    @Override
    @Transactional
    public Format saveFormat(Format format) throws BusinessException {
        return formatService.save(format);
    }

    @Override
    @Transactional
    public Producer saveProducer(Producer producer) throws BusinessException {
        return producerService.save(producer);
    }

    @Override
    @Transactional
    public Region saveRegion(Region region) throws BusinessException {
        return regionService.save(region);
    }

    @Override
    @Transactional
    public Wine saveWine(Wine wine) throws BusinessException {
        return wineService.save(wine);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateAppellation(Appellation appellation) throws BusinessException {
        appellationService.validate(appellation);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateCountry(Country country) throws BusinessException {
        countryService.validate(country);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateFormat(Format format) throws BusinessException {
        formatService.validate(format);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateProducer(Producer producer) throws BusinessException {
        producerService.validate(producer);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateRegion(Region region) throws BusinessException {
        regionService.validate(region);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateWine(Wine wine) throws BusinessException {
        wineService.validate(wine);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Wine> createVintages(Wine toCopy, int from, int to) throws BusinessException {
        return wineService.createVintages(toCopy, from, to);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Wine> getWinesLike(String input, SearchParameters searchParameters) {
        return wineService.getWinesLike(input, searchParameters);
    }

    @Override
    @Transactional(readOnly = false)
    public long countWinesLike(String input, SearchParameters searchParameters) {
        return wineService.countWinesLike(input, searchParameters);
    }

    // BEANS METHODS

    @Inject
    public void setAppellationService(AppellationService appellationService) {
        this.appellationService = appellationService;
    }

    @Inject
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Inject
    public void setFormatService(FormatService formatService) {
        this.formatService = formatService;
    }

    @Inject
    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Inject
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    @Inject
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

}

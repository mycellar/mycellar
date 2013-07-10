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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.wine.AppellationService;
import fr.peralta.mycellar.application.wine.CountryService;
import fr.peralta.mycellar.application.wine.FormatService;
import fr.peralta.mycellar.application.wine.ProducerService;
import fr.peralta.mycellar.application.wine.RegionService;
import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
@Service
public class WineServiceFacadeImpl implements WineServiceFacade {

    private AppellationService appellationService;

    private CountryService countryService;

    private FormatService formatService;

    private ProducerService producerService;

    private RegionService regionService;

    private WineService wineService;

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAppellations(SearchParameters searchParameters) {
        return appellationService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countCountries(SearchParameters searchParameters) {
        return countryService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countFormats(SearchParameters searchParameters) {
        return formatService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countProducers(SearchParameters searchParameters) {
        return producerService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countRegions(SearchParameters searchParameters) {
        return regionService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countWines(SearchParameters searchParameters) {
        return wineService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAppellation(Appellation appellation) throws BusinessException {
        appellationService.delete(appellation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteCountry(Country country) throws BusinessException {
        countryService.delete(country);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteFormat(Format format) throws BusinessException {
        formatService.delete(format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteProducer(Producer producer) throws BusinessException {
        producerService.delete(producer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteRegion(Region region) throws BusinessException {
        regionService.delete(region);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteWine(Wine wine) throws BusinessException {
        wineService.delete(wine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Country findCountry(String name) {
        return countryService.find(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type, WineColorEnum color, String name, Integer vintage) {
        return wineService.find(producer, appellation, type, color, name, vintage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Appellation getAppellationById(Integer appellationId) {
        return appellationService.getById(appellationId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Appellation> getAppellations(SearchParameters searchParameters) {
        return appellationService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountries(SearchParameters searchParameters) {
        return countryService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Country getCountryById(Integer countryId) {
        return countryService.getById(countryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Format getFormatById(Integer formatId) {
        return formatService.getById(formatId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Format> getFormats(SearchParameters searchParameters) {
        return formatService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Producer getProducerById(Integer producerId) {
        return producerService.getById(producerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producer> getProducers(SearchParameters searchParameters) {
        return producerService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Region getRegionById(Integer regionId) {
        return regionService.getById(regionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Region> getRegions(SearchParameters searchParameters) {
        return regionService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Wine getWineById(Integer wineId) {
        return wineService.getById(wineId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Wine> getWines(SearchParameters searchParameters) {
        return wineService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Appellation saveAppellation(Appellation appellation) throws BusinessException {
        return appellationService.save(appellation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Country saveCountry(Country country) throws BusinessException {
        return countryService.save(country);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveFormat(Format format) throws BusinessException {
        formatService.save(format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Producer saveProducer(Producer producer) throws BusinessException {
        return producerService.save(producer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveRegion(Region region) throws BusinessException {
        regionService.save(region);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveWine(Wine wine) throws BusinessException {
        wineService.save(wine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void validateCountry(Country country) throws BusinessException {
        countryService.validate(country);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public List<Wine> createVintages(Wine toCopy, int from, int to) throws BusinessException {
        List<Wine> wines = wineService.createVintages(toCopy, from, to);
        for (Wine wine : wines) {
            saveWine(wine);
        }
        return wines;
    }

    /**
     * @param appellationService
     *            the appellationService to set
     */
    @Autowired
    public void setAppellationService(AppellationService appellationService) {
        this.appellationService = appellationService;
    }

    /**
     * @param countryService
     *            the countryService to set
     */
    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @param formatService
     *            the formatService to set
     */
    @Autowired
    public void setFormatService(FormatService formatService) {
        this.formatService = formatService;
    }

    /**
     * @param producerService
     *            the producerService to set
     */
    @Autowired
    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    /**
     * @param regionService
     *            the regionService to set
     */
    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * @param wineService
     *            the wineService to set
     */
    @Autowired
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

}

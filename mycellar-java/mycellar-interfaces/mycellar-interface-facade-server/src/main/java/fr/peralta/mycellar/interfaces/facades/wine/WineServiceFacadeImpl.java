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
    public long countAppellations(SearchForm searchForm) {
        return appellationService.count(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countCountries(SearchForm searchForm) {
        return countryService.count(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countFormats(SearchForm searchForm) {
        return formatService.count(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countProducers(SearchForm searchForm) {
        return producerService.count(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countRegions(SearchForm searchForm) {
        return regionService.count(searchForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countWines(SearchForm searchForm) {
        return wineService.count(searchForm);
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
    public Wine findWine(Producer producer, Appellation appellation, WineTypeEnum type,
            WineColorEnum color, String name, Integer vintage) {
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
    public List<Appellation> getAppellations(SearchForm searchForm, AppellationOrder order,
            int first, int count) {
        return appellationService.getAll(searchForm, order, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Appellation, Long> getAppellations(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return appellationService.getAll(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum count) {
        return wineService.getColors(searchForm, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Country, Long> getCountries(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return countryService.getAll(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountries(SearchForm searchForm, CountryOrder orders, int first,
            int count) {
        return countryService.getAll(searchForm, orders, first, count);
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
    public Map<Format, Long> getFormats(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return formatService.getAll(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Format> getFormats(SearchForm searchForm, FormatOrder order, int first, int count) {
        return formatService.getAll(searchForm, order, first, count);
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
    public List<Producer> getProducers(SearchForm searchForm, ProducerOrder orders, int first,
            int count) {
        return producerService.getAll(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producer> getProducersLike(String term) {
        return producerService.getAllLike(term);
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
    public Map<Region, Long> getRegions(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return regionService.getAll(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Region> getRegions(SearchForm searchForm, RegionOrder order, int first, int count) {
        return regionService.getAll(searchForm, order, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum count) {
        return wineService.getTypes(searchForm, count);
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
    public List<Wine> getWines(SearchForm searchForm, WineOrder orders, int first, int count) {
        return wineService.getAll(searchForm, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveAppellation(Appellation appellation) throws BusinessException {
        appellationService.save(appellation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveCountry(Country country) throws BusinessException {
        countryService.save(country);
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
    public void saveProducer(Producer producer) throws BusinessException {
        producerService.save(producer);
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

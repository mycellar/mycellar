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

    private CountryService countryService;

    private RegionService regionService;

    private AppellationService appellationService;

    private ProducerService producerService;

    private WineService wineService;

    private FormatService formatService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Country, Long> getCountriesWithCounts() {
        return countryService.getAllWithCounts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Region, Long> getRegionsWithCounts(Country... countries) {
        return regionService.getAllFromCountriesWithCounts(countries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Appellation, Long> getAppellationsWithCounts(Region... regions) {
        return appellationService.getAllFromRegionsWithCounts(regions);
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
    public List<Wine> getWinesLike(Wine wine) {
        return wineService.getWinesLike(wine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<WineTypeEnum, Long> getTypeWithCounts(Producer producer) {
        return wineService.getAllTypeFromProducerWithCounts(producer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<WineColorEnum, Long> getColorWithCounts(Producer producer, WineTypeEnum type) {
        return wineService.getAllColorFromProducerAndTypeWithCounts(producer, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Format, Long> getFormatWithCounts() {
        return formatService.getAllWithCounts();
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
     * @param regionService
     *            the regionService to set
     */
    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
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
     * @param producerService
     *            the producerService to set
     */
    @Autowired
    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    /**
     * @param wineService
     *            the wineService to set
     */
    @Autowired
    public void setWineService(WineService wineService) {
        this.wineService = wineService;
    }

    /**
     * @param formatService
     *            the formatService to set
     */
    @Autowired
    public void setFormatService(FormatService formatService) {
        this.formatService = formatService;
    }

}

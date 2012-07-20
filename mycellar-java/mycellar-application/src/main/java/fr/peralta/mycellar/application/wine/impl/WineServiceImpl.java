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
package fr.peralta.mycellar.application.wine.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntitySearchFormService;
import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.WineRepository;

/**
 * @author speralta
 */
@Service
public class WineServiceImpl extends
        AbstractEntitySearchFormService<Wine, WineOrderEnum, WineOrder, WineRepository> implements
        WineService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wine find(Producer producer, Appellation appellation, WineTypeEnum type,
            WineColorEnum color, String name, Integer vintage) {
        return wineRepository.find(producer, appellation, type, color, name, vintage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Wine entity) throws BusinessException {
        Wine existing = find(entity.getProducer(), entity.getAppellation(), entity.getType(),
                entity.getColor(), entity.getName(), entity.getVintage());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.WINE_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum count) {
        Map<WineTypeEnum, Long> types = wineRepository.getTypes(searchForm, count);
        for (WineTypeEnum type : WineTypeEnum.values()) {
            if (!types.containsKey(type)) {
                types.put(type, 0L);
            }
        }
        return types;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum count) {
        Map<WineColorEnum, Long> colors = wineRepository.getColors(searchForm, count);
        for (WineColorEnum color : WineColorEnum.values()) {
            if (!colors.containsKey(color)) {
                colors.put(color, 0L);
            }
        }
        return colors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected WineRepository getRepository() {
        return wineRepository;
    }

    /**
     * @param wineRepository
     *            the wineRepository to set
     */
    @Autowired
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

}

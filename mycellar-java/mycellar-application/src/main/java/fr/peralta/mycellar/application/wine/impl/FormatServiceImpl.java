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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.wine.FormatService;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.WineRepository;

/**
 * @author speralta
 */
@Service
public class FormatServiceImpl implements FormatService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Format, Long> getAllWithCounts() {
        return wineRepository.getAllFormatWithCounts();
    }

    /**
     * @param wineRepository
     *            the wineRepository to set
     */
    @Autowired
    @Qualifier("hibernate")
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

}

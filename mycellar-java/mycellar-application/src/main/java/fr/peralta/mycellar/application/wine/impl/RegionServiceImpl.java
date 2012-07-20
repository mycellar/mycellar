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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntitySearchFormService;
import fr.peralta.mycellar.application.wine.RegionService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.repository.RegionOrder;
import fr.peralta.mycellar.domain.wine.repository.RegionOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionRepository;

/**
 * @author speralta
 */
@Service
public class RegionServiceImpl extends
        AbstractEntitySearchFormService<Region, RegionOrderEnum, RegionOrder, RegionRepository>
        implements RegionService {

    private RegionRepository regionRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Region entity) throws BusinessException {
        Region existing = regionRepository.find(entity.getCountry(), entity.getName());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.REGION_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RegionRepository getRepository() {
        return regionRepository;
    }

    /**
     * @param regionRepository
     *            the regionRepository to set
     */
    @Autowired
    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

}

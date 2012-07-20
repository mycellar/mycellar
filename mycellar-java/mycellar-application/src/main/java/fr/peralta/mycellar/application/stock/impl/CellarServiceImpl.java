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
package fr.peralta.mycellar.application.stock.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntitySearchFormService;
import fr.peralta.mycellar.application.stock.CellarService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.repository.CellarOrder;
import fr.peralta.mycellar.domain.stock.repository.CellarOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.CellarRepository;

/**
 * @author speralta
 */
@Service
public class CellarServiceImpl extends
        AbstractEntitySearchFormService<Cellar, CellarOrderEnum, CellarOrder, CellarRepository>
        implements CellarService {

    private CellarRepository cellarRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Cellar entity) throws BusinessException {
        Cellar existing = cellarRepository.find(entity.getOwner(), entity.getName());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellarRepository getRepository() {
        return cellarRepository;
    }

    /**
     * @param cellarRepository
     *            the cellarRepository to set
     */
    @Autowired
    public void setCellarRepository(CellarRepository cellarRepository) {
        this.cellarRepository = cellarRepository;
    }

}

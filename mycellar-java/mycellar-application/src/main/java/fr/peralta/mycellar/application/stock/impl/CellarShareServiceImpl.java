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
import fr.peralta.mycellar.application.stock.CellarShareService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrder;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.CellarShareRepository;

/**
 * @author speralta
 */
@Service
public class CellarShareServiceImpl
        extends
        AbstractEntitySearchFormService<CellarShare, CellarShareOrderEnum, CellarShareOrder, CellarShareRepository>
        implements CellarShareService {

    private CellarShareRepository cellarShareRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(CellarShare entity) throws BusinessException {
        CellarShare existing = cellarShareRepository.find(entity.getCellar(), entity.getEmail());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_SHARE_00001);
        }
    }

    /**
     * @param cellarShareRepository
     *            the cellarShareRepository to set
     */
    @Autowired
    public void setCellarShareRepository(CellarShareRepository cellarShareRepository) {
        this.cellarShareRepository = cellarShareRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellarShareRepository getRepository() {
        return cellarShareRepository;
    }

}

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
package fr.mycellar.application.stock.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.CellarShareService;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.repository.CellarShareRepository;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class CellarShareServiceImpl extends AbstractSimpleService<CellarShare, CellarShareRepository> implements CellarShareService {

    private CellarShareRepository cellarShareRepository;

    @Override
    public void validate(CellarShare entity) throws BusinessException {
        CellarShare existing = cellarShareRepository.findUniqueOrNone(new SearchParameters() //
                .property(CellarShare_.cellar, entity.getCellar()) //
                .property(CellarShare_.email, entity.getEmail()));
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CELLAR_SHARE_00001);
        }
    }

    @Inject
    public void setCellarShareRepository(CellarShareRepository cellarShareRepository) {
        this.cellarShareRepository = cellarShareRepository;
    }

    @Override
    protected CellarShareRepository getRepository() {
        return cellarShareRepository;
    }

}

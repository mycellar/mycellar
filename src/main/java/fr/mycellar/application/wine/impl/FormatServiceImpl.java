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
package fr.mycellar.application.wine.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.wine.FormatService;
import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Format_;
import fr.mycellar.domain.wine.repository.FormatRepository;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class FormatServiceImpl extends AbstractSimpleService<Format, FormatRepository> implements FormatService {

    private FormatRepository formatRepository;

    @Override
    public void validate(Format entity) throws BusinessException {
        Format existing = formatRepository.findUniqueOrNone(new SearchParameters() //
                .property(Format_.capacity, entity.getCapacity()) //
                .property(NamedEntity_.name, entity.getName()));
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.FORMAT_00001);
        }
    }

    @Override
    protected FormatRepository getRepository() {
        return formatRepository;
    }

    @Inject
    public void setFormatRepository(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

}

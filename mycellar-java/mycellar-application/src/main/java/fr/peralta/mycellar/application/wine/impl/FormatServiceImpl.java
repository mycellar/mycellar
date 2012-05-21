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
import fr.peralta.mycellar.application.wine.FormatService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.repository.FormatOrder;
import fr.peralta.mycellar.domain.wine.repository.FormatOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.FormatRepository;

/**
 * @author speralta
 */
@Service
public class FormatServiceImpl extends
        AbstractEntitySearchFormService<Format, FormatOrderEnum, FormatOrder, FormatRepository>
        implements FormatService {

    private FormatRepository formatRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Format entity) throws BusinessException {
        if ((entity.getId() == null)
                && (formatRepository.find(entity.getName(), entity.getCapacity()) != null)) {
            throw new BusinessException(BusinessError.FORMAT_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormatRepository getRepository() {
        return formatRepository;
    }

    /**
     * @param formatRepository
     *            the formatRepository to set
     */
    @Autowired
    public void setFormatRepository(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

}

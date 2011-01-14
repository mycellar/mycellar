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
package fr.peralta.mycellar.interfaces.facades.wine.mappers;

import org.springframework.stereotype.Service;

import fr.peralta.mycellar.interfaces.facades.position.Address;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper;
import fr.peralta.mycellar.interfaces.facades.wine.Producer;

/**
 * @author speralta
 */
@Service
public class ProducerDtoMapper extends
        AbstractMapper<fr.peralta.mycellar.domain.wine.Producer, Producer> {

    @Override
    public Producer map(fr.peralta.mycellar.domain.wine.Producer producer) {
        Producer dto = new fr.peralta.mycellar.interfaces.facades.wine.Producer();
        dto.setAddress(getMapperServiceFacade().map(producer.getAddress(), Address.class));
        dto.setDescription(producer.getDescription());
        dto.setName(producer.getName());
        dto.setWebsiteUrl(producer.getWebsiteUrl());
        dto.setId(producer.getId());
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.wine.Producer> getFromClass() {
        return fr.peralta.mycellar.domain.wine.Producer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Producer> getToClass() {
        return Producer.class;
    }

}

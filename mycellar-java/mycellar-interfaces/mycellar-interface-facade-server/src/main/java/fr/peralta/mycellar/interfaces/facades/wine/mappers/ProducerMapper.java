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

import fr.peralta.mycellar.domain.position.Address;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper;

/**
 * @author speralta
 */
@Service
public class ProducerMapper extends
        AbstractMapper<fr.peralta.mycellar.interfaces.facades.wine.Producer, Producer> {

    @Override
    public Producer map(fr.peralta.mycellar.interfaces.facades.wine.Producer dto) {
        return new Producer(dto.getName(), dto.getWebsiteUrl(), dto.getDescription(),
                getMapperServiceFacade().map(dto.getAddress(), Address.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.interfaces.facades.wine.Producer> getFromClass() {
        return fr.peralta.mycellar.interfaces.facades.wine.Producer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Producer> getToClass() {
        return Producer.class;
    }

}

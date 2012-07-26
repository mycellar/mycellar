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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntitySearchFormService;
import fr.peralta.mycellar.application.wine.ProducerService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.repository.ProducerOrder;
import fr.peralta.mycellar.domain.wine.repository.ProducerOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.ProducerRepository;

/**
 * @author speralta
 */
@Service
public class ProducerServiceImpl
        extends
        AbstractEntitySearchFormService<Producer, ProducerOrderEnum, ProducerOrder, ProducerRepository>
        implements ProducerService {

    private ProducerRepository producerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Producer entity) throws BusinessException {
        Producer existing = producerRepository.find(entity.getName());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.PRODUCER_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producer> getAllLike(String term) {
        return producerRepository.getAllProducersLike(term);
    }

    /**
     * @return the producerRepository
     */
    @Override
    public ProducerRepository getRepository() {
        return producerRepository;
    }

    /**
     * @param producerRepository
     *            the producerRepository to set
     */
    @Autowired
    public void setProducerRepository(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

}

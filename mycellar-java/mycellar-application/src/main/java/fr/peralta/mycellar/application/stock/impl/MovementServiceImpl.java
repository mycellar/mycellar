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

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntitySearchFormService;
import fr.peralta.mycellar.application.stock.MovementService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Output;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.MovementRepository;

/**
 * @author speralta
 */
@Service
public class MovementServiceImpl
        extends
        AbstractEntitySearchFormService<Movement, MovementOrderEnum, MovementOrder, MovementRepository>
        implements MovementService {

    private MovementRepository movementRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Movement entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createOutput(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date,
            String destination, float price) {
        Output output = new Output();
        output.setBottle(bottle);
        output.setCellar(cellar);
        output.setDate(date);
        output.setDestination(destination);
        output.setNumber(quantity);
        output.setPrice(price);
        movementRepository.save(output);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createInput(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date,
            float charges, float price, String source) {
        Input input = new Input();
        input.setDate(date);
        input.setBottle(bottle);
        input.setCellar(cellar);
        input.setCharges(charges);
        input.setNumber(quantity);
        input.setPrice(price);
        input.setSource(source);
        movementRepository.save(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementRepository getRepository() {
        return movementRepository;
    }

    /**
     * @param movementRepository
     *            the movementRepository to set
     */
    @Autowired
    public void setMovementRepository(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

}

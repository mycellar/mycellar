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

import org.joda.time.LocalDate;

import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.MovementService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.Input;
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Output;
import fr.mycellar.domain.stock.repository.MovementRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class MovementServiceImpl extends AbstractSimpleService<Movement, MovementRepository> implements MovementService {

    private MovementRepository movementRepository;

    @Override
    public void validate(Movement entity) throws BusinessException {

    }

    @Override
    public void createOutput(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, String destination, float price) {
        Output output = new Output();
        output.setBottle(bottle);
        output.setCellar(cellar);
        output.setDate(date);
        output.setDestination(destination);
        output.setNumber(quantity);
        output.setPrice(price);
        movementRepository.save(output);
    }

    @Override
    public void createInput(Cellar cellar, Bottle bottle, Integer quantity, LocalDate date, float charges, float price, String source) {
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

    @Override
    protected MovementRepository getRepository() {
        return movementRepository;
    }

    @Inject
    public void setMovementRepository(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

}

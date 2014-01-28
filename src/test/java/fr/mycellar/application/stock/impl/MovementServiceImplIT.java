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

import static fr.mycellar.domain.DomainMatchers.hasSameProperties;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.Input;
import fr.mycellar.domain.stock.Output;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.infrastructure.stock.repository.MovementRepository;
import fr.mycellar.infrastructure.stock.repository.StockRepository;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class MovementServiceImplIT {

    private MovementServiceImpl movementServiceImpl;
    private StockServiceImpl stockServiceImpl;

    @Inject
    private MovementRepository movementRepository;

    @Inject
    private StockRepository stockRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() {
        stockServiceImpl = new StockServiceImpl();
        movementServiceImpl = new MovementServiceImpl();
        stockServiceImpl.setStockRepository(stockRepository);
        stockServiceImpl.setMovementService(movementServiceImpl);
        movementServiceImpl.setMovementRepository(movementRepository);
        movementServiceImpl.setStockService(stockServiceImpl);
    }

    @Test
    @Rollback
    public void testSave_input_new() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 2));
        bottle.setWine(entityManager.find(Wine.class, 1));

        Stock stock = stockServiceImpl.findStock(bottle, cellar);
        assertNull(stock);

        Input input = new Input();
        input.setBottle(bottle);
        input.setCellar(cellar);
        input.setCharges(30f);
        input.setDate(new LocalDate());
        input.setNumber(2);
        input.setPrice(10f);
        input.setSource("source");

        // test
        movementServiceImpl.save(input);
        stock = stockServiceImpl.findStock(bottle, cellar);

        // build expected
        Input expectedInput = new Input();
        expectedInput.setBottle(bottle);
        expectedInput.setCellar(cellar);
        expectedInput.setCharges(30f);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(2);
        expectedInput.setPrice(10f);
        expectedInput.setSource("source");

        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(2);

        // assertions
        assertThat(stock, hasSameProperties(expectedStock).without("id").without("version"));
        assertThat(input, hasSameProperties(expectedInput).without("id").without("version"));
    }

    @Test
    @Rollback
    public void testSave_input_existing() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 1));
        bottle.setWine(entityManager.find(Wine.class, 1));

        Stock stock = stockServiceImpl.findStock(bottle, cellar);
        assertNotNull(stock);

        Input input = new Input();
        input.setBottle(bottle);
        input.setCellar(cellar);
        input.setCharges(30f);
        input.setDate(new LocalDate());
        input.setNumber(2);
        input.setPrice(10f);
        input.setSource("source");

        // test
        movementServiceImpl.save(input);
        stock = stockServiceImpl.findStock(bottle, cellar);

        // build expected
        Input expectedInput = new Input();
        expectedInput.setBottle(bottle);
        expectedInput.setCellar(cellar);
        expectedInput.setCharges(30f);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(2);
        expectedInput.setPrice(10f);
        expectedInput.setSource("source");

        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(8);

        // assertions
        assertThat(stock, hasSameProperties(expectedStock).without("id").without("version"));
        assertThat(input, hasSameProperties(expectedInput).without("id").without("version"));
    }

    @Test(expected = BusinessException.class)
    @Rollback
    public void testSave_output_new() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 2));
        bottle.setWine(entityManager.find(Wine.class, 1));

        Output output = new Output();
        output.setBottle(bottle);
        output.setCellar(cellar);
        output.setDate(new LocalDate());
        output.setDestination("destination");
        output.setNumber(2);
        output.setPrice(10f);

        // test
        movementServiceImpl.save(output);
    }

    @Test
    @Rollback
    public void testSave_output_existing() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 1));
        bottle.setWine(entityManager.find(Wine.class, 1));

        Stock stock = stockServiceImpl.findStock(bottle, cellar);
        assertNotNull(stock);

        Output output = new Output();
        output.setBottle(bottle);
        output.setCellar(cellar);
        output.setDestination("destination");
        output.setDate(new LocalDate());
        output.setNumber(2);
        output.setPrice(10f);

        // test
        movementServiceImpl.save(output);
        stock = stockServiceImpl.findStock(bottle, cellar);

        // build expected
        Output expectedOutput = new Output();
        expectedOutput.setBottle(bottle);
        expectedOutput.setCellar(cellar);
        expectedOutput.setDestination("destination");
        expectedOutput.setDate(new LocalDate());
        expectedOutput.setNumber(2);
        expectedOutput.setPrice(10f);

        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(4);

        // assertions
        assertThat(stock, hasSameProperties(expectedStock).without("id").without("version"));
        assertThat(output, hasSameProperties(expectedOutput).without("id").without("version"));
    }

}

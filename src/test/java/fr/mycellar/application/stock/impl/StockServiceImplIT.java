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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

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
import fr.mycellar.domain.stock.Movement;
import fr.mycellar.domain.stock.Movement_;
import fr.mycellar.domain.stock.Output;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.stock.repository.MovementRepository;
import fr.mycellar.infrastructure.stock.repository.StockRepository;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class StockServiceImplIT {

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
    public void testSave_new() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 2));
        bottle.setWine(entityManager.find(Wine.class, 1));

        Stock stock = new Stock();
        stock.setBottle(bottle);
        stock.setCellar(cellar);
        stock.setQuantity(6);

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(stock.getCellar()) //
                .property(Movement_.bottle).equalsTo(stock.getBottle()).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.save(stock);
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(result.getCellar()) //
                .property(Movement_.bottle).equalsTo(result.getBottle()).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setCellar(result.getCellar());
        expectedStock.setBottle(result.getBottle());
        expectedStock.setQuantity(6);

        Input expectedInput = new Input();
        expectedInput.setBottle(result.getBottle());
        expectedInput.setCellar(result.getCellar());
        expectedInput.setCharges(0);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(6);
        expectedInput.setPrice(0);
        expectedInput.setSource("Régularisation");

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Input.class)));
        assertThat((Input) movements.get(0), hasSameProperties(expectedInput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

    @Test
    @Rollback
    public void testSave_existing_less() throws BusinessException {
        Stock stock = entityManager.find(Stock.class, 1);
        entityManager.detach(stock);
        stock.setQuantity(3);

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(stock.getCellar()) //
                .property(Movement_.bottle).equalsTo(stock.getBottle()).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.save(stock);
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(result.getCellar()) //
                .property(Movement_.bottle).equalsTo(result.getBottle()).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setCellar(result.getCellar());
        expectedStock.setBottle(result.getBottle());
        expectedStock.setQuantity(3);

        Output expectedOutput = new Output();
        expectedOutput.setBottle(result.getBottle());
        expectedOutput.setCellar(result.getCellar());
        expectedOutput.setDate(new LocalDate());
        expectedOutput.setNumber(3);
        expectedOutput.setPrice(0);
        expectedOutput.setDestination("Régularisation");

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Output.class)));
        assertThat((Output) movements.get(0), hasSameProperties(expectedOutput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

    @Test
    @Rollback
    public void testSave_existing_more() throws BusinessException {
        Stock stock = entityManager.find(Stock.class, 1);
        entityManager.detach(stock);
        stock.setQuantity(9);

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(stock.getCellar()) //
                .property(Movement_.bottle).equalsTo(stock.getBottle()).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.save(stock);
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(result.getCellar()) //
                .property(Movement_.bottle).equalsTo(result.getBottle()).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setCellar(result.getCellar());
        expectedStock.setBottle(result.getBottle());
        expectedStock.setQuantity(9);

        Input expectedInput = new Input();
        expectedInput.setBottle(result.getBottle());
        expectedInput.setCellar(result.getCellar());
        expectedInput.setCharges(0);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(3);
        expectedInput.setPrice(0);
        expectedInput.setSource("Régularisation");

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Input.class)));
        assertThat((Input) movements.get(0), hasSameProperties(expectedInput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

    @Test
    @Rollback
    public void testAddToStock_new() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 2));
        bottle.setWine(entityManager.find(Wine.class, 1));

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.addToStock(cellar, bottle, 6, new LocalDate(), 30f, 30f, "source");
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(6);

        Input expectedInput = new Input();
        expectedInput.setBottle(bottle);
        expectedInput.setCellar(cellar);
        expectedInput.setCharges(30f);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(6);
        expectedInput.setPrice(30f);
        expectedInput.setSource("source");

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Input.class)));
        assertThat((Input) movements.get(0), hasSameProperties(expectedInput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

    @Test
    @Rollback
    public void testAddToStock_existing() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 1));
        bottle.setWine(entityManager.find(Wine.class, 1));

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.addToStock(cellar, bottle, 6, new LocalDate(), 30f, 30f, "source");
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(12);

        Input expectedInput = new Input();
        expectedInput.setBottle(bottle);
        expectedInput.setCellar(cellar);
        expectedInput.setCharges(30f);
        expectedInput.setDate(new LocalDate());
        expectedInput.setNumber(6);
        expectedInput.setPrice(30f);
        expectedInput.setSource("source");

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Input.class)));
        assertThat((Input) movements.get(0), hasSameProperties(expectedInput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

    @Test(expected = BusinessException.class)
    @Rollback
    public void testRemoveFromStock_new() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 2));
        bottle.setWine(entityManager.find(Wine.class, 1));

        // test
        stockServiceImpl.removeFromStock(cellar, bottle, 6, new LocalDate(), "destination", 30f);
    }

    @Test
    @Rollback
    public void testRemoveFromStock_existing() throws BusinessException {
        Cellar cellar = entityManager.find(Cellar.class, 1);
        Bottle bottle = new Bottle();
        bottle.setFormat(entityManager.find(Format.class, 1));
        bottle.setWine(entityManager.find(Wine.class, 1));

        List<Movement> movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());
        assertEquals(0, movements.size());

        // test
        Stock result = stockServiceImpl.removeFromStock(cellar, bottle, 3, new LocalDate(), "destination", 30f);
        movements = movementRepository.find(new SearchBuilder<Movement>() //
                .property(Movement_.cellar).equalsTo(cellar) //
                .property(Movement_.bottle).equalsTo(bottle).build());

        // build expected
        Stock expectedStock = new Stock();
        expectedStock.setBottle(bottle);
        expectedStock.setCellar(cellar);
        expectedStock.setQuantity(3);

        Output expectedOutput = new Output();
        expectedOutput.setBottle(bottle);
        expectedOutput.setCellar(cellar);
        expectedOutput.setDestination("destination");
        expectedOutput.setDate(new LocalDate());
        expectedOutput.setNumber(3);
        expectedOutput.setPrice(30f);

        // assertions
        assertEquals(1, movements.size());
        assertThat(movements.get(0), is(instanceOf(Output.class)));
        assertThat((Output) movements.get(0), hasSameProperties(expectedOutput).without("id").without("version"));
        assertThat(result, hasSameProperties(expectedStock).without("id").without("version"));
    }

}

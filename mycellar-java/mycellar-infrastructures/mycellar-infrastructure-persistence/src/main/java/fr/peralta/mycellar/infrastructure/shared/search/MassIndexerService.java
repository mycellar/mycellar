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
package fr.peralta.mycellar.infrastructure.shared.search;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;

@Named
@Lazy(false)
public class MassIndexerService {

    private static final Logger logger = LoggerFactory.getLogger(MassIndexerService.class);

    protected static final Class<?>[] CLASSES_TO_BE_INDEXED = { BookingEvent.class, //
            Cellar.class, User.class, Appellation.class, Country.class, Format.class, //
            Producer.class, Region.class, Wine.class, Contact.class //
    };

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${massIndexer.nbThreadsToLoadObjects:1}")
    private int threadsToLoadObjects;

    @Value("${massIndexer.batchSizeToLoadObjects:10}")
    private int batchSizeToLoadObjects;

    @Value("${massIndexer.nbThreadsForSubsequentFetching:1}")
    private int threadsForSubsequentFetching;

    @PostConstruct
    public void createIndex() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            for (Class<?> classToBeIndexed : CLASSES_TO_BE_INDEXED) {
                indexClass(classToBeIndexed);
            }
        } finally {
            stopWatch.stop();
            logger.info("Indexed {} in {}", Arrays.toString(CLASSES_TO_BE_INDEXED), stopWatch.toString());
        }
    }

    /**
     * 
     */
    private void indexClass(Class<?> classToBeIndexed) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            getFullTextEntityManager(entityManager) //
                    .createIndexer(classToBeIndexed) //
                    .batchSizeToLoadObjects(batchSizeToLoadObjects) //
                    .threadsToLoadObjects(threadsToLoadObjects) //
                    .threadsForSubsequentFetching(threadsForSubsequentFetching) //
                    .startAndWait();
        } catch (InterruptedException e) {
            logger.warn("Interrupted while indexing " + classToBeIndexed.getSimpleName(), e);
            Thread.currentThread().interrupt();
        } finally {
            stopWatch.stop();
            logger.info("Indexed {} in {}", classToBeIndexed.getSimpleName(), stopWatch.toString());
        }
    }
}

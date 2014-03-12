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
package fr.mycellar.infrastructure.shared.search;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

@Named
@Lazy(false)
public class MassIndexerService {

    private static final Logger logger = LoggerFactory.getLogger(MassIndexerService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${massIndexer.nbThreadsToLoadObjects:1}")
    private int threadsToLoadObjects;

    @Value("${massIndexer.batchSizeToLoadObjects:10}")
    private int batchSizeToLoadObjects;

    @PostConstruct
    public void createIndex() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            getFullTextEntityManager(entityManager) //
                    .createIndexer() //
                    .batchSizeToLoadObjects(batchSizeToLoadObjects) //
                    .threadsToLoadObjects(threadsToLoadObjects) //
                    .startAndWait();
        } catch (InterruptedException e) {
            logger.warn("Interrupted while indexing.", e);
            Thread.currentThread().interrupt();
        } finally {
            stopWatch.stop();
            logger.info("Indexation done in {}.", stopWatch.toString());
        }
    }

}

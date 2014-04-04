/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.util;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.infrastructure.shared.repository.query.selector.TermSelector;

@Named
@Singleton
public class HibernateSearchUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateSearchUtil.class);

    private EntityManager entityManager;

    private LuceneQueryBuilder luceneQueryBuilder;

    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<? extends T> type, TermSelector<T> termSelector) {
        logger.debug("Searching {} with term {}.", new Object[] { type.getSimpleName(), termSelector });
        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);
        Query query = luceneQueryBuilder.build(fullTextEntityManager, termSelector, type);

        if (query == null) {
            return null;
        }

        FullTextQuery ftq = fullTextEntityManager.createFullTextQuery( //
                query, type);
        ftq.limitExecutionTimeTo(500, TimeUnit.MILLISECONDS);
        return ftq.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<Serializable> findId(Class<? extends T> type, TermSelector<T> termSelector) {
        logger.debug("Searching {} id with term {}.", new Object[] { type.getSimpleName(), termSelector });
        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);
        Query query = luceneQueryBuilder.build(fullTextEntityManager, termSelector, type);

        if (query == null) {
            return null;
        }

        FullTextQuery ftq = fullTextEntityManager.createFullTextQuery( //
                query, type);
        ftq.setProjection("id");
        ftq.limitExecutionTimeTo(500, TimeUnit.MILLISECONDS);
        List<Serializable> ids = new ArrayList<>();
        List<Object[]> resultList = ftq.getResultList();
        for (Object[] result : resultList) {
            ids.add((Serializable) result[0]);
        }
        return ids;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    public void setBuilder(LuceneQueryBuilder luceneQueryBuilder) {
        this.luceneQueryBuilder = luceneQueryBuilder;
    }

}
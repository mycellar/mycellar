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
package fr.peralta.mycellar.infrastructure.stock.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.StockRepository;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@Repository
@Qualifier("hibernate")
public class HibernateStockRepository implements StockRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Bottle findBottle(Wine wine, Format format) {
        if ((wine.getId() == null) || (format.getId() == null)) {
            return null;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bottle> query = criteriaBuilder.createQuery(Bottle.class);
        Root<Bottle> root = query.from(Bottle.class);

        return entityManager.createQuery(
                query.select(root).where(
                        criteriaBuilder.and(criteriaBuilder.equal(root.get("wine"), wine),
                                criteriaBuilder.equal(root.get("format"), format))))
                .getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock findStock(Bottle bottle, Cellar cellar) {
        if ((bottle.getId() == null) || (cellar.getId() == null)) {
            return null;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = criteriaBuilder.createQuery(Stock.class);
        Root<Stock> root = query.from(Stock.class);

        return entityManager.createQuery(
                query.select(root).where(
                        criteriaBuilder.and(criteriaBuilder.equal(root.get("bottle"), bottle),
                                criteriaBuilder.equal(root.get("cellar"), cellar))))
                .getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Cellar, Long> getAllCellarsWithCountsFromUser(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Cellar> root = query.from(Cellar.class);
        Expression<Long> count = criteriaBuilder.sumAsLong(root.joinSet("stocks", JoinType.LEFT)
                .<Integer> get("quantity"));

        CriteriaQuery<Tuple> select = query.multiselect(root, count);
        if (user != null) {
            select = select.where(criteriaBuilder.equal(root.<User> get("owner"), user));
        }

        List<Tuple> tuples = entityManager.createQuery(
                select.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name"))))
                .getResultList();
        Map<Cellar, Long> result = new LinkedHashMap<Cellar, Long>();
        for (Tuple tuple : tuples) {
            Long sum = tuple.get(count);
            result.put(tuple.get(root), sum != null ? sum : 0);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<Movement<?>> getAllMovementsFromCellars(Cellar... cellars) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movement> query = criteriaBuilder.createQuery(Movement.class);

        Root<Movement> root = query.from(Movement.class);
        List<Movement> queryResult = entityManager.createQuery(
                query.select(root).where(root.get("cellar").in(Arrays.asList(cellars))))
                .getResultList();
        List<Movement<?>> result = new ArrayList<Movement<?>>(queryResult.size());
        for (Movement movement : queryResult) {
            result.add(movement);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cellar save(Cellar cellar) {
        return entityManager.merge(cellar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Input save(Input input) {
        return entityManager.merge(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock save(Stock stock) {
        return entityManager.merge(stock);
    }

}

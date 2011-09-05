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
import fr.peralta.mycellar.domain.stock.StockRepository;
import fr.peralta.mycellar.domain.user.User;

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
    public void newInput(Input input) {
        entityManager.persist(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bottle findBottle(int id) {
        return entityManager.find(Bottle.class, id);
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
    @Override
    public void newCellar(Cellar cellar) {
        entityManager.persist(cellar);
    }

}

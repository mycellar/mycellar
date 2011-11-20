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
package fr.peralta.mycellar.infrastructure.stock.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Output;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.StockOrder;
import fr.peralta.mycellar.domain.stock.repository.StockOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.StockRepository;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.infrastructure.shared.repository.HibernateRepository;

/**
 * @author speralta
 */
@Repository
@Qualifier("hibernate")
@SuppressWarnings("rawtypes")
public class HibernateStockRepository extends HibernateRepository implements StockRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public long countMovements(SearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Movement> root = query.from(Movement.class);
        query = query.select(criteriaBuilder.count(root));
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getSet(FilterEnum.CELLAR), root.get("cellar"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.USER), root.get("cellar").get("owner"),
                criteriaBuilder);

        query = where(query, criteriaBuilder, predicates);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movement<?>> getMovements(SearchForm searchForm, MovementOrder orders, int first,
            int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movement> query = criteriaBuilder.createQuery(Movement.class);
        Root<Movement> root = query.from(Movement.class);
        query = query.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getSet(FilterEnum.CELLAR), root.get("cellar"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.USER), root.get("cellar").get("owner"),
                criteriaBuilder);
        query = where(query, criteriaBuilder, predicates);

        List<Order> orderList = new ArrayList<Order>();
        for (Entry<MovementOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            Expression<?> path;
            switch (entry.getKey()) {
            case DATE:
                path = root.get("date");
                break;
            default:
                throw new IllegalStateException("Unknwon "
                        + MovementOrderEnum.class.getSimpleName() + " value [" + entry.getKey()
                        + "].");
            }
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(path));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(path));
                break;
            default:
                throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                        + " value [" + entry.getValue() + "].");
            }
        }
        if (orderList.size() > 0) {
            query = query.orderBy(orderList);
        }

        List<Movement> queryResult = entityManager.createQuery(query).setFirstResult(first)
                .setMaxResults(count).getResultList();
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
    public long countStocks(SearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Stock> root = query.from(Stock.class);
        query = query.select(criteriaBuilder.count(root));
        query = where(query, root, searchForm, criteriaBuilder);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Stock> getStocks(SearchForm searchForm, StockOrder orders, int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = criteriaBuilder.createQuery(Stock.class);
        Root<Stock> root = query.from(Stock.class);
        query = query.select(root);
        query = where(query, root, searchForm, criteriaBuilder);

        List<Order> orderList = new ArrayList<Order>();
        for (Entry<StockOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            Expression<?> path;
            switch (entry.getKey()) {
            case APPELLATION_NAME:
                path = root.get("bottle").get("wine").get("appellation").get("name");
                break;
            case COUNTRY_NAME:
                path = root.get("bottle").get("wine").get("appellation").get("region")
                        .get("country").get("name");
                break;
            case NAME:
                path = root.get("bottle").get("wine").get("name");
                break;
            case REGION_NAME:
                path = root.get("bottle").get("wine").get("appellation").get("name");
                break;
            case VINTAGE:
                path = root.get("bottle").get("wine").get("vintage");
                break;
            case FORMAT_NAME:
                path = root.get("bottle").get("format").get("name");
                break;
            case QUANTITY:
                path = root.get("quantity");
                break;
            default:
                throw new IllegalStateException("Unknwon " + StockOrderEnum.class.getSimpleName()
                        + " value [" + entry.getKey() + "].");
            }
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(path));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(path));
                break;
            default:
                throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                        + " value [" + entry.getValue() + "].");
            }
        }
        if (orderList.size() > 0) {
            query = query.orderBy(orderList);
        }

        return entityManager.createQuery(query).setFirstResult(first).setMaxResults(count)
                .getResultList();
    }

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

        try {
            return entityManager.createQuery(
                    query.select(root).where(
                            criteriaBuilder.and(criteriaBuilder.equal(root.get("wine"), wine),
                                    criteriaBuilder.equal(root.get("format"), format))))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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
    public Map<Cellar, Long> getCellars(SearchForm searchForm, CountEnum countEnum) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Cellar> root = query.from(Cellar.class);
        SetJoin<Cellar, Stock> stock = root.joinSet("stocks", JoinType.LEFT);
        Expression<Long> count = getCount(countEnum, stock, criteriaBuilder);

        query = query.multiselect(root, count);
        query = where(query, stock, searchForm, criteriaBuilder);

        List<Tuple> tuples = entityManager.createQuery(
                query.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name")))).getResultList();
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
    public Output save(Output output) {
        return entityManager.merge(output);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock save(Stock stock) {
        return entityManager.merge(stock);
    }

}

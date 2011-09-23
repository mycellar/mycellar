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
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.MovementSearchForm;
import fr.peralta.mycellar.domain.stock.repository.StockOrder;
import fr.peralta.mycellar.domain.stock.repository.StockOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.StockRepository;
import fr.peralta.mycellar.domain.stock.repository.StockSearchForm;
import fr.peralta.mycellar.domain.user.User;
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
    public long countMovements(MovementSearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Movement> root = query.from(Movement.class);
        query = query.select(criteriaBuilder.count(root));
        query = where(query, root, searchForm, criteriaBuilder);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movement<?>> getMovements(MovementSearchForm searchForm, MovementOrder orders,
            int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movement> query = criteriaBuilder.createQuery(Movement.class);
        Root<Movement> root = query.from(Movement.class);
        query = query.select(root);
        query = where(query, root, searchForm, criteriaBuilder);
        List<Movement> queryResult = entityManager
                .createQuery(orderBy(query, root, orders, criteriaBuilder)).setFirstResult(first)
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
    public long countStocks(StockSearchForm searchForm) {
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
    public List<Stock> getStocks(StockSearchForm searchForm, StockOrder orders, int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = criteriaBuilder.createQuery(Stock.class);
        Root<Stock> root = query.from(Stock.class);
        query = query.select(root);
        query = where(query, root, searchForm, criteriaBuilder);
        return entityManager.createQuery(orderBy(query, root, orders, criteriaBuilder))
                .setFirstResult(first).setMaxResults(count).getResultList();
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

    /**
     * @param query
     * @param root
     * @param searchForm
     * @param criteriaBuilder
     * @return
     */
    private <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Root<Movement> root,
            MovementSearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getCellars(), root.get("cellar"));
        in(predicates, searchForm.getUser(), root.get("cellar").get("owner"), criteriaBuilder);

        return where(query, criteriaBuilder, predicates);
    }

    /**
     * @param query
     * @param root
     * @param searchForm
     * @param criteriaBuilder
     * @return
     */
    private <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Root<Stock> root,
            StockSearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getCellars(), root.get("cellar"));
        in(predicates, searchForm.getUser(), root.get("cellar").get("owner"), criteriaBuilder);
        in(predicates, searchForm.getTypes(), root.get("bottle").get("wine").get("type"));
        in(predicates, searchForm.getColors(), root.get("bottle").get("wine").get("color"));
        in(predicates, searchForm.getAppellations(),
                root.get("bottle").get("wine").get("appellation"));
        in(predicates, searchForm.getRegions(), root.get("bottle").get("wine").get("appellation")
                .get("region"));
        in(predicates, searchForm.getCountries(), root.get("bottle").get("wine").get("appellation")
                .get("region").get("country"));
        in(predicates, searchForm.getProducers(), root.get("bottle").get("wine").get("producer"));
        in(predicates, searchForm.getVintages(), root.get("bottle").get("wine").get("vintage"));
        in(predicates, searchForm.getFormats(), root.get("bottle").get("format"));

        return where(query, criteriaBuilder, predicates);
    }

    private <O> CriteriaQuery<O> orderBy(CriteriaQuery<O> query, Root<Movement> root,
            MovementOrder orders, CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<Order>();
        for (Entry<MovementOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(getPath(root, entry.getKey())));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(getPath(root, entry.getKey())));
                break;
            default:
                throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                        + " value [" + entry.getValue() + "].");
            }
        }
        if (orderList.size() > 0) {
            return query.orderBy(orderList);
        }
        return query;
    }

    /**
     * @param query
     * @param root
     * @param orders
     * @param criteriaBuilder
     * @return
     */
    private <O> CriteriaQuery<O> orderBy(CriteriaQuery<O> query, Root<Stock> root,
            StockOrder orders, CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<Order>();
        for (Entry<StockOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(getPath(root, entry.getKey())));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(getPath(root, entry.getKey())));
                break;
            default:
                throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                        + " value [" + entry.getValue() + "].");
            }
        }
        if (orderList.size() > 0) {
            return query.orderBy(orderList);
        }
        return query;
    }

    /**
     * @param root
     * @param key
     * @return
     */
    private Expression<?> getPath(Root<Stock> root, StockOrderEnum order) {
        switch (order) {
        case APPELLATION_NAME:
            return root.get("bottle").get("wine").get("appellation").get("name");
        case COUNTRY_NAME:
            return root.get("bottle").get("wine").get("appellation").get("region").get("country")
                    .get("name");
        case NAME:
            return root.get("bottle").get("wine").get("name");
        case REGION_NAME:
            return root.get("bottle").get("wine").get("appellation").get("name");
        case VINTAGE:
            return root.get("bottle").get("wine").get("vintage");
        case FORMAT_NAME:
            return root.get("bottle").get("format").get("name");
        case QUANTITY:
            return root.get("quantity");
        default:
            throw new IllegalStateException("Unknwon " + StockOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * @param key
     * @return
     */
    private Expression<?> getPath(Root<Movement> root, MovementOrderEnum order) {
        switch (order) {
        case DATE:
            return root.get("date");
        default:
            throw new IllegalStateException("Unknwon " + MovementOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }
}

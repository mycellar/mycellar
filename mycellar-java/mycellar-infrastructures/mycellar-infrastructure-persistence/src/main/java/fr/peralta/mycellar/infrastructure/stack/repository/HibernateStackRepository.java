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
package fr.peralta.mycellar.infrastructure.stack.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;
import fr.peralta.mycellar.domain.stack.repository.StackOrderEnum;
import fr.peralta.mycellar.domain.stack.repository.StackRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.HibernateRepository;

/**
 * @author speralta
 */
@Repository
@Qualifier("hibernate")
public class HibernateStackRepository extends HibernateRepository implements StackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Stack getStackById(Integer stackId) {
        return entityManager.find(Stack.class, stackId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countStacks(SearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Stack> root = query.from(Stack.class);
        query = query.select(criteriaBuilder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Stack> getStacks(SearchForm searchForm, StackOrder orders, int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stack> query = criteriaBuilder.createQuery(Stack.class);
        Root<Stack> root = query.from(Stack.class);
        query = query.select(root);

        List<Order> orderList = new ArrayList<Order>();
        for (Entry<StackOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            Expression<?> path;
            switch (entry.getKey()) {
            case COUNT:
                path = root.get("count");
                break;
            default:
                throw new IllegalStateException("Unknwon " + StackOrderEnum.class.getSimpleName()
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
    public void deleteAllStacks() {
        entityManager.createQuery("DELETE " + Stack.class.getSimpleName()).executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stack getByHashCode(int hashCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stack> query = criteriaBuilder.createQuery(Stack.class);
        Root<Stack> root = query.from(Stack.class);

        try {
            return entityManager
                    .createQuery(
                            query.select(root).where(
                                    criteriaBuilder.equal(root.get("hashCode"), hashCode)))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @return
     */
    @Override
    public Stack save(Stack stack) {
        return entityManager.merge(stack);
    }

}

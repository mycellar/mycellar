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
package fr.peralta.mycellar.infrastructure.shared.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.AbstractEntityOrder;
import fr.peralta.mycellar.domain.shared.repository.EntityRepository;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;

/**
 * @author speralta
 */
public abstract class JpaEntityRepository<E extends IdentifiedEntity, OE, O extends AbstractEntityOrder<OE, O>>
        extends JpaSimpleRepository<E> implements EntityRepository<E, OE, O> {

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        return getEntityManager().createQuery(
                query.select(criteriaBuilder.count(query.from(getEntityClass()))))
                .getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<E> getAll(O orders, long first, long count) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(getEntityClass());
        Root<E> root = query.from(getEntityClass());
        query = query.select(root);
        return getEntityManager()
                .createQuery(orderBy(query, root, orders, criteriaBuilder, JoinType.INNER))
                .setFirstResult((int) first).setMaxResults((int) count).getResultList();
    }

    // To override

    /**
     * @param root
     * @param order
     * @param joinType
     * @return
     */
    protected abstract Expression<?> getOrderByPath(Root<E> root, OE order, JoinType joinType);

    // Utils methods

    /**
     * @param query
     * @param root
     * @param orders
     * @param criteriaBuilder
     * @param joinType
     * @return
     */
    protected final CriteriaQuery<E> orderBy(CriteriaQuery<E> query, Root<E> root, O orders,
            CriteriaBuilder criteriaBuilder, JoinType joinType) {
        List<Order> orderList = new ArrayList<Order>();
        for (Entry<OE, OrderWayEnum> entry : orders.entrySet()) {
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(getOrderByPath(root, entry.getKey(), joinType)));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(getOrderByPath(root, entry.getKey(), joinType)));
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

}

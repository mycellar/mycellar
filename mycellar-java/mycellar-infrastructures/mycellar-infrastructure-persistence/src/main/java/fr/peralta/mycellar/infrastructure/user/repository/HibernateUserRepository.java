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
package fr.peralta.mycellar.infrastructure.user.repository;

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.domain.user.repository.UserRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.HibernateRepository;

/**
 * @author speralta
 */
@Repository
public class HibernateUserRepository extends HibernateRepository implements UserRepository {

    private static Logger logger = LoggerFactory.getLogger(HibernateUserRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countUsers(SearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query = query.select(criteriaBuilder.count(root));
        List<Predicate> predicates = new ArrayList<Predicate>();

        query = where(query, criteriaBuilder, predicates);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers(SearchForm searchForm, UserOrder orders, int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query = query.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        query = where(query, criteriaBuilder, predicates);

        List<Order> orderList = new ArrayList<Order>();
        for (Entry<UserOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            Expression<?> path;
            switch (entry.getKey()) {
            case EMAIL:
                path = root.get("email");
                break;
            case FIRSTNAME:
                path = root.get("firstname");
                break;
            case LASTNAME:
                path = root.get("lastname");
                break;
            default:
                throw new IllegalStateException("Unknwon " + UserOrderEnum.class.getSimpleName()
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
    public User find(String login, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        try {
            return entityManager.createQuery(
                    query.select(root).where(
                            criteriaBuilder.and(criteriaBuilder.equal(root.get("email"), login),
                                    criteriaBuilder.equal(root.get("password"), password))))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUser(User user) {
        User result = entityManager.merge(user);
        logger.debug("User merged {}", result);
    }

}

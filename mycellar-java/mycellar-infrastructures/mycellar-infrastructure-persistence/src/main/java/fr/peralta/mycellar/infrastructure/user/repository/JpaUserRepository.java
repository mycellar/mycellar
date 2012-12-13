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

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.domain.user.repository.UserRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaUserRepository extends JpaEntityRepository<User, UserOrderEnum, UserOrder>
        implements UserRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public final long countNewUsers() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        return getEntityManager().createQuery(
                query.select(criteriaBuilder.count(root)).where(
                        criteriaBuilder.isNull(root.get("profile")))).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<User> getAllNewUsers(UserOrder orders, long first, long count) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query = query.select(root).where(criteriaBuilder.isNull(root.get("profile")));
        return getEntityManager()
                .createQuery(orderBy(query, root, orders, criteriaBuilder, JoinType.INNER))
                .setFirstResult((int) first).setMaxResults((int) count).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllLike(String term) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        return getEntityManager().createQuery(
                query.select(root).where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.<String> get("firstname")), "%"
                                                + term.toLowerCase() + "%"),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.<String> get("lastname")), "%"
                                                + term.toLowerCase() + "%"),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.<String> get("email")), "%"
                                                + term.toLowerCase() + "%")))).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByEmail(String email) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("email"), email)))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<User> root, UserOrderEnum order, JoinType joinType) {
        switch (order) {
        case EMAIL:
            return root.get("email");
        case FIRSTNAME:
            return root.get("firstname");
        case LASTNAME:
            return root.get("lastname");
        default:
            throw new IllegalStateException("Unknown " + UserOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

}

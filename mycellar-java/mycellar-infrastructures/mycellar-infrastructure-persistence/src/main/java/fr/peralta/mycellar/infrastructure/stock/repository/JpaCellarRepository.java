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

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.CellarOrder;
import fr.peralta.mycellar.domain.stock.repository.CellarOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.CellarRepository;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.infrastructure.shared.repository.JoinHelper;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntitySearchFormRepository;

/**
 * @author speralta
 */
@Repository
public class JpaCellarRepository extends
        JpaEntitySearchFormRepository<Cellar, CellarOrderEnum, CellarOrder> implements
        CellarRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Cellar find(User owner, String name) {
        if (owner.getId() == null) {
            return null;
        }

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cellar> query = criteriaBuilder.createQuery(Cellar.class);
        Root<Cellar> root = query.from(Cellar.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("owner"), owner),
                            criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Cellar> root, CellarOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case OWNER_EMAIL:
            return getOwnerJoin(root, joinType).get("email");
        case OWNER_LASTNAME:
            return getOwnerJoin(root, joinType).get("lastname");
        case OWNER_FIRSTNAME:
            return getOwnerJoin(root, joinType).get("firstname");
        case NAME:
            return root.get("name");
        default:
            throw new IllegalStateException("Unknown " + CellarOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<Cellar> root, CriteriaBuilder criteriaBuilder,
            JoinType joinType) {
        return new Order[] { criteriaBuilder.desc(root.get("name")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(getAppellationJoin(root, joinType), "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(
                JoinHelper.getJoin(getStockJoin(root, joinType), "bottle", joinType), "format",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(
                JoinHelper.getJoin(getStockJoin(root, joinType), "bottle", joinType), "wine",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<Cellar> root, JoinType joinType) {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "appellation", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoinSet(root, "shares", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<Cellar> root, JoinType joinType) {
        return JoinHelper.getJoinSet(root, "stocks", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Cellar> getEntityClass() {
        return Cellar.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return FilterEnum.CELLAR;
    }

}

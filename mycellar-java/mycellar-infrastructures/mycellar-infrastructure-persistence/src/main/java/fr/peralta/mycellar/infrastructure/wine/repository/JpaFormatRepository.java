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
package fr.peralta.mycellar.infrastructure.wine.repository;

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
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.repository.FormatOrder;
import fr.peralta.mycellar.domain.wine.repository.FormatOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.FormatRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JoinHelper;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntitySearchFormRepository;

/**
 * @author speralta
 */
@Repository
public class JpaFormatRepository extends
        JpaEntitySearchFormRepository<Format, FormatOrderEnum, FormatOrder> implements
        FormatRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Format find(String name, float capacity) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Format> query = criteriaBuilder.createQuery(Format.class);
        Root<Format> root = query.from(Format.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("capacity"), capacity),
                            criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Format> root, FormatOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case CAPACITY:
            return root.get("capacity");
        case NAME:
            return root.get("name");
        default:
            throw new IllegalStateException("Unknown " + FormatOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<Format> root, CriteriaBuilder criteriaBuilder,
            JoinType joinType) {
        return new Order[] { criteriaBuilder.asc(root.get("capacity")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getCellarJoin(root, joinType), "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getAppellationJoin(root, joinType), "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<Format> root, JoinType joinType) {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(JoinHelper.getJoinSet(root, "bottles", joinType), "wine",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getStockJoin(root, joinType), "cellar", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "appellation", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoinSet(getCellarJoin(root, joinType), "shares", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<Format> root, JoinType joinType) {
        return JoinHelper.getJoinSet(JoinHelper.getJoinSet(root, "bottles", joinType), "stocks",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Format> getEntityClass() {
        return Format.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return FilterEnum.FORMAT;
    }

}

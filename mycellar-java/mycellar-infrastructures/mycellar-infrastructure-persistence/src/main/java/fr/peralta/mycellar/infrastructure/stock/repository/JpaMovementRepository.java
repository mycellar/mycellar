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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.stock.repository.MovementOrder;
import fr.peralta.mycellar.domain.stock.repository.MovementOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.MovementRepository;
import fr.peralta.mycellar.domain.stock.repository.StockOrderEnum;
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
public class JpaMovementRepository extends
        JpaEntitySearchFormRepository<Movement, MovementOrderEnum, MovementOrder> implements
        MovementRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Movement> root, MovementOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case APPELLATION_NAME:
            return getAppellationJoin(root, joinType).get("name");
        case CELLAR_NAME:
            return getCellarJoin(root, joinType).get("name");
        case COLOR:
            return getWineJoin(root, joinType).get("color");
        case COUNTRY_NAME:
            return getCountryJoin(root, joinType).get("name");
        case DATE:
            return root.get("date");
        case FORMAT_NAME:
            return getFormatJoin(root, joinType).get("name");
        case NUMBER:
            return root.get("number");
        case PRODUCER_NAME:
            return getProducerJoin(root, joinType).get("name");
        case TYPE:
            return getWineJoin(root, joinType).get("type");
        case REGION_NAME:
            return getRegionJoin(root, joinType).get("name");
        case WINE_NAME:
            return getWineJoin(root, joinType).get("name");
        case WINE_VINTAGE:
            return getWineJoin(root, joinType).get("vintage");
        default:
            throw new IllegalStateException("Unknown " + StockOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<Movement> root,
            CriteriaBuilder criteriaBuilder, JoinType joinType) {
        return new Order[] { criteriaBuilder.desc(root.get("date")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(getCellarJoin(root, joinType), "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(getAppellationJoin(root, joinType), "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(JoinHelper.getJoin(root, "bottle", joinType), "format", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(JoinHelper.getJoin(root, "bottle", joinType), "wine", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "cellar", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "appellation", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoinSet(getCellarJoin(root, joinType), "shares", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<Movement> root, JoinType joinType) {
        return JoinHelper.getJoinSet(JoinHelper.getJoin(root, "bottle", joinType), "stocks",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Movement> getEntityClass() {
        return Movement.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return null;
    }
}

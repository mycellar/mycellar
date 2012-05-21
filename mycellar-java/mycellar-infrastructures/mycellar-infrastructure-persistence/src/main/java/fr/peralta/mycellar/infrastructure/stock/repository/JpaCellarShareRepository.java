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
import fr.peralta.mycellar.domain.stock.repository.CellarOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrder;
import fr.peralta.mycellar.domain.stock.repository.CellarShareOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.CellarShareRepository;
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
public class JpaCellarShareRepository extends
        JpaEntitySearchFormRepository<CellarShare, CellarShareOrderEnum, CellarShareOrder>
        implements CellarShareRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellarAlreaySharedWith(Cellar cellar, String email) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<CellarShare> root = query.from(CellarShare.class);
        return getEntityManager()
                .createQuery(
                        query.select(root.<Integer> get("id")).where(
                                criteriaBuilder.equal(root.get("email"), email),
                                criteriaBuilder.equal(root.get("cellar"), cellar))).getResultList()
                .size() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<CellarShare> root, CellarShareOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case ACCESS_RIGHT:
            return root.get("accessRight");
        case EMAIL:
            return root.get("email");
        case CELLAR_NAME:
            return getCellarJoin(root, joinType).get("name");
        case OWNER_EMAIL:
            return getOwnerJoin(root, joinType).get("email");
        default:
            throw new IllegalStateException("Unknown " + CellarOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<CellarShare> root,
            CriteriaBuilder criteriaBuilder, JoinType joinType) {
        return new Order[] { criteriaBuilder.asc(root.get("name")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(getCellarJoin(root, joinType), "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(getAppellationJoin(root, joinType), "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(
                JoinHelper.getJoin(getStockJoin(root, joinType), "bottle", joinType), "format",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(
                JoinHelper.getJoin(getStockJoin(root, joinType), "bottle", joinType), "wine",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "cellar", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "appellation", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<CellarShare> root, JoinType joinType) {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<CellarShare> root, JoinType joinType) {
        return JoinHelper.getJoinSet(getCellarJoin(root, joinType), "stocks", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<CellarShare> getEntityClass() {
        return CellarShare.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return null;
    }

}

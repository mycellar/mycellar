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

import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
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
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.WineRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JoinHelper;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntitySearchFormRepository;

/**
 * @author speralta
 */
@Repository
public class JpaWineRepository extends
        JpaEntitySearchFormRepository<Wine, WineOrderEnum, WineOrder> implements WineRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Wine find(Producer producer, Appellation appellation, WineTypeEnum type,
            WineColorEnum color, String name, Integer vintage) {
        if ((appellation.getId() == null) || (producer.getId() == null)) {
            return null;
        }
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Wine> query = criteriaBuilder.createQuery(Wine.class);
        Root<Wine> root = query.from(Wine.class);
        try {
            return getEntityManager().createQuery(
                    query.select(root).where(
                            criteriaBuilder.equal(root.get("appellation"), appellation),
                            criteriaBuilder.equal(root.get("producer"), producer),
                            criteriaBuilder.equal(root.get("vintage"), vintage),
                            criteriaBuilder.equal(root.get("name"), name),
                            criteriaBuilder.equal(root.get("color"), color),
                            criteriaBuilder.equal(root.get("type"), type))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineTypeEnum, Long> getTypes(SearchForm searchForm, CountEnum countEnum) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineTypeEnum> type = root.get("type");
        return getAllWithCount(query, root, type, null, searchForm, countEnum, criteriaBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Long> getColors(SearchForm searchForm, CountEnum countEnum) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineColorEnum> color = root.get("color");
        return getAllWithCount(query, root, color, null, searchForm, countEnum, criteriaBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Wine> root, WineOrderEnum order, JoinType joinType) {
        switch (order) {
        case APPELLATION_NAME:
            return getAppellationJoin(root, joinType).get("name");
        case COUNTRY_NAME:
            return getCountryJoin(root, joinType).get("name");
        case NAME:
            return root.get("name");
        case REGION_NAME:
            return getRegionJoin(root, joinType).get("name");
        case VINTAGE:
            return root.get("vintage");
        case COLOR:
            return root.get("color");
        case PRODUCER_NAME:
            return getProducerJoin(root, joinType).get("name");
        case TYPE:
            return root.get("type");
        default:
            throw new IllegalStateException("Unknown " + WineOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<Wine> root, CriteriaBuilder criteriaBuilder,
            JoinType joinType) {
        return new Order[] { criteriaBuilder.desc(root.get("vintage")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(getCellarJoin(root, joinType), "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(getAppellationJoin(root, joinType), "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(JoinHelper.getJoinSet(root, "bottles", joinType), "format",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<Wine> root, JoinType joinType) {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(getStockJoin(root, joinType), "cellar", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "appellation", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoinSet(getCellarJoin(root, joinType), "shares", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<Wine> root, JoinType joinType) {
        return JoinHelper.getJoinSet(JoinHelper.getJoinSet(root, "bottles", joinType), "stocks",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Wine> getEntityClass() {
        return Wine.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return FilterEnum.WINE;
    }

}

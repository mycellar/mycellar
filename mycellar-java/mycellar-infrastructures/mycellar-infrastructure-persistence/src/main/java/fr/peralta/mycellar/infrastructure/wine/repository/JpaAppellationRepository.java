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

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

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
import fr.peralta.mycellar.domain.wine.repository.AppellationOrder;
import fr.peralta.mycellar.domain.wine.repository.AppellationOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JoinHelper;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntitySearchFormRepository;

/**
 * @author speralta
 */
@Repository
public class JpaAppellationRepository extends
        JpaEntitySearchFormRepository<Appellation, AppellationOrderEnum, AppellationOrder>
        implements AppellationRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Appellation find(Region region, String name) {
        if (region.getId() == null) {
            return null;
        }

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appellation> query = criteriaBuilder.createQuery(Appellation.class);
        Root<Appellation> root = query.from(Appellation.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("region"), region),
                            criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Appellation> getAllLike(String term, SearchForm searchForm, FilterEnum... filters) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Appellation> query = criteriaBuilder.createQuery(Appellation.class);
        Root<Appellation> root = query.from(Appellation.class);
        List<Predicate> predicates = wherePredicates(root, searchForm, criteriaBuilder,
                JoinType.LEFT, filters);
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("name")), "%"
                + term.toLowerCase() + "%"));
        where(query, criteriaBuilder, predicates);
        return getEntityManager().createQuery(query.select(root)).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Appellation> root, AppellationOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case COUNTRY_NAME:
            return getCountryJoin(root, joinType).get("name");
        case NAME:
            return root.get("name");
        case REGION_NAME:
            return getRegionJoin(root, joinType).get("name");
        default:
            throw new IllegalStateException("Unknown " + AppellationOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Order[] getOrderByForAllWithCount(Root<Appellation> root,
            CriteriaBuilder criteriaBuilder, JoinType joinType) {
        return new Order[] { criteriaBuilder.asc(root.get("name")) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, User> getOwnerJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(getCellarJoin(root, joinType), "owner", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Region> getRegionJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(root, "region", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Producer> getProducerJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(getWineJoin(root, joinType), "producer", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Format> getFormatJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(
                JoinHelper.getJoinSet(getWineJoin(root, joinType), "bottles", joinType), "format",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Country> getCountryJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(getRegionJoin(root, joinType), "country", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Wine> getWineJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoinSet(root, "wines", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Cellar> getCellarJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoin(getStockJoin(root, joinType), "cellar", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Appellation> getAppellationJoin(Root<Appellation> root, JoinType joinType) {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, CellarShare> getSharesJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoinSet(getCellarJoin(root, joinType), "shares", joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected From<?, Stock> getStockJoin(Root<Appellation> root, JoinType joinType) {
        return JoinHelper.getJoinSet(
                JoinHelper.getJoinSet(getWineJoin(root, joinType), "bottles", joinType), "stocks",
                joinType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Appellation> getEntityClass() {
        return Appellation.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToIgnore() {
        return FilterEnum.APPELLATION;
    }

}

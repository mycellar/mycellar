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

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.*;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.GenericRepository;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * JPA 2 Generic DAO with find by example/range/pattern and CRUD support.
 */
public abstract class JpaGenericRepository<E extends Identifiable<PK>, PK extends Serializable>
        implements GenericRepository<E, PK> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ByFullTextUtil byFullTextUtil;
    private ByPropertySelectorUtil byPropertySelectorUtil;
    private ByRangeUtil byRangeUtil;
    private NamedQueryUtil namedQueryUtil;
    private OrderByUtil orderByUtil;

    private EntityManager entityManager;

    private final Class<E> type;
    private final List<SingularAttribute<?, ?>> indexedAttributes;
    private final String cacheRegion;

    /**
     * This constructor needs the real type of the generic type E so it can be
     * passed to the {@link EntityManager}.
     */
    public JpaGenericRepository(Class<E> type, SingularAttribute<?, ?>... indexedAttributes) {
        this.type = type;
        this.cacheRegion = type.getCanonicalName();
        this.indexedAttributes = newArrayList(indexedAttributes);
    }

    public final Class<E> getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<E> find(SearchParameters sp) {
        if (sp.hasNamedQuery()) {
            return getNamedQueryUtil().findByNamedQuery(sp);
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = builder.createQuery(type);
        if (sp.getDistinct()) {
            criteriaQuery.distinct(true);
        }
        Root<E> root = criteriaQuery.from(type);

        // predicate
        Predicate predicate = getPredicate(root, criteriaQuery, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // left join
        if (sp.hasLeftJoins()) {
            for (SingularAttribute<?, ?> arg : sp.getLeftJoins()) {
                root.fetch((SingularAttribute<E, ?>) arg, JoinType.LEFT);
            }
        }

        // order by
        criteriaQuery.orderBy(orderByUtil.buildJpaOrders(sp.getOrders(), root, builder, sp));

        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);

        // cache
        setCacheHints(typedQuery, sp);

        // pagination
        if (sp.getFirstResult() >= 0) {
            typedQuery.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            typedQuery.setMaxResults(sp.getMaxResults());
        }

        // execution
        List<E> entities = typedQuery.getResultList();
        logger.trace("Returned {} elements.", entities.size());

        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long findCount(SearchParameters sp) {
        checkNotNull(sp, "The searchParameters cannot be null");

        if (sp.hasNamedQuery()) {
            return getNamedQueryUtil().numberByNamedQuery(sp).intValue();
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(type);

        if (sp.getDistinct()) {
            criteriaQuery = criteriaQuery.select(builder.countDistinct(root));
        } else {
            criteriaQuery = criteriaQuery.select(builder.count(root));
        }

        // predicate
        Predicate predicate = getPredicate(root, criteriaQuery, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        // cache
        setCacheHints(typedQuery, sp);

        // execution
        Long count = typedQuery.getSingleResult();

        if (count != null) {
            return count;
        } else {
            logger.warn("findCount returned null!");
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findUnique(SearchParameters sp) {
        E result = findUniqueOrNone(sp);

        if (result == null) {
            throw new NoResultException("Developper: You expected 1 result but we found none !");
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findUniqueOrNone(SearchParameters sp) {
        // this code is an optimization to prevent using a count
        sp.setFirstResult(0);
        sp.setMaxResults(2);
        List<E> results = find(sp);

        if ((results == null) || results.isEmpty()) {
            return null;
        }

        if (results.size() > 1) {
            throw new NonUniqueResultException(
                    "Developper: You expected 1 result but we found more !");
        }

        return results.iterator().next();
    }

    protected <R> Predicate getPredicate(Root<E> root, CriteriaQuery<R> query,
            CriteriaBuilder builder, SearchParameters sp) {
        return JpaUtil.andPredicate(builder, //
                byFullTextUtil.byFullText(root, query, builder, sp, type, indexedAttributes), //
                byRangeUtil.byRanges(root, query, builder, sp.getRanges(), type), //
                byPropertySelectorUtil.byPropertySelectors(root, builder, sp, sp.getProperties()), //
                byExtraPredicate(root, query, builder, sp));
    }

    /**
     * You may override this method to add a Predicate to the default find
     * method.
     */
    protected <R> Predicate byExtraPredicate(Root<E> root, CriteriaQuery<R> query,
            CriteriaBuilder builder, SearchParameters sp) {
        return null;
    }

    // -----------------
    // Commons
    // -----------------

    /**
     * Set hints for 2d level cache.
     */
    protected void setCacheHints(TypedQuery<?> typedQuery, SearchParameters sp) {
        if (sp.isCacheable()) {
            typedQuery.setHint("org.hibernate.cacheable", true);

            if (sp.hasCacheRegion()) {
                typedQuery.setHint("org.hibernate.cacheRegion", sp.getCacheRegion());
            } else {
                typedQuery.setHint("org.hibernate.cacheRegion", cacheRegion);
            }
        }
    }

    // BEANS

    /**
     * @return the byFullTextUtil
     */
    protected final ByFullTextUtil getByFullTextUtil() {
        return byFullTextUtil;
    }

    /**
     * @param byFullTextUtil
     *            the byFullTextUtil to set
     */
    @Inject
    public final void setByFullTextUtil(ByFullTextUtil byFullTextUtil) {
        this.byFullTextUtil = byFullTextUtil;
    }

    /**
     * @return the byPropertySelectorUtil
     */
    protected final ByPropertySelectorUtil getByPropertySelectorUtil() {
        return byPropertySelectorUtil;
    }

    /**
     * @param byPropertySelectorUtil
     *            the byPropertySelectorUtil to set
     */
    @Inject
    public final void setByPropertySelectorUtil(ByPropertySelectorUtil byPropertySelectorUtil) {
        this.byPropertySelectorUtil = byPropertySelectorUtil;
    }

    /**
     * @return the byRangeUtil
     */
    protected final ByRangeUtil getByRangeUtil() {
        return byRangeUtil;
    }

    /**
     * @param byRangeUtil
     *            the byRangeUtil to set
     */
    @Inject
    public final void setByRangeUtil(ByRangeUtil byRangeUtil) {
        this.byRangeUtil = byRangeUtil;
    }

    /**
     * @return the namedQueryUtil
     */
    protected final NamedQueryUtil getNamedQueryUtil() {
        return namedQueryUtil;
    }

    /**
     * @param namedQueryUtil
     *            the namedQueryUtil to set
     */
    @Inject
    public final void setNamedQueryUtil(NamedQueryUtil namedQueryUtil) {
        this.namedQueryUtil = namedQueryUtil;
    }

    /**
     * @return the entityManager
     */
    protected final EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    @PersistenceContext
    public final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @return the orderByUtil
     */
    protected final OrderByUtil getOrderByUtil() {
        return orderByUtil;
    }

    /**
     * @param orderByUtil
     *            the orderByUtil to set
     */
    @Inject
    public final void setOrderByUtil(OrderByUtil orderByUtil) {
        this.orderByUtil = orderByUtil;
    }

}
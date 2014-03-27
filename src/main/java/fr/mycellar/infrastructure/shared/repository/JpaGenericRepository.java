/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.shared.repository.query.SearchParametersValues;
import fr.mycellar.infrastructure.shared.repository.util.ByFullTextUtil;
import fr.mycellar.infrastructure.shared.repository.util.ByPropertySelectorUtil;
import fr.mycellar.infrastructure.shared.repository.util.ByRangeUtil;
import fr.mycellar.infrastructure.shared.repository.util.JpaUtil;
import fr.mycellar.infrastructure.shared.repository.util.MetamodelUtil;
import fr.mycellar.infrastructure.shared.repository.util.OrderByUtil;

/**
 * JPA 2 Generic DAO with find by example/range/pattern and CRUD support.
 */
public abstract class JpaGenericRepository<E extends Identifiable<PK>, PK extends Serializable> implements GenericRepository<E, PK> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ByFullTextUtil byFullTextUtil;
    private ByPropertySelectorUtil byPropertySelectorUtil;
    private ByRangeUtil byRangeUtil;
    private OrderByUtil orderByUtil;
    private JpaUtil jpaUtil;
    private MetamodelUtil metamodelUtil;

    private EntityManager entityManager;

    private final Class<E> type;
    protected String cacheRegion;

    /**
     * This constructor needs the real type of the generic type E so it can be
     * passed to the {@link EntityManager}.
     */
    public JpaGenericRepository(Class<E> type) {
        this.type = type;
    }

    public final Class<E> getType() {
        return type;
    }

    /**
     * Find and load a list of E instance.
     * 
     * @param searchParameters
     *            carries additional search information
     * @return the entities matching the search.
     */
    @Override
    public List<E> find(SearchParameters<E> searchParameters) {
        checkNotNull(searchParameters, "The searchParameters cannot be null");
        SearchParametersValues<E> sp = searchParameters.build();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = builder.createQuery(type);
        if (sp.isUseDistinct()) {
            criteriaQuery.distinct(true);
        }
        Root<E> root = criteriaQuery.from(type);

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // fetches
        jpaUtil.fetches(sp, root);

        // order by
        criteriaQuery.orderBy(orderByUtil.buildJpaOrders(sp.getOrders(), root, builder));

        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);
        jpaUtil.applyPagination(typedQuery, sp);
        List<E> entities = typedQuery.getResultList();
        logger.trace("Returned {} elements for {}.", entities.size(), sp);

        return entities;
    }

    /**
     * Count the number of E instances.
     * 
     * @param searchParameters
     *            carries additional search information
     * @return the number of entities matching the search.
     */
    @Override
    public long findCount(SearchParameters<E> searchParameters) {
        checkNotNull(searchParameters, "The searchParameters cannot be null");
        SearchParametersValues<E> sp = searchParameters.build();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(type);

        if (sp.isUseDistinct()) {
            criteriaQuery = criteriaQuery.select(builder.countDistinct(root));
        } else {
            criteriaQuery = criteriaQuery.select(builder.count(root));
        }

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // construct order by to fetch or joins if needed
        orderByUtil.buildJpaOrders(sp.getOrders(), root, builder);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult().longValue();
    }

    /**
     * Find a list of E property.
     * 
     * @param propertyType
     *            type of the property
     * @param entity
     *            a sample entity whose non-null properties may be used as
     *            search hints
     * @param searchParameters
     *            carries additional search information
     * @param attributes
     *            the list of attributes to the property
     * @return the entities property matching the search.
     */
    @Override
    public <T> List<T> findProperty(Class<T> propertyType, SearchParameters<E> searchParameters, Attribute<?, ?>... attributes) {
        checkNotNull(searchParameters, "The searchParameters cannot be null");
        SearchParametersValues<E> sp = searchParameters.build();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(propertyType);
        if (sp.isUseDistinct()) {
            criteriaQuery.distinct(true);
        }
        Root<E> root = criteriaQuery.from(type);
        Path<T> path = jpaUtil.getPath(root, Arrays.asList(attributes));
        criteriaQuery.select(path);

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // fetches
        jpaUtil.fetches(sp, root);

        // order by
        // we do not want to follow order by specified in search parameters
        criteriaQuery.orderBy(builder.asc(path));

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        jpaUtil.applyPagination(typedQuery, sp);
        List<T> entities = typedQuery.getResultList();
        logger.debug("Returned {} elements for {}.", entities.size(), sp);

        return entities;
    }

    /**
     * Count the number of E instances.
     * 
     * @param entity
     *            a sample entity whose non-null properties may be used as
     *            search hint
     * @param searchParameters
     *            carries additional search information
     * @param attributes
     *            the list of attributes to the property
     * @return the number of entities matching the search.
     */
    @Override
    public long findPropertyCount(SearchParameters<E> searchParameters, Attribute<?, ?>... attributes) {
        checkNotNull(searchParameters, "The searchParameters cannot be null");
        SearchParametersValues<E> sp = searchParameters.build();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(type);
        Path<?> path = jpaUtil.getPath(root, Arrays.asList(attributes));

        if (sp.isUseDistinct()) {
            criteriaQuery = criteriaQuery.select(builder.countDistinct(path));
        } else {
            criteriaQuery = criteriaQuery.select(builder.count(path));
        }

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // construct order by to fetch or joins if needed
        orderByUtil.buildJpaOrders(sp.getOrders(), root, builder);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult().longValue();
    }

    @Override
    public E findUnique(SearchParameters<E> sp) {
        E result = findUniqueOrNone(sp);

        if (result == null) {
            throw new NoResultException("Developper: You expected 1 result but we found none !");
        }

        return result;
    }

    /**
     * We request at most 2, if there's more than one then we throw a
     * {@link NonUniqueResultException}
     * 
     * @param searchParameters
     * @return
     * @throws NonUniqueResultException
     */
    @Override
    public E findUniqueOrNone(SearchParameters<E> sp) {
        // this code is an optimization to prevent using a count
        List<E> results = find(sp.paginate(0, 2));

        if ((results == null) || results.isEmpty()) {
            return null;
        }

        if (results.size() > 1) {
            throw new NonUniqueResultException("Developper: You expected 1 result but we found more !");
        }

        return results.iterator().next();
    }

    protected <R> Predicate getPredicate(CriteriaQuery<?> criteriaQuery, Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp) {
        return jpaUtil.andPredicate(builder, //
                bySearchPredicate(root, builder, sp), //
                byMandatoryPredicate(criteriaQuery, root, builder, sp));
    }

    protected <R> Predicate bySearchPredicate(Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp) {
        return jpaUtil.andPredicate(builder, //
                byFullText(root, builder, sp, type), //
                byRanges(root, builder, sp, type), //
                byPropertySelectors(root, builder, sp));
    }

    protected Predicate byFullText(Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp, Class<E> type) {
        return byFullTextUtil.byFullText(root, builder, sp, type);
    }

    protected Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp) {
        return byPropertySelectorUtil.byPropertySelectors(root, builder, sp);
    }

    protected Predicate byRanges(Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp, Class<E> type) {
        return byRangeUtil.byRanges(root, builder, sp, type);
    }

    /**
     * You may override this method to add a Predicate to the default find
     * method.
     */
    protected <R> Predicate byMandatoryPredicate(CriteriaQuery<?> criteriaQuery, Root<E> root, CriteriaBuilder builder, SearchParametersValues<E> sp) {
        return null;
    }

    // BEANS METHODS

    protected final ByFullTextUtil getByFullTextUtil() {
        return byFullTextUtil;
    }

    @Inject
    public final void setByFullTextUtil(ByFullTextUtil byFullTextUtil) {
        this.byFullTextUtil = byFullTextUtil;
    }

    protected final ByPropertySelectorUtil getByPropertySelectorUtil() {
        return byPropertySelectorUtil;
    }

    @Inject
    public final void setByPropertySelectorUtil(ByPropertySelectorUtil byPropertySelectorUtil) {
        this.byPropertySelectorUtil = byPropertySelectorUtil;
    }

    protected final ByRangeUtil getByRangeUtil() {
        return byRangeUtil;
    }

    @Inject
    public final void setByRangeUtil(ByRangeUtil byRangeUtil) {
        this.byRangeUtil = byRangeUtil;
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected final OrderByUtil getOrderByUtil() {
        return orderByUtil;
    }

    @Inject
    public final void setOrderByUtil(OrderByUtil orderByUtil) {
        this.orderByUtil = orderByUtil;
    }

    protected final JpaUtil getJpaUtil() {
        return jpaUtil;
    }

    @Inject
    public final void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    protected final MetamodelUtil getMetamodelUtil() {
        return metamodelUtil;
    }

    @Inject
    public final void setMetamodelUtil(MetamodelUtil metamodelUtil) {
        this.metamodelUtil = metamodelUtil;
    }

}
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
package fr.mycellar.infrastructure.shared.repository;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.domain.shared.repository.SearchParameters;
import fr.mycellar.domain.shared.repository.TermSelector;

/**
 * Helper to create a predicate out of full search.
 */
@Named
@Singleton
public class ByFullTextUtil {
    private EntityManager entityManager;
    private HibernateSearchUtil hibernateSearchUtil;
    private JpaUtil jpaUtil;

    public <T extends Identifiable<?>> Predicate byFullText(Root<T> root, CriteriaBuilder builder, SearchParameters sp, Class<T> type) {
        if (!hasNonEmptyTerms(sp)) {
            return null;
        }

        if (jpaUtil.hasSimplePk(type)) {
            return onSimplePrimaryKey(root, builder, sp);
        } else {
            return onCompositePrimaryKeys(root, builder, sp);
        }
    }

    private boolean hasNonEmptyTerms(SearchParameters sp) {
        for (TermSelector termSelector : sp.getTerms()) {
            if (termSelector.isNotEmpty()) {
                return true;
            }
        }
        return false;
    }

    private <T extends Identifiable<?>> Predicate onCompositePrimaryKeys(Root<T> root, CriteriaBuilder builder, SearchParameters sp) {
        List<? extends T> found = hibernateSearchUtil.find(root.getJavaType(), sp);
        if (found == null) {
            return null;
        } else if (found.isEmpty()) {
            return builder.disjunction();
        }

        List<Predicate> predicates = new ArrayList<>();
        for (T t : found) {
            predicates.add(byExampleOnEntity(root, t, sp, builder));
        }
        return jpaUtil.concatPredicate(sp, builder, jpaUtil.orPredicate(builder, predicates));
    }

    private <T> Predicate onSimplePrimaryKey(Root<T> root, CriteriaBuilder builder, SearchParameters sp) {
        List<Serializable> ids = hibernateSearchUtil.findId(root.getJavaType(), sp);
        if (ids == null) {
            return null;
        } else if (ids.isEmpty()) {
            return builder.disjunction();
        }

        return jpaUtil.concatPredicate(sp, builder, root.get("id").in(ids));
    }

    public <T extends Identifiable<?>> Predicate byExampleOnEntity(Root<T> rootPath, T entityValue, SearchParameters sp, CriteriaBuilder builder) {
        if (entityValue == null) {
            return null;
        }

        Class<T> type = rootPath.getModel().getBindableJavaType();
        ManagedType<T> mt = entityManager.getMetamodel().entity(type);

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(byExample(mt, rootPath, entityValue, sp, builder));
        predicates.addAll(byExampleOnCompositePk(rootPath, entityValue, sp, builder));
        return jpaUtil.orPredicate(builder, predicates);
    }

    protected <T extends Identifiable<?>> List<Predicate> byExampleOnCompositePk(Root<T> root, T entity, SearchParameters sp, CriteriaBuilder builder) {
        String compositePropertyName = jpaUtil.compositePkPropertyName(entity);
        if (compositePropertyName == null) {
            return emptyList();
        } else {
            return newArrayList(byExampleOnEmbeddable(root.get(compositePropertyName), entity.getId(), sp, builder));
        }
    }

    public <E> Predicate byExampleOnEmbeddable(Path<E> embeddablePath, E embeddableValue, SearchParameters sp, CriteriaBuilder builder) {
        if (embeddableValue == null) {
            return null;
        }

        Class<E> type = embeddablePath.getModel().getBindableJavaType();
        ManagedType<E> mt = entityManager.getMetamodel().embeddable(type);
        // note: calling .managedType() does not work
        return jpaUtil.orPredicate(builder, byExample(mt, embeddablePath, embeddableValue, sp, builder));
    }

    /**
     * Add a predicate for each simple property whose value is not null.
     */
    public <T> List<Predicate> byExample(ManagedType<T> mt, Path<T> mtPath, T mtValue, SearchParameters sp, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SingularAttribute<? super T, ?> attr : mt.getSingularAttributes()) {
            if (!isPrimaryKey(mt, attr)) {
                continue;
            }

            Object attrValue = jpaUtil.getValue(mtValue, attr);
            if (attrValue != null) {
                predicates.add(builder.equal(mtPath.get(jpaUtil.attribute(mt, attr)), attrValue));
            }
        }
        return predicates;
    }

    private <T> boolean isPrimaryKey(ManagedType<T> mt, SingularAttribute<? super T, ?> attr) {
        if (mt.getJavaType().getAnnotation(Embeddable.class) != null) {
            return true;
        }
        return jpaUtil.isPk(mt, attr);
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param hibernateSearchUtil
     *            the hibernateSearchUtil to set
     */
    @Inject
    public void setHibernateSearchUtil(HibernateSearchUtil hibernateSearchUtil) {
        this.hibernateSearchUtil = hibernateSearchUtil;
    }

    /**
     * @param jpaUtil
     *            the jpaUtil to set
     */
    @Inject
    public void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

}
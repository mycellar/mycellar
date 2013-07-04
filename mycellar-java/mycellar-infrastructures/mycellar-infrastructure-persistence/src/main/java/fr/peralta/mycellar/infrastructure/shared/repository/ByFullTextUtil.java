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

import static com.google.common.collect.Lists.*;
import static java.util.Collections.*;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper to create a predicate out of full search.
 */
@Named
@Singleton
public class ByFullTextUtil {

    private EntityManager entityManager;

    private HibernateSearchUtil hibernateSearchUtil;

    public <T extends Identifiable<?>> Predicate byFullText(Root<T> root, CriteriaQuery<?> query,
            CriteriaBuilder builder, final SearchParameters sp, Class<T> type,
            List<SingularAttribute<?, ?>> indexedAttributes) {
        if (!sp.hasTerms()) {
            return null;
        }

        List<String> properties = JpaUtil.toNamesList(indexedAttributes);
        if (JpaUtil.hasSimplePk(type)) {
            return onSimplePrimaryKey(root, builder, sp, properties);
        } else {
            return onCompositePrimaryKeys(root, builder, sp, properties);
        }
    }

    private <T extends Identifiable<?>> Predicate onCompositePrimaryKeys(Root<T> root,
            CriteriaBuilder builder, final SearchParameters sp, List<String> properties) {
        List<? extends T> found = hibernateSearchUtil.find(root.getJavaType(), sp, properties);
        if (found.isEmpty()) {
            return builder.disjunction();
        }

        List<Predicate> predicates = newArrayList();
        for (T t : found) {
            predicates.add(byExampleOnEntity(root, t, sp, builder));
        }
        return JpaUtil.andPredicate(builder, JpaUtil.orPredicate(builder, predicates));
    }

    private <T> Predicate onSimplePrimaryKey(Root<T> root, CriteriaBuilder builder,
            final SearchParameters sp, List<String> properties) {
        List<Serializable> ids = hibernateSearchUtil.findId(root.getJavaType(), sp, properties);
        if (ids.isEmpty()) {
            return builder.disjunction();
        }

        return JpaUtil.andPredicate(builder, root.get("id").in(ids));
    }

    public <T extends Identifiable<?>> Predicate byExampleOnEntity(Root<T> rootPath,
            final T entityValue, SearchParameters sp, CriteriaBuilder builder) {
        if (entityValue == null) {
            return null;
        }

        Class<T> type = rootPath.getModel().getBindableJavaType();
        ManagedType<T> mt = entityManager.getMetamodel().entity(type);

        List<Predicate> predicates = newArrayList();
        predicates.addAll(byExample(mt, rootPath, entityValue, sp, builder));
        predicates.addAll(byExampleOnCompositePk(rootPath, entityValue, sp, builder));
        return JpaUtil.orPredicate(builder, predicates);
    }

    protected <T extends Identifiable<?>> List<Predicate> byExampleOnCompositePk(Root<T> root,
            T entity, SearchParameters sp, CriteriaBuilder builder) {
        String compositePropertyName = JpaUtil.compositePkPropertyName(entity);
        if (compositePropertyName == null) {
            return emptyList();
        } else {
            return newArrayList(byExampleOnEmbeddable(root.get(compositePropertyName),
                    entity.getId(), sp, builder));
        }
    }

    public <E> Predicate byExampleOnEmbeddable(Path<E> embeddablePath, final E embeddableValue,
            SearchParameters sp, CriteriaBuilder builder) {
        if (embeddableValue == null) {
            return null;
        }

        Class<E> type = embeddablePath.getModel().getBindableJavaType();
        ManagedType<E> mt = entityManager.getMetamodel().embeddable(type); // note:
                                                                           // calling
        // .managedType()
        // does not work
        return JpaUtil.orPredicate(builder,
                byExample(mt, embeddablePath, embeddableValue, sp, builder));
    }

    /**
     * Add a predicate for each simple property whose value is not null.
     */
    public <T> List<Predicate> byExample(ManagedType<T> mt, Path<T> mtPath, final T mtValue,
            SearchParameters sp, CriteriaBuilder builder) {
        List<Predicate> predicates = newArrayList();
        for (SingularAttribute<? super T, ?> attr : mt.getSingularAttributes()) {
            if (!isPrimaryKey(mt, attr)) {
                continue;
            }

            Object attrValue = JpaUtil.getValue(mtValue, attr);
            if (attrValue != null) {
                predicates.add(builder.equal(mtPath.get(JpaUtil.attribute(mt, attr)), attrValue));
            }
        }
        return predicates;
    }

    private <T> boolean isPrimaryKey(ManagedType<T> mt, SingularAttribute<? super T, ?> attr) {
        if (mt.getJavaType().getAnnotation(Embeddable.class) != null) {
            return true;
        }
        return JpaUtil.isPk(mt, attr);
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
    @Autowired
    public void setHibernateSearchUtil(HibernateSearchUtil hibernateSearchUtil) {
        this.hibernateSearchUtil = hibernateSearchUtil;
    }

}
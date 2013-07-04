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
import static javax.persistence.metamodel.Attribute.PersistentAttributeType.*;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper to create a predicate out of pattern.
 */
@Named
@Singleton
public class ByPatternUtil {

    private EntityManager entityManager;

    /**
     * Lookup entities having at least one String attribute matching the passed
     * sp's pattern
     */
    @SuppressWarnings("unchecked")
    public <T> Predicate byPattern(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
            final SearchParameters sp, final Class<T> type) {
        if (!sp.hasSearchPattern()) {
            return null;
        }

        List<Predicate> predicates = newArrayList();
        EntityType<T> entity = entityManager.getMetamodel().entity(type);
        String pattern = sp.getSearchPattern();

        for (Attribute<T, ?> attr : entity.getDeclaredSingularAttributes()) {
            if ((attr.getPersistentAttributeType() == MANY_TO_ONE)
                    || (attr.getPersistentAttributeType() == ONE_TO_ONE)) {
                continue;
            }

            if (attr.getJavaType() == String.class) {
                predicates.add(JpaUtil.stringPredicate(
                        (Expression<String>) root.get(JpaUtil.attribute(entity, attr)), pattern,
                        sp, builder));
            }
        }

        return JpaUtil.orPredicate(builder, predicates);
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
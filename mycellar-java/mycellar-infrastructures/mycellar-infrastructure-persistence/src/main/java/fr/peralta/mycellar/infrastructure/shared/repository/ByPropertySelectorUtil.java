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

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper to create a predicate out of {@link PropertySelector}s.
 */
@Named
@Singleton
public class ByPropertySelectorUtil {
    @SuppressWarnings("unchecked")
    public <E> Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder,
            final SearchParameters sp, final List<PropertySelector<?, ?>> selectors) {
        List<Predicate> predicates = newArrayList();

        for (PropertySelector<?, ?> selector : selectors) {
            if (selector.isBoolean()) {
                byBooleanSelector(root, builder, predicates,
                        (PropertySelector<? super E, Boolean>) selector);
            } else {
                byObjectSelector(root, builder, predicates, sp,
                        (PropertySelector<? super E, ?>) selector);
            }
        }
        return JpaUtil.andPredicate(builder, predicates);
    }

    private <E> void byBooleanSelector(Root<E> root, CriteriaBuilder builder,
            List<Predicate> predicates, PropertySelector<? super E, Boolean> selector) {
        if (selector.isNotEmpty()) {
            List<Predicate> selectorPredicates = newArrayList();

            for (Boolean selection : selector.getSelected()) {
                Path<Boolean> path = getPath(root, selector);
                if (selection == null) {
                    selectorPredicates.add(builder.isNull(path));
                } else {
                    selectorPredicates
                            .add(selection ? builder.isTrue(path) : builder.isFalse(path));
                }
            }
            predicates.add(JpaUtil.orPredicate(builder, selectorPredicates));
        }
    }

    private <E> void byObjectSelector(Root<E> root, CriteriaBuilder builder,
            List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, ?> selector) {
        if (selector.isNotEmpty()) {
            List<Predicate> selectorPredicates = newArrayList();

            for (Object selection : selector.getSelected()) {
                if (selection instanceof String) {
                    Path<String> path = getPath(root, selector);
                    selectorPredicates.add(JpaUtil.stringPredicate(path, selection,
                            selector.getSearchMode(), sp, builder));
                } else {
                    Path<?> path = getPath(root, selector);
                    selectorPredicates.add(selection == null ? builder.isNull(path) : builder
                            .equal(path, selection));
                }
            }
            predicates.add(JpaUtil.orPredicate(builder, selectorPredicates));
        }
    }

    @SuppressWarnings("unchecked")
    private <E, F> Path<F> getPath(Root<E> root, PropertySelector<? super E, ?> selector) {
        Path<?> path = root;
        for (Attribute<?, ?> attribute : selector.getFields()) {
            path = path.get(attribute.getName());
        }
        return (Path<F>) path;
    }
}
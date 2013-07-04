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
import static java.lang.Boolean.*;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.EntitySelector;

/**
 * Helper to create a predicate out of {@link EntitySelector}s.
 */
@Named
@Singleton
public class ByEntitySelectorUtil {

    public <E> Predicate byEntitySelectors(Root<E> root, CriteriaBuilder builder,
            final List<EntitySelector<?, ? extends Identifiable<?>, ?>> selectors) {
        List<Predicate> predicates = newArrayList();

        for (EntitySelector<?, ? extends Identifiable<?>, ?> s : selectors) {
            @SuppressWarnings("unchecked")
            EntitySelector<? super E, ? extends Identifiable<?>, ?> selector = (EntitySelector<? super E, ? extends Identifiable<?>, ?>) s;

            if (selector.isNotEmpty()) {
                List<Predicate> selectorPredicates = newArrayList();

                for (Identifiable<?> selection : selector.getSelected()) {
                    selectorPredicates.add(builder.equal(getExpression(root, selector),
                            selection.getId()));
                }

                if (TRUE == selector.getIncludeNull()) {
                    selectorPredicates
                            .add(builder.or(builder.isNull(getExpression(root, selector))));
                }

                predicates.add(JpaUtil.orPredicate(builder, selectorPredicates));
            } else if (selector.isIncludeNullSet()) {
                if (selector.getIncludeNull()) {
                    predicates.add(builder.isNull(getExpression(root, selector)));
                } else {
                    predicates.add(builder.isNotNull(getExpression(root, selector)));
                }
            }
        }

        return JpaUtil.andPredicate(builder, predicates);
    }

    private <E> Expression<?> getExpression(Root<E> root,
            EntitySelector<? super E, ? extends Identifiable<?>, ?> selector) {
        if (selector.getField() != null) {
            return root.get(selector.getField()).get("id");
        } else {
            return root.get(selector.getCpkField()).get(selector.getCpkInnerField().getName());
        }
    }
}
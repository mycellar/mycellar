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

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper to create a predicate out of {@link PropertySelector}s.
 */
@Named
@Singleton
public class ByPropertySelectorUtil {

    private JpaUtil jpaUtil;

    private MetamodelUtil metamodelUtil;

    @SuppressWarnings("unchecked")
    public <E> Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder, SearchParameters sp) {
        List<Predicate> predicates = newArrayList();

        for (PropertySelector<?, ?> selector : sp.getProperties()) {
            if (metamodelUtil.isBoolean(selector.getPath())) {
                byBooleanSelector(root, builder, predicates, sp, (PropertySelector<? super E, Boolean>) selector);
            } else if (metamodelUtil.isString(selector.getPath())) {
                byStringSelector(root, builder, predicates, sp, (PropertySelector<? super E, String>) selector);
            } else {
                byObjectSelector(root, builder, predicates, sp, (PropertySelector<? super E, ?>) selector);
            }
        }
        return jpaUtil.concatPredicate(sp, builder, predicates);
    }

    private <E> void byBooleanSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, Boolean> selector) {
        if (selector.isNotEmpty()) {
            List<Predicate> selectorPredicates = newArrayList();

            for (Boolean selection : selector.getSelected()) {
                Path<Boolean> path = jpaUtil.getPath(root, selector.getPath());
                if (selection == null) {
                    selectorPredicates.add(builder.isNull(path));
                } else {
                    selectorPredicates.add(selection ? builder.isTrue(path) : builder.isFalse(path));
                }
            }
            if (selector.isOrMode()) {
                predicates.add(jpaUtil.orPredicate(builder, selectorPredicates));
            } else {
                predicates.add(jpaUtil.andPredicate(builder, selectorPredicates));
            }
        }
    }

    private <E> void byStringSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, String> selector) {
        if (selector.isNotEmpty()) {
            List<Predicate> selectorPredicates = newArrayList();

            for (String selection : selector.getSelected()) {
                Path<String> path = jpaUtil.getPath(root, selector.getPath());
                selectorPredicates.add(jpaUtil.stringPredicate(path, selection, selector.getSearchMode(), sp, builder));
            }
            if (selector.isOrMode()) {
                predicates.add(jpaUtil.orPredicate(builder, selectorPredicates));
            } else {
                predicates.add(jpaUtil.andPredicate(builder, selectorPredicates));
            }
        }
    }

    private <E> void byObjectSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, ?> selector) {
        if (selector.isNotEmpty()) {
            if (selector.isOrMode()) {
                byObjectOrModeSelector(root, builder, predicates, sp, selector);
            } else {
                byObjectAndModeSelector(root, builder, predicates, sp, selector);
            }
        } else if (selector.isNotIncludingNullSet()) {
            predicates.add(builder.isNotNull(jpaUtil.getPath(root, selector.getPath())));
        }
    }

    private <E> void byObjectOrModeSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, ?> selector) {
        List<Predicate> selectorPredicates = new ArrayList<>();
        Path<?> path = jpaUtil.getPath(root, selector.getPath());
        List<?> selected = selector.getSelected();
        if (selector.getSelected().contains(null)) {
            selected = newArrayList(selector.getSelected());
            selected.remove(null);
            selectorPredicates.add(builder.isNull(path));
        }
        if (selected.get(0) instanceof Identifiable) {
            List<Serializable> ids = new ArrayList<>();
            for (Object selection : selected) {
                ids.add(((Identifiable<?>) selection).getId());
            }
            selectorPredicates.add(path.get("id").in(ids));
        } else {
            selectorPredicates.add(path.in(selected));
        }
        predicates.add(jpaUtil.orPredicate(builder, selectorPredicates));
    }

    private <E> void byObjectAndModeSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, SearchParameters sp, PropertySelector<? super E, ?> selector) {
        List<Predicate> selectorPredicates = newArrayList();
        List<?> selected = selector.getSelected();
        if (selector.getSelected().contains(null)) {
            selected = newArrayList(selector.getSelected());
            selected.remove(null);
            selectorPredicates.add(builder.isNull(jpaUtil.getPath(root, selector.getPath())));
        }
        for (Object selection : selector.getSelected()) {
            Path<?> path = jpaUtil.getPath(root, selector.getPath());
            if (selection instanceof Identifiable) {
                selectorPredicates.add(builder.equal(path.get("id"), ((Identifiable<?>) selection).getId()));
            } else {
                selectorPredicates.add(builder.equal(path, selection));
            }
        }
        predicates.add(jpaUtil.andPredicate(builder, selectorPredicates));
    }

    /**
     * @param jpaUtil
     *            the jpaUtil to set
     */
    @Inject
    public void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    /**
     * @param metamodelUtil
     *            the metamodelUtil to set
     */
    @Inject
    public void setMetamodelUtil(MetamodelUtil metamodelUtil) {
        this.metamodelUtil = metamodelUtil;
    }

}
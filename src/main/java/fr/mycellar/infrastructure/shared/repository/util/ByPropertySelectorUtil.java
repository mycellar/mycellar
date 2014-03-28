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
package fr.mycellar.infrastructure.shared.repository.util;

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

import org.springframework.util.CollectionUtils;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelectors;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * Helper to create a predicate out of {@link PropertySelector}s.
 */
@Named
@Singleton
public class ByPropertySelectorUtil {

    private JpaUtil jpaUtil;

    private MetamodelUtil metamodelUtil;

    public <E> Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder, SearchParameters<E> sp) {
        return byPropertySelectors(root, builder, sp.getPropertySelectors());
    }

    @SuppressWarnings("unchecked")
    private <E> Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder, PropertySelectors<E> propertySelectors) {
        List<Predicate> predicates = new ArrayList<>();

        for (PropertySelector<? super E, ?> selector : propertySelectors.getProperties()) {
            if (metamodelUtil.isBoolean(selector.getAttributes())) {
                byBooleanSelector(root, builder, predicates, (PropertySelector<? super E, Boolean>) selector);
            } else if (metamodelUtil.isString(selector.getAttributes())) {
                byStringSelector(root, builder, predicates, (PropertySelector<? super E, String>) selector);
            } else {
                byObjectSelector(root, builder, predicates, selector);
            }
        }
        for (PropertySelectors<E> selectors : propertySelectors.getSelectors()) {
            predicates.add(byPropertySelectors(root, builder, selectors));
        }
        if (propertySelectors.isAndMode()) {
            return jpaUtil.andPredicate(builder, predicates);
        } else {
            return jpaUtil.orPredicate(builder, predicates);
        }
    }

    private <E> void byBooleanSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, PropertySelector<? super E, Boolean> selector) {
        if (selector.isNotEmpty()) {
            List<Predicate> selectorPredicates = new ArrayList<>();

            for (Boolean selection : selector.getSelected()) {
                Path<Boolean> path = jpaUtil.getPath(root, selector.getAttributes());
                if (selection == null) {
                    selectorPredicates.add(builder.isNull(path));
                } else {
                    selectorPredicates.add(selection ? builder.isTrue(path) : builder.isFalse(path));
                }
            }
            if (selector.isOrMode()) {
                Predicate predicate = jpaUtil.orPredicate(builder, selectorPredicates);
                if (selector.isNotMode()) {
                    predicates.add(builder.not(predicate));
                } else {
                    predicates.add(predicate);
                }
            } else {
                Predicate predicate = jpaUtil.andPredicate(builder, selectorPredicates);
                if (selector.isNotMode()) {
                    predicates.add(builder.not(predicate));
                } else {
                    predicates.add(predicate);
                }
            }
        }
    }

    private <E> void byStringSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, PropertySelector<? super E, String> selector) {
        if (selector.isNotEmpty()) {
            Path<String> path = null;
            List<Predicate> selectorPredicates = new ArrayList<>();
            List<String> selected = selector.getSelected();
            if (selector.getSelected().contains(null)) {
                path = jpaUtil.getPath(root, selector.getAttributes());
                selected = new ArrayList<>(selector.getSelected());
                selected.remove(null);
                selectorPredicates.add(builder.isNull(path));
            }
            if (selector.isOrMode()) {
                // re-use created pat if it exists in 'or' mode : only one join
                if (path == null) {
                    path = jpaUtil.getPath(root, selector.getAttributes());
                }
                for (String selection : selected) {
                    selectorPredicates.add(jpaUtil.stringPredicate(path, selection, selector.getSearchMode(), selector.isCaseSensitive(), builder));
                }
                Predicate predicate = jpaUtil.orPredicate(builder, selectorPredicates);
                if (selector.isNotMode()) {
                    predicates.add(builder.not(predicate));
                } else {
                    predicates.add(predicate);
                }
            } else {
                for (String selection : selected) {
                    // explicitly create new path for new join in 'and' mode
                    path = jpaUtil.getPath(root, selector.getAttributes());
                    selectorPredicates.add(jpaUtil.stringPredicate(path, selection, selector.getSearchMode(), selector.isCaseSensitive(), builder));
                }
                Predicate predicate = jpaUtil.andPredicate(builder, selectorPredicates);
                if (selector.isNotMode()) {
                    predicates.add(builder.not(predicate));
                } else {
                    predicates.add(predicate);
                }
            }
        }
    }

    private <E> void byObjectSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, PropertySelector<? super E, ?> selector) {
        if (selector.isNotEmpty()) {
            if (selector.isOrMode()) {
                byObjectOrModeSelector(root, builder, predicates, selector);
            } else {
                byObjectAndModeSelector(root, builder, predicates, selector);
            }
        } else if (selector.isNotIncludingNullSet()) {
            predicates.add(builder.isNotNull(jpaUtil.getPath(root, selector.getAttributes())));
        }
    }

    private <E> void byObjectOrModeSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, PropertySelector<? super E, ?> selector) {
        List<Predicate> selectorPredicates = new ArrayList<>();
        Path<?> path = jpaUtil.getPath(root, selector.getAttributes());
        List<?> selected = selector.getSelected();
        if (selector.getSelected().contains(null)) {
            selected = new ArrayList<>(selector.getSelected());
            selected.remove(null);
            selectorPredicates.add(builder.isNull(path));
        }
        if (!CollectionUtils.isEmpty(selected)) {
            if (selected.get(0) instanceof Identifiable) {
                List<Serializable> ids = new ArrayList<>();
                for (Object selection : selected) {
                    ids.add(((Identifiable<?>) selection).getId());
                }
                selectorPredicates.add(path.get("id").in(ids));
            } else {
                selectorPredicates.add(path.in(selected));
            }
        }
        Predicate predicate = jpaUtil.orPredicate(builder, selectorPredicates);
        if (selector.isNotMode()) {
            predicates.add(builder.not(predicate));
        } else {
            predicates.add(predicate);
        }
    }

    private <E> void byObjectAndModeSelector(Root<E> root, CriteriaBuilder builder, List<Predicate> predicates, PropertySelector<? super E, ?> selector) {
        List<Predicate> selectorPredicates = new ArrayList<>();
        List<?> selected = selector.getSelected();
        if (selector.getSelected().contains(null)) {
            selected = new ArrayList<>(selector.getSelected());
            selected.remove(null);
            selectorPredicates.add(builder.isNull(jpaUtil.getPath(root, selector.getAttributes())));
        }
        for (Object selection : selector.getSelected()) {
            Path<?> path = jpaUtil.getPath(root, selector.getAttributes());
            if (selection instanceof Identifiable) {
                selectorPredicates.add(builder.equal(path.get("id"), ((Identifiable<?>) selection).getId()));
            } else {
                selectorPredicates.add(builder.equal(path, selection));
            }
        }
        Predicate predicate = jpaUtil.andPredicate(builder, selectorPredicates);
        if (selector.isNotMode()) {
            predicates.add(builder.not(predicate));
        } else {
            predicates.add(predicate);
        }
    }

    @Inject
    public void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Inject
    public void setMetamodelUtil(MetamodelUtil metamodelUtil) {
        this.metamodelUtil = metamodelUtil;
    }

}
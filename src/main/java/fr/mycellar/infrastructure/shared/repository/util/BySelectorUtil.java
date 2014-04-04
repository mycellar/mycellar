/*
 * Copyright 2014, MyCellar
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.shared.repository.query.selector.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.selector.Range;
import fr.mycellar.infrastructure.shared.repository.query.selector.Selector;
import fr.mycellar.infrastructure.shared.repository.query.selector.Selectors;
import fr.mycellar.infrastructure.shared.repository.query.selector.TermSelector;

/**
 * @author speralta
 */
@Named
@Singleton
public class BySelectorUtil {

    private ByFullTextUtil byFullTextUtil;
    private ByRangeUtil byRangeUtil;
    private ByPropertySelectorUtil byPropertySelectorUtil;
    private JpaUtil jpaUtil;

    public <E> Predicate bySelectors(Root<E> root, CriteriaBuilder builder, SearchParameters<E> sp) {
        return bySelectors(root, builder, sp.getSelectors());
    }

    @SuppressWarnings("unchecked")
    private <E> Predicate bySelectors(Root<E> root, CriteriaBuilder builder, Selectors<E> selectors) {
        List<Predicate> predicates = new ArrayList<>();
        for (Selector<E, ?> selector : selectors.getSelectors()) {
            if (selector instanceof Selectors) {
                predicates.add(bySelectors(root, builder, (Selectors<E>) selector));
            } else if (selector instanceof PropertySelector) {
                predicates.add(byPropertySelectorUtil.byPropertySelectors(root, builder, (PropertySelector<? super E, ?>) selector));
            } else if (selector instanceof Range) {
                predicates.add(byRangeUtil.byRange(root, builder, (Range<E, ?>) selector));
            } else if (selector instanceof TermSelector) {
                predicates.add(byFullTextUtil.byFullText(root, builder, (TermSelector<E>) selector));
            }
        }
        if (selectors.isAndMode()) {
            return jpaUtil.andPredicate(builder, predicates);
        } else {
            return jpaUtil.orPredicate(builder, predicates);
        }
    }

    @Inject
    public void setByFullTextUtil(ByFullTextUtil byFullTextUtil) {
        this.byFullTextUtil = byFullTextUtil;
    }

    @Inject
    public void setByRangeUtil(ByRangeUtil byRangeUtil) {
        this.byRangeUtil = byRangeUtil;
    }

    @Inject
    public void setByPropertySelectorUtil(ByPropertySelectorUtil byPropertySelectorUtil) {
        this.byPropertySelectorUtil = byPropertySelectorUtil;
    }

    @Inject
    public void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

}

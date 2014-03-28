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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.shared.repository.query.TermSelector;

@Named
@Singleton
public class ByFullTextUtil {
    private HibernateSearchUtil hibernateSearchUtil;
    private JpaUtil jpaUtil;

    public <T extends Identifiable<?>> Predicate byFullText(Root<T> root, CriteriaBuilder builder, SearchParameters<T> sp, Class<T> type) {
        if (!hasNonEmptyTerms(sp)) {
            return null;
        }

        if (Identifiable.class.isAssignableFrom(type)) {
            return onIdentifiable(root, builder, sp);
        } else {
            return onOther(root, builder, sp);
        }
    }

    private boolean hasNonEmptyTerms(SearchParameters<?> sp) {
        for (TermSelector<?> termSelector : sp.getTerms()) {
            if (termSelector.isNotEmpty()) {
                return true;
            }
        }
        return false;
    }

    private <T extends Identifiable<?>> Predicate onOther(Root<T> root, CriteriaBuilder builder, SearchParameters<T> sp) {
        List<? extends T> found = hibernateSearchUtil.find(root.getJavaType(), sp);
        if (found == null) {
            return null;
        } else if (found.isEmpty()) {
            return builder.disjunction();
        }

        List<Predicate> predicates = new ArrayList<>();
        for (T t : found) {
            predicates.add(builder.equal(root, t));
        }
        return jpaUtil.andPredicate(builder, jpaUtil.orPredicate(builder, predicates));
    }

    private <T> Predicate onIdentifiable(Root<T> root, CriteriaBuilder builder, SearchParameters<T> sp) {
        List<Serializable> ids = hibernateSearchUtil.findId(root.getJavaType(), sp);
        if (ids == null) {
            return null;
        } else if (ids.isEmpty()) {
            return builder.disjunction();
        }

        return root.get("id").in(ids);
    }

    @Inject
    public void setHibernateSearchUtil(HibernateSearchUtil hibernateSearchUtil) {
        this.hibernateSearchUtil = hibernateSearchUtil;
    }

    @Inject
    public void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

}
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
public abstract class HibernateRepository {

    /**
     * @param query
     * @param criteriaBuilder
     * @param predicates
     * @return
     */
    protected <O> CriteriaQuery<O> where(CriteriaQuery<O> query, CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {
        Predicate wherePredicate;
        switch (predicates.size()) {
        case 0:
            wherePredicate = null;
            break;
        case 1:
            wherePredicate = predicates.get(0);
        default:
            wherePredicate = criteriaBuilder
                    .and(predicates.toArray(new Predicate[predicates.size()]));
            break;
        }

        if (wherePredicate != null) {
            query.where(wherePredicate);
        }
        return query;
    }

    protected void in(List<Predicate> predicates, Collection<?> collection, Path<?> path,
            CriteriaBuilder criteriaBuilder) {
        if ((collection == null) || (collection.size() == 0)) {
            return;
        }
        List<Object> list = new ArrayList<Object>();
        for (Object object : collection) {
            if ((object != null)
                    && (!(object instanceof IdentifiedEntity<?>) || (((IdentifiedEntity<?>) object)
                            .getId() != null))) {
                list.add(object);
            }
        }
        switch (list.size()) {
        case 0:
            return;
        case 1:
            predicates.add(criteriaBuilder.equal(path, list.get(0)));
            break;
        default:
            predicates.add(path.in(list));
            break;
        }
    }

}

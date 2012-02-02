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
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.AccessRightEnum;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.domain.stock.Stock;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public abstract class HibernateRepository {

    /**
     * @param query
     * @param path
     * @param shares
     * @param searchForm
     * @param criteriaBuilder
     * @return
     */
    protected <Q extends AbstractQuery<O>, O> Q where(Q query, From<?, Stock> path,
            Path<CellarShare> shares, SearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        return where(query, path, shares, searchForm, criteriaBuilder, null);
    }

    /**
     * @param query
     * @param path
     * @param shares
     * @param searchForm
     * @param criteriaBuilder
     * @param filterEnum
     * @return
     */
    protected <Q extends AbstractQuery<O>, O> Q where(Q query, From<?, Stock> path,
            Path<CellarShare> shares, SearchForm searchForm, CriteriaBuilder criteriaBuilder,
            FilterEnum filterEnum) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filterEnum != FilterEnum.APPELLATION) {
            in(predicates, searchForm.getSet(FilterEnum.APPELLATION), path.get("bottle")
                    .get("wine").get("appellation"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.CELLAR) {
            in(predicates, searchForm.getSet(FilterEnum.CELLAR), path.get("cellar"),
                    criteriaBuilder);
        }
        if (filterEnum != FilterEnum.COLOR) {
            in(predicates, searchForm.getSet(FilterEnum.COLOR),
                    path.get("bottle").get("wine").get("color"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.COUNTRY) {
            in(predicates, searchForm.getSet(FilterEnum.COUNTRY), path.get("bottle").get("wine")
                    .get("appellation").get("region").get("country"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.FORMAT) {
            in(predicates, searchForm.getSet(FilterEnum.FORMAT), path.get("bottle").get("format"),
                    criteriaBuilder);
        }
        if (filterEnum != FilterEnum.PRODUCER) {
            in(predicates, searchForm.getSet(FilterEnum.PRODUCER), path.get("bottle").get("wine")
                    .get("producer"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.REGION) {
            in(predicates, searchForm.getSet(FilterEnum.REGION), path.get("bottle").get("wine")
                    .get("appellation").get("region"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.TYPE) {
            in(predicates, searchForm.getSet(FilterEnum.TYPE),
                    path.get("bottle").get("wine").get("type"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.USER) {
            List<Predicate> userPredicates = new ArrayList<Predicate>();
            Set<User> users = searchForm.<User> getSet(FilterEnum.USER);
            in(userPredicates, users, path.get("cellar").get("owner"), criteriaBuilder);
            if (users != null) {
                for (User user : users) {
                    if (searchForm.isCellarModification()) {
                        userPredicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                                shares.get("email"), user.getEmail()), criteriaBuilder.equal(
                                shares.get("accessRight"), AccessRightEnum.MODIFY)));
                    } else {
                        userPredicates.add(criteriaBuilder.equal(shares.get("email"),
                                user.getEmail()));
                    }
                }
            }
            if (userPredicates.size() > 1) {
                predicates.add(criteriaBuilder.or(userPredicates
                        .toArray(new Predicate[userPredicates.size()])));
            } else if (userPredicates.size() == 1) {
                predicates.add(userPredicates.get(0));
            }
        }
        if (filterEnum != FilterEnum.VINTAGE) {
            in(predicates, searchForm.getSet(FilterEnum.VINTAGE), path.get("bottle").get("wine")
                    .get("vintage"), criteriaBuilder);
        }
        if (filterEnum != FilterEnum.WINE) {
            in(predicates, searchForm.getSet(FilterEnum.WINE), path.get("bottle").get("wine"),
                    criteriaBuilder);
        }
        return where(query, criteriaBuilder, predicates);
    }

    /**
     * @param countEnum
     * @param stock
     * @param criteriaBuilder
     * @return
     */
    protected Expression<Long> getCount(CountEnum countEnum, From<?, Stock> stock,
            CriteriaBuilder criteriaBuilder) {
        Expression<Long> count;
        switch (countEnum) {
        case WINE:
            count = criteriaBuilder.count(stock.get("bottle").get("wine"));
            break;
        case STOCK_QUANTITY:
            count = criteriaBuilder.sumAsLong(stock.<Integer> get("quantity"));
            break;
        default:
            throw new IllegalStateException("Unknown " + CountEnum.class.getSimpleName()
                    + " value [" + countEnum + "].");
        }
        return count;
    }

    /**
     * @param query
     * @param criteriaBuilder
     * @param predicates
     * @return
     */
    protected <Q extends AbstractQuery<O>, O> Q where(Q query, CriteriaBuilder criteriaBuilder,
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

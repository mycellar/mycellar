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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Stock;

/**
 * @author speralta
 */
public abstract class HibernateRepository {

    /**
     * @param query
     * @param root
     * @param searchForm
     * @param criteriaBuilder
     * @return
     */
    protected <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Path<Stock> root,
            SearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getSet(FilterEnum.APPELLATION), root.get("bottle").get("wine")
                .get("appellation"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.CELLAR), root.get("cellar"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.COLOR),
                root.get("bottle").get("wine").get("color"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.COUNTRY),
                root.get("bottle").get("wine").get("appellation").get("region").get("country"),
                criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.FORMAT), root.get("bottle").get("format"),
                criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.PRODUCER),
                root.get("bottle").get("wine").get("producer"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.REGION),
                root.get("bottle").get("wine").get("appellation").get("region"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.TYPE),
                root.get("bottle").get("wine").get("type"), criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.USER), root.get("cellar").get("owner"),
                criteriaBuilder);
        in(predicates, searchForm.getSet(FilterEnum.VINTAGE),
                root.get("bottle").get("wine").get("vintage"), criteriaBuilder);
        return where(query, criteriaBuilder, predicates);
    }

    /**
     * @param countEnum
     * @param stock
     * @param criteriaBuilder
     * @return
     */
    protected Expression<Long> getCount(CountEnum countEnum, Path<Stock> stock,
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

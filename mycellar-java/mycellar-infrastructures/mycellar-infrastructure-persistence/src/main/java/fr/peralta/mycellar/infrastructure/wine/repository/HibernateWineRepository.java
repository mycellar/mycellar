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
package fr.peralta.mycellar.infrastructure.wine.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationCountEnum;
import fr.peralta.mycellar.domain.wine.repository.AppellationSearchForm;
import fr.peralta.mycellar.domain.wine.repository.CountryCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionCountEnum;
import fr.peralta.mycellar.domain.wine.repository.RegionSearchForm;
import fr.peralta.mycellar.domain.wine.repository.WineOrder;
import fr.peralta.mycellar.domain.wine.repository.WineOrderEnum;
import fr.peralta.mycellar.domain.wine.repository.WineRepository;
import fr.peralta.mycellar.domain.wine.repository.WineSearchForm;
import fr.peralta.mycellar.infrastructure.shared.repository.HibernateRepository;

/**
 * @author speralta
 */
@Repository
public class HibernateWineRepository extends HibernateRepository implements WineRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public long countWines(WineSearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Wine> root = query.from(Wine.class);
        query = query.select(criteriaBuilder.count(root));
        query = where(query, root, searchForm, criteriaBuilder);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> getWines(WineSearchForm searchForm, WineOrder orders, int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wine> query = criteriaBuilder.createQuery(Wine.class);
        Root<Wine> root = query.from(Wine.class);
        query = query.select(root);
        query = where(query, root, searchForm, criteriaBuilder);
        return entityManager.createQuery(orderBy(query, root, orders, criteriaBuilder))
                .setFirstResult(first).setMaxResults(count).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Long> getCountries(CountryCountEnum countryCountEnum) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Country> root = query.from(Country.class);
        Expression<Long> count = criteriaBuilder.count(getPath(root, countryCountEnum));

        List<Tuple> tuples = entityManager.createQuery(
                query.multiselect(root, count).groupBy(root)
                        .orderBy(criteriaBuilder.asc(root.get("name")))).getResultList();
        Map<Country, Long> result = new LinkedHashMap<Country, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(root), tuple.get(count));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Region, Long> getRegions(RegionSearchForm searchForm, RegionCountEnum regionCountEnum) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Region> root = query.from(Region.class);
        Expression<Long> count = criteriaBuilder.count(getPath(root, regionCountEnum));

        query = query.multiselect(root, count);
        query = where(query, root, searchForm, criteriaBuilder);

        List<Tuple> tuples = entityManager.createQuery(
                query.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name")))).getResultList();
        Map<Region, Long> result = new LinkedHashMap<Region, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(root), tuple.get(count));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Appellation, Long> getAppellations(AppellationSearchForm searchForm,
            AppellationCountEnum appellationCountEnum) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Appellation> root = query.from(Appellation.class);
        Expression<Long> count = criteriaBuilder.count(getPath(root, appellationCountEnum));

        query = query.multiselect(root, count);
        query = where(query, root, searchForm, criteriaBuilder);

        List<Tuple> tuples = entityManager.createQuery(
                query.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name")))).getResultList();
        Map<Appellation, Long> result = new LinkedHashMap<Appellation, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(root), tuple.get(count));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producer> getAllProducersLike(String term) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> query = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = query.from(Producer.class);
        return entityManager.createQuery(
                query.select(root).where(
                        criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("name")), "%"
                                + term.toLowerCase() + "%"))).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineTypeEnum, Long> getAllTypesFromProducersWithCounts(Producer... producers) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineTypeEnum> type = root.get("type");
        Expression<Long> count = criteriaBuilder.count(type);

        query = query.multiselect(type, count);

        if ((producers != null) && (producers.length > 0)) {
            query = query.where(root.get("producer").in(Arrays.asList(producers)));
        }

        List<Tuple> tuples = entityManager.createQuery(query.groupBy(type)).getResultList();
        Map<WineTypeEnum, Long> result = new LinkedHashMap<WineTypeEnum, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(type), tuple.get(count));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Long> getAllColorsFromTypesAndProducersWithCounts(
            WineTypeEnum[] types, Producer... producers) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineColorEnum> color = root.get("color");
        Expression<Long> count = criteriaBuilder.count(color);

        query = query.multiselect(color, count);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if ((producers != null) && (producers.length > 0)) {
            predicates.add(root.get("producer").in(Arrays.asList(producers)));
        }
        if ((producers != null) && (producers.length > 0)) {
            predicates.add(root.get("type").in(Arrays.asList(types)));
        }

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
            query = query.where(wherePredicate);
        }

        List<Tuple> tuples = entityManager.createQuery(query.groupBy(color)).getResultList();
        Map<WineColorEnum, Long> result = new LinkedHashMap<WineColorEnum, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(color), tuple.get(count));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Format, Long> getAllFormatWithCounts() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Format> root = query.from(Format.class);
        Expression<Long> count = criteriaBuilder.count(root.joinSet("bottles", JoinType.LEFT));

        List<Tuple> tuples = entityManager.createQuery(
                query.multiselect(root, count).groupBy(root)
                        .orderBy(criteriaBuilder.asc(root.get("name")))).getResultList();
        Map<Format, Long> result = new LinkedHashMap<Format, Long>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(root), tuple.get(count));
        }
        return result;
    }

    private <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Root<Region> root,
            RegionSearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getCountries(), root.get("country"));

        return where(query, criteriaBuilder, predicates);
    }

    private <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Root<Appellation> root,
            AppellationSearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getRegions(), root.get("region"));
        // TODO manage country cases

        return where(query, criteriaBuilder, predicates);
    }

    private <O> CriteriaQuery<O> where(CriteriaQuery<O> query, Root<Wine> root,
            WineSearchForm searchForm, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        in(predicates, searchForm.getTypes(), root.get("type"));
        in(predicates, searchForm.getColors(), root.get("color"));
        in(predicates, searchForm.getAppellations(), root.get("appellation"));
        in(predicates, searchForm.getProducers(), root.get("producer"));
        in(predicates, searchForm.getVintages(), root.get("vintage"));
        // TODO manage region and country cases

        return where(query, criteriaBuilder, predicates);
    }

    private <O> CriteriaQuery<O> orderBy(CriteriaQuery<O> query, Root<Wine> root, WineOrder orders,
            CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<Order>();
        for (Entry<WineOrderEnum, OrderWayEnum> entry : orders.entrySet()) {
            switch (entry.getValue()) {
            case ASC:
                orderList.add(criteriaBuilder.asc(getPath(root, entry.getKey())));
                break;
            case DESC:
                orderList.add(criteriaBuilder.desc(getPath(root, entry.getKey())));
                break;
            default:
                throw new IllegalStateException("Unknown " + OrderWayEnum.class.getSimpleName()
                        + " value [" + entry.getValue() + "].");
            }
        }
        if (orderList.size() > 0) {
            return query.orderBy(orderList);
        }
        return query;
    }

    /**
     * @param key
     * @return
     */
    private Expression<?> getPath(Root<Wine> root, WineOrderEnum order) {
        switch (order) {
        case APPELLATION_NAME:
            return root.get("appellation").get("name");
        case COUNTRY_NAME:
            return root.get("appellation").get("region").get("country").get("name");
        case NAME:
            return root.get("name");
        case REGION_NAME:
            return root.get("appellation").get("name");
        case VINTAGE:
            return root.get("vintage");
        default:
            throw new IllegalStateException("Unknwon " + WineOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    private Expression<?> getPath(Root<Country> root, CountryCountEnum count) {
        switch (count) {
        case APPELLATION:
            return root.joinSet("regions", JoinType.LEFT).joinSet("appellations", JoinType.LEFT);
        case REGION:
            return root.joinSet("regions", JoinType.LEFT);
        case WINE:
            return root.joinSet("regions", JoinType.LEFT).joinSet("appellations", JoinType.LEFT)
                    .joinSet("wines", JoinType.LEFT);
        default:
            throw new IllegalStateException("Unknown " + CountryCountEnum.class.getSimpleName()
                    + " value [" + count + "].");
        }
    }

    private Expression<?> getPath(Root<Region> root, RegionCountEnum count) {
        switch (count) {
        case APPELLATION:
            return root.joinSet("appellations", JoinType.LEFT);
        case WINE:
            return root.joinSet("appellations", JoinType.LEFT).joinSet("wines", JoinType.LEFT);
        default:
            throw new IllegalStateException("Unknown " + CountryCountEnum.class.getSimpleName()
                    + " value [" + count + "].");
        }
    }

    private Expression<?> getPath(Root<Appellation> root, AppellationCountEnum count) {
        switch (count) {
        case WINE:
            return root.joinSet("wines", JoinType.LEFT);
        default:
            throw new IllegalStateException("Unknown " + AppellationCountEnum.class.getSimpleName()
                    + " value [" + count + "].");
        }
    }

}

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
package fr.peralta.mycellar.infrastructure.wine.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineRepository;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
@Repository
@Qualifier("hibernate")
public class HibernateWineRepository implements WineRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Long> getAllCountriesWithCounts() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Country> root = query.from(Country.class);
        Expression<Long> count = criteriaBuilder.count(root.joinSet("regions", JoinType.LEFT)
                .joinSet("appellations", JoinType.LEFT).joinSet("wines", JoinType.LEFT));

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
    public Map<Region, Long> getAllRegionsFromCountriesWithCounts(Country... countries) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Region> root = query.from(Region.class);
        Expression<Long> count = criteriaBuilder.count(root.joinSet("appellations", JoinType.LEFT)
                .joinSet("wines", JoinType.LEFT));

        query = query.multiselect(root, count);
        if ((countries != null) && (countries.length > 0)) {
            query = query.where(root.get("country").in(Arrays.asList(countries)));
        }

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
    public Map<Appellation, Long> getAllAppellationsFromRegionsWithCounts(Region... regions) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<Appellation> root = query.from(Appellation.class);
        Expression<Long> count = criteriaBuilder.count(root.joinSet("wines", JoinType.LEFT));

        query = query.multiselect(root, count);
        if ((regions != null) && (regions.length > 0)) {
            query = query.where(root.get("region").in(Arrays.asList(regions)));
        }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> getAllWinesFrom(Producer producer, Appellation appellation, Region region,
            Country country, WineTypeEnum type, WineColorEnum color, Integer vintage) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wine> query = criteriaBuilder.createQuery(Wine.class);

        Root<Wine> root = query.from(Wine.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (producer != null) {
            predicates.add(criteriaBuilder.equal(root.get("producer"), producer));
        }
        if (appellation != null) {
            predicates.add(criteriaBuilder.equal(root.get("appellation"), appellation));
        }
        if (type != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
        }
        if (color != null) {
            predicates.add(criteriaBuilder.equal(root.get("color"), color));
        }
        if (vintage != null) {
            predicates.add(criteriaBuilder.equal(root.get("vintage"), vintage));
        }
        // TODO manage region and country cases

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

        query = query.select(root);
        if (wherePredicate != null) {
            query = query.where(wherePredicate);
        }
        return entityManager
                .createQuery(
                        query.orderBy(criteriaBuilder.asc(root.get("name")),
                                criteriaBuilder.desc(root.get("vintage")))).setMaxResults(10)
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> getAllWinesFrom(List<WineTypeEnum> types, List<WineColorEnum> colors,
            List<Country> countries, List<Region> regions, List<Appellation> appellations,
            int first, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wine> query = criteriaBuilder.createQuery(Wine.class);

        Root<Wine> root = query.from(Wine.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (types.size() > 0) {
            predicates.add(root.get("type").in(types));
        }
        if (colors.size() > 0) {
            predicates.add(root.get("color").in(colors));
        }
        if (appellations.size() > 0) {
            predicates.add(root.get("appellation").in(appellations));
        }
        // TODO manage region and country cases

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

        List<Order> orders = new ArrayList<Order>();
        orders.add(criteriaBuilder.asc(root.get("appellation").get("region").get("country")
                .get("name")));
        orders.add(criteriaBuilder.asc(root.get("appellation").get("region").get("name")));
        orders.add(criteriaBuilder.asc(root.get("appellation").get("name")));

        query = query.select(root);
        if (wherePredicate != null) {
            query = query.where(wherePredicate);
        }
        return entityManager.createQuery(query.orderBy(orders)).setFirstResult(first)
                .setMaxResults(count).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAllWinesFrom(List<Country> countries, List<Region> regions,
            List<Appellation> appellations) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

        Root<Wine> root = query.from(Wine.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (appellations.size() > 0) {
            predicates.add(root.get("appellation").in(appellations));
        }
        // TODO manage region and country cases

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

        query = query.select(criteriaBuilder.count(root));

        if (wherePredicate != null) {
            query.where(wherePredicate);
        }
        return entityManager.createQuery(query).getSingleResult();
    }
}

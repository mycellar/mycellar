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
import javax.persistence.criteria.Path;
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

        CriteriaQuery<Tuple> select = query.multiselect(root, count);
        if ((countries != null) && (countries.length > 0)) {
            select = select.where(root.<Country> get("country").in(Arrays.asList(countries)));
        }

        List<Tuple> tuples = entityManager.createQuery(
                select.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name"))))
                .getResultList();
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

        CriteriaQuery<Tuple> select = query.multiselect(root, count);
        if ((regions != null) && (regions.length > 0)) {
            select = select.where(root.<Region> get("region").in(Arrays.asList(regions)));
        }

        List<Tuple> tuples = entityManager.createQuery(
                select.groupBy(root).orderBy(criteriaBuilder.asc(root.get("name"))))
                .getResultList();
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
    public Map<WineTypeEnum, Long> getAllTypeFromProducerWithCounts(Producer producer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineTypeEnum> type = root.get("type");
        Expression<Long> count = criteriaBuilder.count(type);

        List<Tuple> tuples = entityManager
                .createQuery(
                        query.multiselect(type, count)
                                .where(criteriaBuilder.equal(root.get("producer"), producer))
                                .groupBy(type)).getResultList();
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
    public Map<WineColorEnum, Long> getAllColorFromProducerAndTypeWithCounts(Producer producer,
            WineTypeEnum type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<Wine> root = query.from(Wine.class);
        Path<WineColorEnum> color = root.get("color");
        Expression<Long> count = criteriaBuilder.count(color);

        List<Tuple> tuples = entityManager.createQuery(
                query.multiselect(color, count)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("producer"), producer),
                                criteriaBuilder.equal(root.get("type"), type))).groupBy(color))
                .getResultList();
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

}

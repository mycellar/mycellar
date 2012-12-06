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
package fr.peralta.mycellar.infrastructure.admin.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrderEnum;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaConfigurationRepository extends
        JpaEntityRepository<Configuration, ConfigurationOrderEnum, ConfigurationOrder> implements
        ConfigurationRepository {

    private final Map<ConfigurationKeyEnum, Configuration> cache = new ConcurrentHashMap<ConfigurationKeyEnum, Configuration>();

    @PostConstruct
    public void initializeCache() {
        for (ConfigurationKeyEnum key : ConfigurationKeyEnum.values()) {
            find(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration find(ConfigurationKeyEnum key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Configuration> query = criteriaBuilder.createQuery(Configuration.class);
        Root<Configuration> root = query.from(Configuration.class);

        try {
            Configuration configuration = getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("key"), key)))
                    .getSingleResult();
            cache.put(key, configuration);
            return configuration;
        } catch (NoResultException e) {
            throw new IllegalStateException("Configuration for " + key + " not found.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Configuration> root, ConfigurationOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case KEY:
            return root.get("key");
        default:
            throw new IllegalStateException("Unknown "
                    + ConfigurationOrderEnum.class.getSimpleName() + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Configuration> getEntityClass() {
        return Configuration.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postSave(Configuration entity) {
        cache.put(entity.getKey(), entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postDelete(Configuration entity) {
        cache.remove(entity.getKey());
    }

}

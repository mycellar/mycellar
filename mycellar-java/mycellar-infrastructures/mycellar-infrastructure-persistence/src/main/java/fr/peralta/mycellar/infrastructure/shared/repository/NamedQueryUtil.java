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

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.OrderBy;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * Helper class to create named query supporting dynamic sort order and
 * pagination.
 */
@Named
@Singleton
public class NamedQueryUtil {
    private static final Logger logger = LoggerFactory.getLogger(NamedQueryUtil.class);

    private static final String NAMED_PARAMETER_CURRENT_USER_ID = "currentUserId";
    private static final String NAMED_PARAMETER_NOW = "now";

    private EntityManager entityManager;

    public <T> List<T> findByNamedQuery(SearchParameters sp) {
        if ((sp == null) || StringUtils.isBlank(sp.getNamedQuery())) {
            throw new IllegalArgumentException("searchParameters must be non null and must have a namedQuery");
        }

        Query query = entityManager.createNamedQuery(sp.getNamedQuery());
        String queryString = getQueryString(query);

        // append order by if needed
        if ((queryString != null) && !sp.getOrders().isEmpty()) {
            // create the sql restriction clausis
            StringBuilder orderClausis = new StringBuilder("order by ");
            boolean first = true;
            for (OrderBy orderBy : sp.getOrders()) {
                if (!first) {
                    orderClausis.append(", ");
                }
                orderClausis.append(getPath(orderBy.getAttributes()));
                orderClausis.append(orderBy.isOrderDesc() ? " desc" : " asc");
                first = false;
            }

            logger.trace("appending: [{}] to {}", orderClausis, queryString);

            query = recreateQuery(query, queryString + " " + orderClausis.toString());
        }

        // pagination
        if (sp.getFirstResult() >= 0) {
            query.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            query.setMaxResults(sp.getMaxResults());
        }

        // named parameters
        setQueryParameters(query, sp);

        // execute
        @SuppressWarnings("unchecked")
        List<T> result = query.getResultList();

        if (result != null) {
            logger.trace("{} returned a List of size: {}", sp.getNamedQuery(), result.size());
        }

        return result;
    }

    /**
     * @param attributes
     * @return
     */
    private String getPath(List<Attribute<?, ?>> attributes) {
        StringBuilder builder = new StringBuilder();
        for (Attribute<?, ?> attribute : attributes) {
            builder.append(attribute.getName()).append(".");
        }
        return builder.substring(0, builder.length() - 1);
    }

    @SuppressWarnings("unchecked")
    public <T> T byNamedQuery(SearchParameters sp) {
        return (T) objectByNamedQuery(sp);
    }

    public Number numberByNamedQuery(SearchParameters sp) {
        return (Number) objectByNamedQuery(sp);
    }

    public Object objectByNamedQuery(SearchParameters sp) {
        if ((sp == null) || StringUtils.isBlank(sp.getNamedQuery())) {
            throw new IllegalStateException("Invalid search template provided: could not determine which namedQuery to use");
        }

        Query query = entityManager.createNamedQuery(sp.getNamedQuery());
        String queryString = getQueryString(query);

        // append select count if needed
        if ((queryString != null) && queryString.toLowerCase().startsWith("from") && !queryString.toLowerCase().contains("count(")) {
            query = recreateQuery(query, "select count(*) " + queryString);
        }

        setQueryParameters(query, sp);

        logger.debug("objectNamedQuery : {}", sp.toString());

        // execute
        Object result = query.getSingleResult();

        if (logger.isDebugEnabled()) {
            logger.debug("{} returned a {} object", sp.getNamedQuery(), result == null ? "null" : result.getClass());
            if (result instanceof Number) {
                logger.debug("{} returned a number with value : {}", sp.getNamedQuery(), result);
            }
        }

        return result;
    }

    private void setQueryParameters(Query query, SearchParameters sp) {
        // add default parameter if specified in the named query
        for (Parameter<?> p : query.getParameters()) {
            if (NAMED_PARAMETER_CURRENT_USER_ID.equals(p.getName())) {
                // query.setParameter(NAMED_PARAMETER_CURRENT_USER_ID,
                // UserContext.getId());
            } else if (NAMED_PARAMETER_NOW.equals(p.getName())) {
                query.setParameter(NAMED_PARAMETER_NOW, Calendar.getInstance().getTime());
            }
        }

        // add parameters for the named query
        for (Entry<String, Object> entrySet : sp.getNamedQueryParameters().entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
    }

    /**
     * If the named query has the "query" hint, it uses the hint value (which
     * must be jpa QL) to create a new query and append to it the proper order
     * by clause.
     */
    private String getQueryString(Query query) {
        Map<String, Object> hints = query.getHints();
        return hints != null ? (String) hints.get("query") : null;
    }

    private Query recreateQuery(Query current, String newSqlString) {
        Query result = entityManager.createQuery(newSqlString);
        for (Entry<String, Object> hint : current.getHints().entrySet()) {
            result.setHint(hint.getKey(), hint.getValue());
        }
        return result;
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
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
package fr.mycellar.infrastructure.contact.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.domain.contact.Contact;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaContactRepository extends JpaSimpleRepository<Contact> implements ContactRepository {

    private final static Logger logger = LoggerFactory.getLogger(JpaContactRepository.class);

    /**
     * Default constructor.
     */
    public JpaContactRepository() {
        super(Contact.class);
    }

    @Override
    public long countLastContacts(SearchParameters<Contact> search) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"), root.<LocalDate> get("current")));
        Predicate predicate = getPredicate(query, root, criteriaBuilder, search);
        if (predicate == null) {
            predicate = criteriaBuilder.not(criteriaBuilder.exists(subquery));
        } else {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(criteriaBuilder.exists(subquery)));
        }
        return getEntityManager().createQuery(query.select(criteriaBuilder.count(root)).where(predicate)).getSingleResult();
    }

    @Override
    public List<Contact> getLastContacts(SearchParameters<Contact> search) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"), root.<LocalDate> get("current")));

        Predicate predicate = getPredicate(query, root, criteriaBuilder, search);
        if (predicate == null) {
            predicate = criteriaBuilder.not(criteriaBuilder.exists(subquery));
        } else {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(criteriaBuilder.exists(subquery)));
        }

        TypedQuery<Contact> typedQuery = getEntityManager().createQuery( //
                query.orderBy(getOrderByUtil().buildJpaOrders(search.getOrders(), root, criteriaBuilder)) //
                        .select(root) //
                        .where(predicate));

        getJpaUtil().applyPagination(typedQuery, search);
        List<Contact> result = typedQuery.getResultList();
        logger.trace("Returned {} lastContacts for {}.", result.size(), search);

        return result;
    }

    @Override
    public List<Contact> getAllToContact() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"), root.<LocalDate> get("current")));

        return getEntityManager().createQuery(
                query.select(root).where(criteriaBuilder.not(criteriaBuilder.exists(subquery)), criteriaBuilder.lessThanOrEqualTo(root.<LocalDate> get("next"), new LocalDate()))).getResultList();
    }

}

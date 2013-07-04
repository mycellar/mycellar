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
package fr.peralta.mycellar.infrastructure.contact.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactRepository;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaSimpleRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaUtil;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaContactRepository extends JpaSimpleRepository<Contact> implements ContactRepository {

    /**
     * Default constructor.
     */
    public JpaContactRepository() {
        super(Contact.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countLastContacts() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(
                criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"),
                        root.<LocalDate> get("current")));

        return getEntityManager().createQuery(
                query.select(criteriaBuilder.count(root)).where(
                        criteriaBuilder.not(criteriaBuilder.exists(subquery)))).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> getLastContacts(SearchParameters searchParameters) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(
                criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"),
                        root.<LocalDate> get("current")));

        return getEntityManager()
                .createQuery(
                        query.orderBy(
                                JpaUtil.buildJpaOrders(searchParameters.getOrders(), root,
                                        criteriaBuilder, searchParameters)).select(root)
                                .where(criteriaBuilder.not(criteriaBuilder.exists(subquery))))
                .setFirstResult(searchParameters.getFirstResult())
                .setMaxResults(searchParameters.getMaxResults()).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> getAllToContact() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = query.from(Contact.class);

        Subquery<Contact> subquery = query.subquery(Contact.class);
        Root<Contact> subroot = subquery.from(Contact.class);
        subquery.select(subroot).where(
                criteriaBuilder.equal(root.get("producer"), subroot.get("producer")),
                criteriaBuilder.greaterThan(subroot.<LocalDate> get("current"),
                        root.<LocalDate> get("current")));

        return getEntityManager().createQuery(
                query.select(root).where(
                        criteriaBuilder.not(criteriaBuilder.exists(subquery)),
                        criteriaBuilder.lessThanOrEqualTo(root.<LocalDate> get("next"),
                                new LocalDate()))).getResultList();
    }

}

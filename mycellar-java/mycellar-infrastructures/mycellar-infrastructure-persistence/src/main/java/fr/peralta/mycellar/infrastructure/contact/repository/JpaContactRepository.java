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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.contact.repository.ContactRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaContactRepository extends
        JpaEntityRepository<Contact, ContactOrderEnum, ContactOrder> implements ContactRepository {

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Contact> root, ContactOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case PRODUCER_NAME:
            return root.get("producer").get("name");
        case CURRENT_DATE:
            return root.get("current");
        case NEXT_DATE:
            return root.get("next");
        default:
            throw new IllegalStateException("Unknown " + ContactOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Contact> getEntityClass() {
        return Contact.class;
    }

}

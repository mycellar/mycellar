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
package fr.mycellar.infrastructure.user.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;

import fr.mycellar.domain.user.ResetPasswordRequest;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.user.repository.ResetPasswordRequestRepository;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaResetPasswordRequestRepository extends JpaSimpleRepository<ResetPasswordRequest>
        implements ResetPasswordRequestRepository {

    /**
     * Default constructor.
     */
    public JpaResetPasswordRequestRepository() {
        super(ResetPasswordRequest.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOldRequests() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ResetPasswordRequest> query = criteriaBuilder
                .createQuery(ResetPasswordRequest.class);
        Root<ResetPasswordRequest> root = query.from(ResetPasswordRequest.class);
        query.select(root).where(
                criteriaBuilder.lessThanOrEqualTo(root.<LocalDate> get("dateTime"),
                        new LocalDate().minusDays(1)));

        List<ResetPasswordRequest> requests = getEntityManager().createQuery(query).getResultList();
        for (ResetPasswordRequest request : requests) {
            getEntityManager().remove(request);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllForUser(User user) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ResetPasswordRequest> query = criteriaBuilder
                .createQuery(ResetPasswordRequest.class);
        Root<ResetPasswordRequest> root = query.from(ResetPasswordRequest.class);
        query.select(root).where(criteriaBuilder.equal(root.get("user"), user));

        List<ResetPasswordRequest> requests = getEntityManager().createQuery(query).getResultList();
        for (ResetPasswordRequest request : requests) {
            getEntityManager().remove(request);
        }
    }

}

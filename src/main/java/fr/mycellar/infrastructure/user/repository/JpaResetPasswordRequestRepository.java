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

import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.JpaSimpleRepository;

import org.joda.time.LocalDateTime;

import fr.mycellar.domain.user.ResetPasswordRequest;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaResetPasswordRequestRepository extends JpaSimpleRepository<ResetPasswordRequest, Integer> implements ResetPasswordRequestRepository {

    /**
     * Default constructor.
     */
    public JpaResetPasswordRequestRepository() {
        super(ResetPasswordRequest.class);
    }

    @Override
    public void deleteOldRequests() {
        getEntityManager().createQuery("DELETE " + ResetPasswordRequest.class.getSimpleName() + " WHERE dateTime <= :dateTime").setParameter("dateTime", new LocalDateTime().minusDays(1)).executeUpdate();
    }

    @Override
    public void deleteAllForUser(User user) {
        getEntityManager().createQuery("DELETE " + ResetPasswordRequest.class.getSimpleName() + " WHERE user = :user").setParameter("user", user).executeUpdate();
    }
}

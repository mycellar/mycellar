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
package fr.mycellar.interfaces.facades.user;

import java.util.List;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.shared.repository.SearchParameters;
import fr.mycellar.domain.user.ResetPasswordRequest;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface UserServiceFacade {

    /**
     * @param email
     * @param password
     * @return
     */
    User authenticateUser(String email, String password);

    /**
     * @param searchParameters
     * @return
     */
    long countUsers(SearchParameters searchParameters);

    /**
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * @param searchParameters
     * @return
     */
    List<User> getUsers(SearchParameters searchParameters);

    /**
     * @param term
     * @return
     */
    List<User> getUsersLike(String term);

    /**
     * @param user
     * @return
     * @throws BusinessException
     */
    User saveUser(User user) throws BusinessException;

    /**
     * @param user
     * @param password
     * @return
     * @throws BusinessException
     */
    User saveUserPassword(User user, String password) throws BusinessException;

    /**
     * @param email
     * @param url
     * @throws BusinessException
     */
    void resetPasswordRequest(String email, String url);

    /**
     * @param key
     * @return
     */
    ResetPasswordRequest getResetPasswordRequestByKey(String key);

    /**
     * @param user
     */
    void deleteAllResetPasswordRequestsForUser(User user);

    /**
     * @param user
     * @throws BusinessException
     */
    void deleteUser(User user) throws BusinessException;
}

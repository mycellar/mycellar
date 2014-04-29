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

import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface UserServiceFacade {

    User authenticateUser(String email, String password);

    long countUsers(SearchParameters<User> search);

    long countUsersLike(String term, SearchParameters<User> search);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    List<User> getUsers(SearchParameters<User> search);

    List<User> getUsersLike(String term, SearchParameters<User> search);

    User saveUser(User user) throws BusinessException;

    User saveUserPassword(User user, String password) throws BusinessException;

    void resetPasswordRequest(String email, String url);

    String getEmailFromResetPasswordRequestByKey(String key) throws BusinessException;

    void deleteUser(User user) throws BusinessException;

    User resetPassword(String key, String password) throws BusinessException;

    void validateUser(User user) throws BusinessException;

}

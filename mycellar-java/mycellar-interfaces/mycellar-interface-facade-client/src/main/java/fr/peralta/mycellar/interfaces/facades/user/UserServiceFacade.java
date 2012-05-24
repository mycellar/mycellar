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
package fr.peralta.mycellar.interfaces.facades.user;

import java.util.List;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;

/**
 * @author speralta
 */
public interface UserServiceFacade {

    /**
     * @param login
     * @param password
     * @return
     */
    User authenticateUser(String login, String password);

    /**
     * @return
     */
    long countUsers();

    /**
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<User> getUsers(UserOrder orders, int first, int count);

    /**
     * @param term
     * @return
     */
    List<User> getUsersLike(String term);

    /**
     * @param user
     * @throws BusinessException
     */
    void saveUser(User user) throws BusinessException;

}

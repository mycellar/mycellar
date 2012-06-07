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
package fr.peralta.mycellar.application.user;

import java.util.List;

import fr.peralta.mycellar.application.shared.EntityService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;

/**
 * @author speralta
 */
public interface UserService extends EntityService<User, UserOrderEnum, UserOrder> {

    /**
     * @param login
     * @param password
     * @return
     */
    User authenticate(String login, String password);

    /**
     * @param input
     * @return
     */
    List<User> getAllLike(String input);

    /**
     * @param user
     * @param password
     * @throws BusinessException
     */
    void saveUserPassword(User user, String password) throws BusinessException;

    /**
     * @param email
     * @throws BusinessException
     */
    void resetPasswordRequest(String email);

}

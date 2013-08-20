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

import fr.peralta.mycellar.application.shared.SimpleService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface UserService extends SimpleService<User> {

    /**
     * @param email
     * @param password
     * @return
     */
    User authenticate(String email, String password);

    /**
     * @param user
     * @param password
     * @throws BusinessException
     */
    void saveUserPassword(User user, String password) throws BusinessException;

    /**
     * @param email
     * @param url
     */
    void resetPasswordRequest(String email, String url);

    /**
     * @param email
     * @return
     */
    User getByEmail(String email);

}

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
package fr.mycellar.application.user;

import fr.mycellar.application.shared.SearchableService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
public interface UserService extends SearchableService<User> {

    User authenticate(String email, String password);

    User saveUserPassword(User user, String password) throws BusinessException;

    void resetPasswordRequest(String email, String url);

    User getByEmail(String email);

    User resetPassword(String key, String password) throws BusinessException;

}

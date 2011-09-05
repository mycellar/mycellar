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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.user.UserService;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Service
public class UserServiceFacadeImpl implements UserServiceFacade {

    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void newUser(User user) {
        userService.newUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public User authenticateUser(String login, String password) {
        return userService.authenticate(login, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    /**
     * @param userService
     *            the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

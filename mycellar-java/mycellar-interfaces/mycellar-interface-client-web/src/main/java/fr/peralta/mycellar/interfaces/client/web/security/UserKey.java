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
package fr.peralta.mycellar.interfaces.client.web.security;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;

import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public class UserKey extends MetaDataKey<User> {

    private static final long serialVersionUID = 201108261637L;

    private static UserKey USER_KEY = new UserKey();

    /**
     * @param user
     *            the user logged in
     */
    public static void userLogsIn(User user) {
        Session.get().setMetaData(USER_KEY, user);
    }

    /**
     * Remove the user from the session.
     */
    public static void userLogsOut() {
        Session.get().setMetaData(USER_KEY, null);
    }

    /**
     * @return the logged in user (can be null)
     */
    public static User getUserLoggedIn() {
        return Session.get().getMetaData(USER_KEY);
    }

    /**
     * Refuse instantiation.
     */
    private UserKey() {
        super();
    }

}

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
package fr.mycellar.domain.user;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import fr.mycellar.domain.shared.IdentifiedEntityHasSameProperties;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
public class UserHasSameProperties extends IdentifiedEntityHasSameProperties<User> {

    /**
     * @param object
     */
    public UserHasSameProperties(User object) {
        super(object);
        addProperty("email", is(equalTo(object.getEmail())));
        addProperty("firstname", is(equalTo(object.getFirstname())));
        addProperty("lastname", is(equalTo(object.getLastname())));
        addProperty("password", is(equalTo(object.getPassword())));
    }

}

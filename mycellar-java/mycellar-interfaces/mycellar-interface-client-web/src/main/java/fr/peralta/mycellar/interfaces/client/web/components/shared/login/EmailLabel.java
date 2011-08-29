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
package fr.peralta.mycellar.interfaces.client.web.components.shared.login;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
class EmailLabel extends Label {

    private static final long serialVersionUID = 201108261716L;

    /**
     * @param id
     */
    public EmailLabel(String id) {
        super(id);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        User user = UserKey.getUserLoggedIn();
        setVisibilityAllowed(user != null);
        setDefaultModel(new Model<String>(user != null ? user.getEmail() : ""));
    }

}

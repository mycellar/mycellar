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

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.security.WaspSession;

import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
class LogoutLink extends Link<Void> {

    private static final long serialVersionUID = 201108261718L;

    /**
     * @param id
     */
    public LogoutLink(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick() {
        WaspSession session = (WaspSession) Session.get();
        session.logoff(new MyCellarLoginContext(UserKey.getUserLoggedIn().getEmail(), UserKey
                .getUserLoggedIn().getPassword()));
        session.invalidateNow();
        setResponsePage(WebApplication.get().getHomePage());
    }

}

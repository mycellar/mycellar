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

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import fr.peralta.mycellar.interfaces.client.web.pages.MyAccountPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
public class LoggedInContainer extends WebMarkupContainer {

    private static final long serialVersionUID = 201202291256L;

    /**
     * @param id
     */
    public LoggedInContainer(String id) {
        super(id);
        add(new BookmarkablePageLink<Void>("emailLink", MyAccountPage.class).add(new EmailLabel(
                "email")));
        add(new LogoutLink("logout"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisibilityAllowed(UserKey.getUserLoggedIn() != null);
    }

}

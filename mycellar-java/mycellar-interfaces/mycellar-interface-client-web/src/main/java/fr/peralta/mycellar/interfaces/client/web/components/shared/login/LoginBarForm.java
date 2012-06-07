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

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import fr.peralta.mycellar.interfaces.client.web.pages.user.LoginPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.NewUserPage;

/**
 * @author speralta
 */
class LoginBarForm extends AbstractLoginForm {

    private static final long serialVersionUID = 201108221836L;

    /**
     * @param id
     */
    public LoginBarForm(String id) {
        super(id);
        add(new EmailTextField("username").setType(String.class).setRequired(true));
        add(new PasswordTextField("password").setType(String.class).setRequired(true));
        add(new BookmarkablePageLink<Void>("newUserLink", NewUserPage.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onError() {
        setResponsePage(LoginPage.class, LoginPage.getPageParametersWithError());
    }
}

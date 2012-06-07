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
package fr.peralta.mycellar.interfaces.client.web.pages.user;

import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import fr.peralta.mycellar.interfaces.client.web.components.shared.login.LoginPanel;
import fr.peralta.mycellar.interfaces.client.web.components.user.edit.NewUserPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.BasePage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
public class LoginPage extends BasePage {

    private static final long serialVersionUID = 201205312123L;

    private static final String ERROR_PARAMETER = "e";

    /**
     * @param username
     * @return
     */
    public static PageParameters getPageParametersWithError() {
        return new PageParameters().add(ERROR_PARAMETER, 1);
    }

    /**
     * @param parameters
     */
    public LoginPage(PageParameters parameters) {
        super(parameters);
        setStatelessHint(true);
        if (UserKey.getUserLoggedIn() != null) {
            setResponsePage(WebApplication.get().getHomePage());
        }
        hideLoginBarPanel();
        LoginPanel loginPanel = new LoginPanel("loginPanel");
        add(loginPanel);
        add(new NewUserPanel("newUserPanel"));
        StringValue username = parameters.get(ERROR_PARAMETER);
        if (!username.isEmpty()) {
            loginPanel.get("loginForm").error(
                    new StringResourceModel("exception.login", this, null).getString());
        }
    }

}

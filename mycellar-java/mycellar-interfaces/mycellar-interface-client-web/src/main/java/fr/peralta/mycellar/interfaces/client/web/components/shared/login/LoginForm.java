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

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.authentication.LoginContext;

import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
class LoginForm extends StatelessForm<ValueMap> {

    private static final long serialVersionUID = 201108221836L;

    /**
     * @param id
     */
    public LoginForm(String id) {
        super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
        add(new TextField<String>("username").setType(String.class).setRequired(true));
        add(new PasswordTextField("password").setType(String.class).setRequired(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisibilityAllowed(UserKey.getUserLoggedIn() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        ValueMap values = getModelObject();
        String username = values.getString("username");
        String password = values.getString("password");

        LoginContext ctx = new MyCellarLoginContext(username, password);
        try {
            ((WaspSession) getSession()).login(ctx);
            if (!getPage().continueToOriginalDestination()) {
                setResponsePage(getPage());
            }
        } catch (LoginException e) {
            error(getLocalizer().getString("exception.login", this));
        }
    }

}
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

import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.authentication.LoginContext;

import fr.peralta.mycellar.interfaces.client.web.pages.user.LoginPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.MyAccountPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;

/**
 * @author speralta
 */
abstract class AbstractLoginForm extends StatelessForm<ValueMap> {

    private static final long serialVersionUID = 201108221836L;

    /**
     * @param id
     */
    public AbstractLoginForm(String id) {
        super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
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
            if (UserKey.getUserLoggedIn().getProfile() == null) {
                setResponsePage(MyAccountPage.class);
            } else {
                getPage().continueToOriginalDestination();
                setResponsePage(getPage().getClass());
            }
        } catch (LoginException e) {
            setResponsePage(LoginPage.class, LoginPage.getPageParametersWithError());
        }
    }
}

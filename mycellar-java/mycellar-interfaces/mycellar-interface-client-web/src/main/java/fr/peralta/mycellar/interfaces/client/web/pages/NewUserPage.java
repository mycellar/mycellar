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
package fr.peralta.mycellar.interfaces.client.web.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.facades.user.User;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 * 
 */
public class NewUserPage extends HomeSuperPage {

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param parameters
     */
    public NewUserPage(PageParameters parameters) {
        super(parameters);
        Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(new User())) {
            private static final long serialVersionUID = 201111121710L;

            @Override
            protected void onSubmit() {
                userServiceFacade.newUser(getModelObject());
                setResponsePage(HomePage.class);
            }
        };
        form.add(new TextField<String>("email"));
        form.add(new TextField<String>("firstname"));
        form.add(new TextField<String>("lastname"));
        form.add(new PasswordTextField("password"));
        add(form);
    }

}

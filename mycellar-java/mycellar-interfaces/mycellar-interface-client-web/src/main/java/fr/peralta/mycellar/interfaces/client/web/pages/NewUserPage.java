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

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ContainerVisibleFeedbackMessageFilter;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FeedbackPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.FormValidationHelper;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class NewUserPage extends HomeSuperPage {

    private static class UserForm extends Form<User> {

        private static final long serialVersionUID = 201203291653L;

        @SpringBean
        private UserServiceFacade userServiceFacade;

        private PasswordTextField password;
        private PasswordTextField password2;

        /**
         * @param id
         */
        public UserForm(String id) {
            super(id, new CompoundPropertyModel<User>(new User()));
            add(new FormComponentFeedbackBorder("firstname").add(new TextField<String>("firstname")
                    .setRequired(true)));
            add(new FormComponentFeedbackBorder("lastname").add(new TextField<String>("lastname")
                    .setRequired(true)));
            add(new FormComponentFeedbackBorder("email").add(new EmailTextField("email")
                    .setRequired(true)));
            add(new FormComponentFeedbackBorder("password").add((password = new PasswordTextField(
                    "password")).setRequired(true)));
            add(new FormComponentFeedbackBorder("password2")
                    .add((password2 = new PasswordTextField("password2", new Model<String>()))
                            .setRequired(true)));
            add(new EqualPasswordInputValidator(password, password2));
            add(new FeedbackPanel("feedback", new ContainerVisibleFeedbackMessageFilter(this)));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            try {
                userServiceFacade.saveUser(getModelObject());
                setResponsePage(HomePage.class);
            } catch (BusinessException e) {
                FormValidationHelper.error(this, e);
            }
        }

    }

    private static final long serialVersionUID = 201203262225L;

    /**
     * @param parameters
     */
    public NewUserPage(PageParameters parameters) {
        super(parameters);
        add(new UserForm("form"));
    }

}

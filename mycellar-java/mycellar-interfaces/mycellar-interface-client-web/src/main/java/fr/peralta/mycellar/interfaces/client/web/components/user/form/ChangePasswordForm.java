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
package fr.peralta.mycellar.interfaces.client.web.components.user.form;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ContainerVisibleFeedbackMessageFilter;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;

public class ChangePasswordForm extends Form<String> {

    private static final long serialVersionUID = 201205310838L;

    private PasswordTextField password;
    private PasswordTextField password2;

    /**
     * @param id
     * @param model
     */
    public ChangePasswordForm(String id, IModel<String> model) {
        super(id, model);
        add(new FeedbackPanel("feedback", new ContainerVisibleFeedbackMessageFilter(this)));
        add(new FormComponentFeedbackBorder("password").add((password = new PasswordTextField(
                "password", getModel())).setResetPassword(true).setRequired(true)));
        add(new FormComponentFeedbackBorder("password2").add((password2 = new PasswordTextField(
                "password2", new Model<String>())).setResetPassword(true).setRequired(true)));
        add(new EqualPasswordInputValidator(password, password2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        send(getParent(), Broadcast.BUBBLE, Action.SAVE);
    }

}
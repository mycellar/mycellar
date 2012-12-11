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
package fr.peralta.mycellar.interfaces.client.web.components.user.edit;

import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.domain.user.ProfileEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.select.SelectEnumUtils;
import fr.peralta.mycellar.interfaces.client.web.components.shared.select.SelectRenderer;

/**
 * @author speralta
 */
public class UserEditPanel extends Panel {

    private static final long serialVersionUID = 201203091757L;

    /**
     * @param id
     */
    public UserEditPanel(String id) {
        super(id);
        add(new FormComponentFeedbackBorder("firstname").add(new TextField<String>("firstname")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("lastname").add(new TextField<String>("lastname")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("email").add(new EmailTextField("email")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("profile").add(new Select<ProfileEnum>("profile")
                .add(new SelectOptions<ProfileEnum>("options", SelectEnumUtils
                        .nullBeforeValues(ProfileEnum.class), new SelectRenderer<ProfileEnum>()))));
    }
}

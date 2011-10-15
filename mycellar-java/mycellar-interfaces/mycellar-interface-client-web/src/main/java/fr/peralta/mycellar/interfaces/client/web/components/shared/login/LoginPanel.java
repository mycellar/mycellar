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

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;

/**
 * @author speralta
 */
public class LoginPanel extends Panel {

    private static final long serialVersionUID = 201108221835L;

    /**
     * @param id
     */
    public LoginPanel(String id) {
        super(id);
        add(new LoginFeedbackPanel("feedback"));
        add(new LoginForm("loginForm"));
        add(new EmailLabel("email"));
        add(new LogoutLink("logout").add(new Image("logoutImage", ImageReferences
                .getDisconnectImage())));
    }
}

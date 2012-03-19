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
package fr.peralta.mycellar.interfaces.client.web.components.wine.edit;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.validation.validator.MinimumValidator;

import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;

/**
 * @author speralta
 */
public class FormatEditPanel extends Panel {

    private static final long serialVersionUID = 201011071626L;

    /**
     * @param id
     */
    public FormatEditPanel(String id) {
        super(id);
        add(new FormComponentFeedbackBorder("name").add(new TextField<String>("name")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("capacity").add(new TextField<Float>("capacity")
                .setRequired(true).add(new MinimumValidator<Float>(0.1f))));
    }
}

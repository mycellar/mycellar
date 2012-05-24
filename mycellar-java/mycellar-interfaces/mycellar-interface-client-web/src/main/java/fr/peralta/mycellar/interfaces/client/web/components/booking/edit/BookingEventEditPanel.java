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
package fr.peralta.mycellar.interfaces.client.web.components.booking.edit;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.LocalDateTextField;

/**
 * @author speralta
 */
public class BookingEventEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     */
    public BookingEventEditPanel(String id) {
        super(id);
        add(new FormComponentFeedbackBorder("name").add(new TextField<String>("name")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("start").add(new LocalDateTextField("start")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("end").add(new LocalDateTextField("end")
                .setRequired(true)));
        add(new BookingBottlesEditPanel("bottles"));
    }

}

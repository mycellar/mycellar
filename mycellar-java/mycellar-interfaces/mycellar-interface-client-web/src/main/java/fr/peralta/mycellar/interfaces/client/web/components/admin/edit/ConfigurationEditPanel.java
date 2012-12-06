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
package fr.peralta.mycellar.interfaces.client.web.components.admin.edit;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;

/**
 * @author speralta
 */
public class ConfigurationEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;

    private DropDownChoice<ConfigurationKeyEnum> key;

    /**
     * @param id
     */
    public ConfigurationEditPanel(String id) {
        super(id);
        add(new FormComponentFeedbackBorder("key")
                .add((key = new DropDownChoice<ConfigurationKeyEnum>("key", Arrays
                        .asList(ConfigurationKeyEnum.values()))).setRequired(true)));
        add(new FormComponentFeedbackBorder("value").add(new TextField<String>("value")
                .setRequired(true)));
    }

    /**
     * @param keyReadonly
     * @return this
     */
    public ConfigurationEditPanel setKeyReadonly(boolean keyReadonly) {
        key.setEnabled(!keyReadonly);
        return this;
    }
}

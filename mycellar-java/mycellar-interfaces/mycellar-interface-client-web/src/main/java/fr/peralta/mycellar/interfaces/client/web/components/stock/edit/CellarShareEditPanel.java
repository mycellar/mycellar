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
package fr.peralta.mycellar.interfaces.client.web.components.stock.edit;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.stock.cloud.AccessRightEnumSimpleTagCloud;

/**
 * @author speralta
 */
public class CellarShareEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public CellarShareEditPanel(String id, IModel<SearchForm> searchFormModel, CountEnum count,
            FilterEnum... filters) {
        super(id);
        add(new FormComponentFeedbackBorder("email").add(new EmailTextField("email")
                .setRequired(true)));
        add(new AccessRightEnumSimpleTagCloud("accessRight", new StringResourceModel("accessRight",
                null), searchFormModel, count, filters));
    }

}

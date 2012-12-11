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

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.RegionComplexTypeahead;
import fr.peralta.mycellar.interfaces.client.web.shared.FilterEnumHelper;

/**
 * @author speralta
 */
public class AppellationEditPanel extends Panel {

    private static final long serialVersionUID = 201011071626L;

    /**
     * @param id
     * @param searchFormModel
     * @param filters
     */
    public AppellationEditPanel(String id, IModel<SearchForm> searchFormModel,
            FilterEnum... filters) {
        super(id);
        add(new RegionComplexTypeahead("region", new StringResourceModel("region", this, null),
                searchFormModel, FilterEnumHelper.removeFilter(filters, FilterEnum.APPELLATION)));
        add(new FormComponentFeedbackBorder("name").add(new TextField<String>("name")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("description").add(new TextArea<String>("description")));
    }

}

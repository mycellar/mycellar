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
import org.apache.wicket.model.StringResourceModel;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.CountryComplexTagCloud;

/**
 * @author speralta
 */
public class RegionEditPanel extends Panel {

    private static final long serialVersionUID = 201011071626L;

    private final CountryComplexTagCloud countryComplexTagCloud;

    /**
     * @param id
     * @param count
     */
    public RegionEditPanel(String id, CountEnum count) {
        super(id);
        add(countryComplexTagCloud = new CountryComplexTagCloud("country", new StringResourceModel(
                "country", this, null), new SearchFormModel(new SearchForm()), count));
        add(new FormComponentFeedbackBorder("name").add(new TextField<String>("name")
                .setRequired(true)));
        add(new FormComponentFeedbackBorder("description").add(new TextArea<String>("description")));
    }

    public RegionEditPanel setCountryCancelAllowed(boolean allowed) {
        countryComplexTagCloud.setCancelAllowed(allowed);
        return this;
    }

}

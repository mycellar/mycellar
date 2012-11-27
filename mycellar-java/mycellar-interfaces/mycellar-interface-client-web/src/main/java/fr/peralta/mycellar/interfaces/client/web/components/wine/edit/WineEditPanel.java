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

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerComplexTypeahead;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.AppellationComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineColorEnumSimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.WineTypeEnumSimpleTagCloud;

/**
 * @author speralta
 */
public class WineEditPanel extends Panel {

    private static final long serialVersionUID = 201109081819L;

    /**
     * @param id
     * @param count
     */
    public WineEditPanel(String id, CountEnum count) {
        super(id);
        SearchFormModel searchFormModel = new SearchFormModel(new SearchForm());
        add(new ProducerComplexTypeahead("producer", new StringResourceModel("producer", this,
                null), searchFormModel));
        add(new AppellationComplexTagCloud("appellation", new StringResourceModel("appellation",
                this, null), searchFormModel, count));
        add(new WineColorEnumSimpleTagCloud("color", new StringResourceModel("color", this, null),
                searchFormModel, count));
        add(new WineTypeEnumSimpleTagCloud("type", new StringResourceModel("type", this, null),
                searchFormModel, count));
        add(new FormComponentFeedbackBorder("vintage").add(new NumberTextField<Integer>("vintage")));
        add(new FormComponentFeedbackBorder("name").add(new TextField<String>("name")));
        add(new FormComponentFeedbackBorder("ranking").add(new TextField<String>("ranking")));
    }

}

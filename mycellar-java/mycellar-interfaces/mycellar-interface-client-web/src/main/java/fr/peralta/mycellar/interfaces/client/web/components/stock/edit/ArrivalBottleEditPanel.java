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

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.stock.BottleComponent;

/**
 * @author speralta
 */
public class ArrivalBottleEditPanel extends Panel {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     * @param searchFormModel
     */
    public ArrivalBottleEditPanel(String id, IModel<SearchForm> searchFormModel) {
        super(id);
        add(new BottleComponent("bottle", searchFormModel, CountEnum.STOCK_QUANTITY));
        add(new FormComponentFeedbackBorder("quantity").add(new TextField<Integer>("quantity")
                .setRequired(true).add(RangeValidator.minimum(1))));
        add(new FormComponentFeedbackBorder("price").add(new TextField<Float>("price").setRequired(
                true).add(RangeValidator.minimum(0f))));
    }

}

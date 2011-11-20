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
package fr.peralta.mycellar.interfaces.client.web.components.shared;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ActionAjaxImageButton;
import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;

/**
 * @author speralta
 */
public class ValueComponent extends Panel {

    private static final long serialVersionUID = 201111181728L;

    private static final String VALUE_COMPONENT_ID = "value";
    private static final String CANCEL_COMPONENT_ID = "cancel";

    /**
     * @param id
     */
    public ValueComponent(String id) {
        super(id);
        add(new TextField<String>(VALUE_COMPONENT_ID).setDefaultModel(new Model<String>()));
        add(new ActionAjaxImageButton(CANCEL_COMPONENT_ID, Action.CANCEL,
                ImageReferences.getCancelImage()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged() {
        int length = 10;
        String value = (String) getDefaultModelObject();
        if (value != null) {
            length = value.length();
        }
        get(VALUE_COMPONENT_ID).setDefaultModel(getDefaultModel()).add(
                new AttributeModifier("size", new Model<Integer>(length)));
    }

}

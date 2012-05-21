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
import org.apache.wicket.Component;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.interfaces.client.web.behaviors.DependantAttributeModifier;

/**
 * @author speralta
 */
public class ValueComponent extends Panel {

    private static class LengthModel extends ParentComponentModel<Integer> {
        private static final long serialVersionUID = 201111181728L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected Integer getObject(Component component) {
            int length = 10;
            String value = (String) component.getDefaultModelObject();
            if (value != null) {
                length = value.length();
            }
            return length;
        }

    }

    private static final long serialVersionUID = 201111181728L;

    private static final String CONTAINER_COMPONENT_ID = "container";
    private static final String VALUE_COMPONENT_ID = "value";
    private static final String CANCEL_COMPONENT_ID = "cancel";

    private final ActionLink cancelLink;

    /**
     * @param id
     * @param valueMarkupId
     */
    public ValueComponent(String id, String valueMarkupId) {
        super(id);
        WebMarkupContainer container = new WebMarkupContainer(CONTAINER_COMPONENT_ID);
        container.setDefaultModel(new ParentComponentModel<String>()).add(
                new DependantAttributeModifier("class", "input-append") {
                    private static final long serialVersionUID = 201205111815L;

                    @Override
                    protected boolean dependencyVerified() {
                        return cancelLink.isVisibilityAllowed();
                    }
                });
        add(container);
        container.add(new TextField<String>(VALUE_COMPONENT_ID)
                .setDefaultModel(new ParentComponentModel<String>()).setMarkupId(valueMarkupId)
                .add(new AttributeModifier("size", new LengthModel())));
        container.add(cancelLink = new ActionLink(CANCEL_COMPONENT_ID, Action.CANCEL));
    }

    /**
     * @param allowed
     */
    public void setCancelAllowed(boolean allowed) {
        cancelLink.setVisibilityAllowed(allowed);
    }

    /**
     * @return
     */
    public boolean isCancelLink(IEventSource source) {
        return cancelLink == source;
    }

}

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

import java.util.List;

import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import fr.peralta.mycellar.domain.stock.ArrivalBottle;
import fr.peralta.mycellar.interfaces.client.web.behaviors.NotEmptyCollectionValidator;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;

/**
 * @author speralta
 */
public class ArrivalBottlesEditPanel extends FormComponentPanel<List<ArrivalBottle>> {

    private static class ArrivalBottlesView extends PropertyListView<ArrivalBottle> {

        private static final long serialVersionUID = 201108082321L;

        /**
         * @param id
         */
        public ArrivalBottlesView(String id) {
            super(id);
            setReuseItems(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(ListItem<ArrivalBottle> item) {
            item.add(new Label("bottle.wine.appellation.region.country.name"));
            item.add(new Label("bottle.wine.appellation.region.name"));
            item.add(new Label("bottle.wine.appellation.name"));
            item.add(new Label("bottle.wine.producer.name"));
            item.add(new Label("bottle.wine.name"));
            item.add(new Label("bottle.wine.vintage"));
            item.add(new Label("bottle.format.name"));
            item.add(new Label("quantity"));
            item.add(new WebMarkupContainer("remove").add(removeLink("removeBottle", item)));
        }
    }

    private static final long serialVersionUID = 201202231626L;

    private static final String NO_BOTTLES_COMPONENT_ID = "noBottles";

    private final ActionLink addBottle;

    /**
     * @param id
     */
    public ArrivalBottlesEditPanel(String id) {
        super(id);
        add(new ArrivalBottlesView("arrivalBottles"));
        add(addBottle = new ActionLink("addBottle", Action.ADD));
        add(new WebMarkupContainer(NO_BOTTLES_COMPONENT_ID) {
            private static final long serialVersionUID = 201108082329L;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isVisible() {
                return ArrivalBottlesEditPanel.this.getModelObject().size() == 0;
            }
        });
        add(new NotEmptyCollectionValidator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void convertInput() {
        setConvertedInput(getModelObject());
    }

    /**
     * @param source
     * @return
     */
    public boolean isAddBottle(IEventSource source) {
        return addBottle == source;
    }

}
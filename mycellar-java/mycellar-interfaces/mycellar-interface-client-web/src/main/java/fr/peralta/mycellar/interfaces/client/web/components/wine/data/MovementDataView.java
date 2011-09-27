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
package fr.peralta.mycellar.interfaces.client.web.components.wine.data;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataViewBase;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Input;
import fr.peralta.mycellar.domain.stock.Movement;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;

/**
 * @author speralta
 * 
 */
public class MovementDataView extends DataViewBase<Movement<?>> {

    private static final long serialVersionUID = 201109192009L;

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    /**
     * @param id
     * @param searchFormModel
     */
    public MovementDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, new MovementDataProvider(searchFormModel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(Item<Movement<?>> item) {
        item.setModel(new CompoundPropertyModel<Movement<?>>(item.getModel()));
        item.add(new Label("date"));
        item.add(new Label("cellar.name"));
        item.add(new Label("bottle", rendererServiceFacade
                .render(item.getModelObject().getBottle())));
        item.add(new Label("number"));
        item.add(new Label("io", (item.getModelObject() instanceof Input) ? "E" : "S"));
    }

}

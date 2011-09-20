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

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataViewBase;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 * 
 */
public class WineDataView extends DataViewBase<Wine> {

    private static final long serialVersionUID = 201109192009L;

    /**
     * @param id
     * @param colors
     * @param types
     * @param countries
     * @param regions
     * @param appellations
     */
    public WineDataView(String id, IModel<ArrayList<WineTypeEnum>> types,
            IModel<ArrayList<WineColorEnum>> colors, IModel<ArrayList<Country>> countries,
            IModel<ArrayList<Region>> regions, IModel<ArrayList<Appellation>> appellations) {
        super(id, new WineDataProvider(types, colors, countries, regions, appellations));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(Item<Wine> item) {
        item.setModel(new CompoundPropertyModel<Wine>(item.getModel()));
        item.add(new Label("appellation.region.country.name"));
        item.add(new Label("appellation.region.name"));
        item.add(new Label("appellation.name"));
        item.add(new Label("producer.name"));
        item.add(new Label("name"));
        item.add(new Label("type"));
        item.add(new Label("color"));
        item.add(new Label("vintage"));
    }

}

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
import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class WineDataProvider implements IDataProvider<Wine> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final IModel<ArrayList<WineTypeEnum>> types;
    private final IModel<ArrayList<WineColorEnum>> colors;
    private final IModel<ArrayList<Country>> countries;
    private final IModel<ArrayList<Region>> regions;
    private final IModel<ArrayList<Appellation>> appellations;

    /**
     * @param colors
     * @param types
     * @param countries
     * @param regions
     * @param appellations
     */
    public WineDataProvider(IModel<ArrayList<WineTypeEnum>> types,
            IModel<ArrayList<WineColorEnum>> colors, IModel<ArrayList<Country>> countries,
            IModel<ArrayList<Region>> regions, IModel<ArrayList<Appellation>> appellations) {
        Injector.get().inject(this);
        this.types = types;
        this.colors = colors;
        this.countries = countries;
        this.regions = regions;
        this.appellations = appellations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach() {
        countries.detach();
        regions.detach();
        appellations.detach();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Wine> iterator(int first, int count) {
        return wineServiceFacade.getWinesFrom(types.getObject(), colors.getObject(),
                countries.getObject(), regions.getObject(), appellations.getObject(), first, count)
                .iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return (int) wineServiceFacade.countWinesFrom(countries.getObject(), regions.getObject(),
                appellations.getObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Wine> model(Wine object) {
        return new Model<Wine>(object);
    }

}

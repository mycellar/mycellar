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
package fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.objectautocomplete.AutoCompletionChoicesProvider;

import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ProducerAutoCompleteProvider implements AutoCompletionChoicesProvider<Producer> {

    private static final long serialVersionUID = 201203161859L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * Default constructor.
     */
    public ProducerAutoCompleteProvider() {
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Producer> getChoices(String input) {
        return wineServiceFacade.getProducersLike(input).iterator();
    }

}

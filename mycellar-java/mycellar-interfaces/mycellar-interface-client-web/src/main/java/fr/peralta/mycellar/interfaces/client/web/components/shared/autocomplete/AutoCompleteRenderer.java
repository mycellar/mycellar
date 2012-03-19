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
package fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteRenderer;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;

/**
 * @author speralta
 * 
 */
public class AutoCompleteRenderer<E extends IdentifiedEntity<E>> extends
        ObjectAutoCompleteRenderer<E> {

    private static final long serialVersionUID = 201203161917L;

    @SpringBean
    RendererServiceFacade rendererServiceFacade;

    /**
     * Default constructor.
     */
    public AutoCompleteRenderer() {
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdValue(E object) {
        return Integer.toString(object.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTextValue(E object) {
        return rendererServiceFacade.render(object);
    }

}

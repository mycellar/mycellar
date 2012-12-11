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

import java.util.List;

import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 * 
 * @param <E>
 */
public class IdentifiedEntityTypeaheadComponent<E extends IdentifiedEntity> extends
        AbstractTypeaheadComponent<E> {

    private static final long serialVersionUID = 201212071928L;

    private final TypeaheadIdentifiedEntityProvider<E> provider;

    /**
     * @param id
     * @param provider
     */
    public IdentifiedEntityTypeaheadComponent(String id,
            TypeaheadIdentifiedEntityProvider<E> provider) {
        super(id);
        this.provider = provider;
        setChoiceRenderer(new EntityChoiceRenderer<E>());
    }

    /**
     * @param id
     * @param model
     * @param provider
     */
    public IdentifiedEntityTypeaheadComponent(String id, IModel<E> model,
            TypeaheadIdentifiedEntityProvider<E> provider) {
        super(id, model);
        this.provider = provider;
        setChoiceRenderer(new EntityChoiceRenderer<E>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<E> getChoices(String term) {
        return provider.getChoices(term);
    }

}

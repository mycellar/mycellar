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
package fr.peralta.mycellar.interfaces.client.web.components.shared.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

/**
 * @author speralta
 * 
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMapView<K, V> extends RefreshingView<Entry<K, V>> {

    private static final long serialVersionUID = 201108082321L;

    /**
     * @param id
     */
    public AbstractMapView(String id) {
        super(id);
    }

    /**
     * Gets model
     * 
     * @return model
     */
    @SuppressWarnings("unchecked")
    public final IModel<Map<K, V>> getModel() {
        return (IModel<Map<K, V>>) getDefaultModel();
    }

    /**
     * Gets model object
     * 
     * @return model object
     */
    @SuppressWarnings("unchecked")
    public final Map<K, V> getModelObject() {
        return (Map<K, V>) getDefaultModelObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Iterator<IModel<Entry<K, V>>> getItemModels() {
        return new EntryIteratorModel<K, V>(getModelObject().entrySet().iterator());
    }

    protected IModel<V> newValueModel(Item<Entry<K, V>> item) {
        return new EntryValueModel<K, V>(getModel(), item.getModel());
    }
}

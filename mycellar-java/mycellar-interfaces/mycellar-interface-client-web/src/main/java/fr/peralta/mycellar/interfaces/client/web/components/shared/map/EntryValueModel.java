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

import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.model.IModel;

/**
 * @author speralta
 * 
 * @param <K>
 * @param <V>
 */
class EntryValueModel<K, V> implements IModel<V> {

    private static final long serialVersionUID = 201205230927L;

    private final IModel<Map<K, V>> mapModel;
    private final IModel<Entry<K, V>> entryModel;

    /**
     * @param mapModel
     * @param entryModel
     */
    public EntryValueModel(IModel<Map<K, V>> mapModel, IModel<Entry<K, V>> entryModel) {
        this.mapModel = mapModel;
        this.entryModel = entryModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach() {
        mapModel.detach();
        entryModel.detach();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V getObject() {
        return entryModel.getObject().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObject(V object) {
        mapModel.getObject().put(entryModel.getObject().getKey(), object);
        entryModel.getObject().setValue(object);
    }

}
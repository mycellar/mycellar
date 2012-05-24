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
import java.util.Map.Entry;

import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;

/**
 * @author speralta
 * 
 * @param <K>
 * @param <V>
 */
class EntryIteratorModel<K, V> extends ModelIteratorAdapter<Entry<K, V>> {

    /**
     * @param delegate
     */
    public EntryIteratorModel(Iterator<Entry<K, V>> delegate) {
        super(delegate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IModel<Entry<K, V>> model(Entry<K, V> object) {
        IModel<Entry<K, V>> model = new EntryModel<K, V>();
        model.setObject(object);
        return model;
    }
}
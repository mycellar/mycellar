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

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.wicket.model.util.GenericBaseModel;

/**
 * @author speralta
 * 
 * @param <K>
 * @param <V>
 */
class EntryModel<K, V> extends GenericBaseModel<Entry<K, V>> {

    private static final long serialVersionUID = 201205230914L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Entry<K, V> createSerializableVersionOf(Entry<K, V> object) {
        return new SimpleEntry<K, V>(object);
    }

}
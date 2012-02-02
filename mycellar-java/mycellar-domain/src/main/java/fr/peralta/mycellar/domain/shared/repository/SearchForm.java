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
package fr.peralta.mycellar.domain.shared.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author speralta
 */
public class SearchForm implements Serializable {

    private static final long serialVersionUID = 201109231830L;

    private final Map<FilterEnum, Set<?>> sets = new HashMap<FilterEnum, Set<?>>();

    private boolean cellarModification = false;

    /**
     * @return the cellarModification
     */
    public boolean isCellarModification() {
        return cellarModification;
    }

    /**
     * @param cellarModification
     *            the cellarModification to set
     * @return this for chaining
     */
    public SearchForm setCellarModification(boolean cellarModification) {
        this.cellarModification = cellarModification;
        return this;
    }

    public final <O> SearchForm replaceSet(FilterEnum filter, Collection<? extends O> objects) {
        Set<O> set = getSet(filter);
        if (set == null) {
            set = new HashSet<O>();
            sets.put(filter, set);
        } else {
            set.clear();
        }
        set.addAll(objects);
        return this;
    }

    /**
     * @param filter
     * @param object
     * @return
     */
    public final <O> SearchForm replaceSet(FilterEnum filter, O object) {
        Set<O> set = getSet(filter);
        if (set == null) {
            set = new HashSet<O>();
            sets.put(filter, set);
        } else {
            set.clear();
        }
        set.add(object);
        return this;
    }

    /**
     * @param filter
     * @param set
     * @return this for chaining
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public final <O> SearchForm putSet(FilterEnum filter, Set<O> set) {
        sets.put(filter, set);
        return this;
    }

    /**
     * @param filter
     * @param set
     * @return this for chaining
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public final <O> SearchForm addToSet(FilterEnum filter, O object) {
        Set<O> set = getSet(filter);
        if (set == null) {
            set = new HashSet<O>();
            sets.put(filter, set);
        }
        set.add(object);
        return this;
    }

    /**
     * @param filter
     * @param set
     * @return this for chaining
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public final <O> SearchForm addAllToSet(FilterEnum filter, Collection<? extends O> objects) {
        Set<O> set = getSet(filter);
        if (set == null) {
            set = new HashSet<O>();
            sets.put(filter, set);
        }
        set.addAll(objects);
        return this;
    }

    /**
     * @param filter
     * @return
     * @see java.util.Map#get(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public final <O> Set<O> getSet(FilterEnum filter) {
        return (Set<O>) sets.get(filter);
    }

}

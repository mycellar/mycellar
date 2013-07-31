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
package fr.mycellar.interfaces.facade.web;

import java.util.List;

/**
 * @author speralta
 */
public class ListWithCount<T> {

    private final long count;
    private final List<T> list;

    /**
     * @param count
     * @param list
     */
    public ListWithCount(long count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

}

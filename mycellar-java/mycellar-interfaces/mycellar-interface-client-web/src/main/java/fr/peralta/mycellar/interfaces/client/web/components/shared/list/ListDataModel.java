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
package fr.peralta.mycellar.interfaces.client.web.components.shared.list;

import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author speralta
 */
class ListDataModel<O> extends AbstractReadOnlyModel<List<ListData<O>>> {

    private static final long serialVersionUID = 201205091648L;

    private final SimpleList<O> simpleList;
    private final ComplexList<O> complexList;

    /**
     * @param complexList
     */
    public ListDataModel(ComplexList<O> complexList) {
        this.complexList = complexList;
        this.simpleList = null;
    }

    /**
     * @param simpleList
     */
    public ListDataModel(SimpleList<O> simpleList) {
        this.simpleList = simpleList;
        this.complexList = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ListData<O>> getObject() {
        return simpleList != null ? simpleList.getList() : complexList.getList();
    }

}

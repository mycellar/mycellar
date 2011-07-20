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
package fr.peralta.mycellar.interfaces.client.web.components.shared.cloud;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

/**
 * @author speralta
 */
public class TagCloud<O> extends PropertyListView<TagData<O>> {

    private static final long serialVersionUID = 201011071626L;

    private final Class<? extends Component> parentToReRender;

    /**
     * @param id
     * @param list
     * @param parentToReRender
     */
    public TagCloud(String id, List<? extends TagData<O>> list,
            Class<? extends Component> parentToReRender) {
        super(id, list);
        this.parentToReRender = parentToReRender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(ListItem<TagData<O>> item) {
        item.add(new Tag<O>("object", parentToReRender));
    }

}

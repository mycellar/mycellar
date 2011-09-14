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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ComponentPropertyModel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;

/**
 * @author speralta
 */
class Tag<O> extends AjaxLink<TagData<O>> {

    private static final long serialVersionUID = 201107252130L;

    /**
     * @param id
     */
    public Tag(String id) {
        super(id);
        add(new Label("label").add(new AttributeAppender("style",
                new ComponentPropertyModel<String>("style"), ";")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
        send(getParent(), Broadcast.BUBBLE, Action.SELECT);
    }
}
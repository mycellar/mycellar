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
package fr.peralta.mycellar.interfaces.client.web.components.stack.data;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.StackPage;

/**
 * @author speralta
 */
public class StackDetailPanel extends Panel {

    private static final long serialVersionUID = 201111291932L;

    /**
     * @param id
     */
    public StackDetailPanel(String id, Integer stackId) {
        super(id);
        add(new BookmarkablePageLink<Void>("detail", StackPage.class,
                StackPage.getPageParameters(stackId)).add(new Image("detailImg", ImageReferences
                .getEyeImage())));
    }

}

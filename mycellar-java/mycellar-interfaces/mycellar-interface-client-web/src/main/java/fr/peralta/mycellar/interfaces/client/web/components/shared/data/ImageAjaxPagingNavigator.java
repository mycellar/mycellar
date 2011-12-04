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
package fr.peralta.mycellar.interfaces.client.web.components.shared.data;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;

/**
 * @author speralta
 */
public class ImageAjaxPagingNavigator extends AjaxPagingNavigator {

    private static final long serialVersionUID = 201111222012L;

    /**
     * @param id
     * @param pageable
     * @param labelProvider
     */
    public ImageAjaxPagingNavigator(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    /**
     * @param id
     * @param pageable
     */
    public ImageAjaxPagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<?> newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        Link<?> link = super.newPagingNavigationIncrementLink(id, pageable, increment);
        if ("next".equals(id)) {
            link.add(new Image("nextImg", ImageReferences.getPagingNextImage()));
        } else if ("prev".equals(id)) {
            link.add(new Image("prevImg", ImageReferences.getPagingPrevImage()));
        }
        return link;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<?> newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        Link<?> link = super.newPagingNavigationLink(id, pageable, pageNumber);
        if ("first".equals(id)) {
            link.add(new Image("firstImg", ImageReferences.getPagingFirstImage()));
        } else if ("last".equals(id)) {
            link.add(new Image("lastImg", ImageReferences.getPagingLastImage()));
        }
        return link;
    }

}

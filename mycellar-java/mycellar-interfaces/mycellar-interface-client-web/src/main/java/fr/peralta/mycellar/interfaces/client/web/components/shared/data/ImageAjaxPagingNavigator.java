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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;

/**
 * @author speralta
 */
public class ImageAjaxPagingNavigator extends AjaxPagingNavigator {

    private static final long serialVersionUID = 201111222012L;

    private final int viewSize;

    /**
     * @param id
     * @param pageable
     * @param labelProvider
     * @param viewSize
     */
    public ImageAjaxPagingNavigator(String id, IPageable pageable,
            IPagingLabelProvider labelProvider, int viewSize) {
        super(id, pageable, labelProvider);
        this.viewSize = viewSize;
    }

    /**
     * @param id
     * @param pageable
     * @param viewSize
     */
    public ImageAjaxPagingNavigator(String id, IPageable pageable, int viewSize) {
        super(id, pageable);
        this.viewSize = viewSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<?> newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        return new ImageAjaxPagingNavigationIncrementLink(id, pageable, increment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<?> newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        return new ImageAjaxPagingNavigationLink(id, pageable, pageNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PagingNavigation newNavigation(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        ImageAjaxPagingNavigation navigation = new ImageAjaxPagingNavigation(id, pageable,
                labelProvider);
        navigation.setViewSize(viewSize);
        return navigation;
    }

}

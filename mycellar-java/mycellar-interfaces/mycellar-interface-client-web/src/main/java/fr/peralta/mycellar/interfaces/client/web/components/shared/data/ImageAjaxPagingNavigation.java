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

import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.collections.MicroMap;

/**
 * @author speralta
 */
public class ImageAjaxPagingNavigation extends AjaxPagingNavigation {

    private static final long serialVersionUID = 201111222012L;

    /**
     * @param id
     * @param pageable
     * @param labelProvider
     */
    public ImageAjaxPagingNavigation(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    /**
     * @param id
     * @param pageable
     */
    public ImageAjaxPagingNavigation(String id, IPageable pageable) {
        super(id, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<?> newPagingNavigationLink(String id, IPageable pageable, long pageIndex) {
        return new ImageAjaxPagingNavigationLink(id, pageable, pageIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(LoopItem loopItem) {
        // Get the index of page this link shall point to
        final long pageIndex = getStartIndex() + loopItem.getIndex();

        // Add a page link pointing to the page
        final AbstractLink link = newPagingNavigationLink("pageLink", pageable, pageIndex);
        link.add(new TitleAppender(pageIndex));
        loopItem.add(link);

        // Add a page number label to the list which is enclosed by the link
        String label = "";
        if (labelProvider != null) {
            label = labelProvider.getPageLabel(pageIndex);
        } else {
            label = String.valueOf(pageIndex + 1);
        }
        link.add(new Label("pageNumber", label));
        loopItem.add(new AttributeModifier("class", new AbstractReadOnlyModel<String>() {
            private static final long serialVersionUID = 201205251402L;

            /**
             * {@inheritDoc}
             */
            @Override
            public String getObject() {
                return pageIndex == pageable.getCurrentPage() ? "active" : "";
            }

        }));
    }

    /**
     * Appends title attribute to navigation links
     * 
     * @author igor.vaynberg
     */
    private final class TitleAppender extends Behavior {
        private static final long serialVersionUID = 1L;
        /** resource key for the message */
        private static final String RES = "PagingNavigation.page";
        /** page number */
        private final long page;

        /**
         * Constructor
         * 
         * @param page
         *            page number to use as the ${page} var
         */
        public TitleAppender(long page) {
            this.page = page;
        }

        /** {@inheritDoc} */
        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            Map<String, String> vars = new MicroMap<String, String>("page", Long.toString(page + 1));
            tag.put("title", ImageAjaxPagingNavigation.this.getString(RES, Model.ofMap(vars)));
        }
    }

}

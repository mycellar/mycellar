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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByLink;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;

/**
 * @author speralta
 */
public abstract class AdvancedAjaxFallbackOrderByBorder extends Border {

    private static final long serialVersionUID = 201111280846L;

    /**
     * Constructor
     * 
     * @param id
     * @param property
     * @param stateLocator
     */
    public AdvancedAjaxFallbackOrderByBorder(final String id, final String property,
            final ISortStateLocator stateLocator) {
        this(id, property, stateLocator, AjaxFallbackOrderByLink.DefaultCssProvider.getInstance(),
                null);
    }

    /**
     * Constructor
     * 
     * @param id
     * @param property
     * @param stateLocator
     * @param cssProvider
     */
    public AdvancedAjaxFallbackOrderByBorder(final String id, final String property,
            final ISortStateLocator stateLocator,
            final AjaxFallbackOrderByLink.ICssProvider cssProvider) {
        this(id, property, stateLocator, cssProvider, null);
    }

    /**
     * Constructor
     * 
     * @param id
     * @param property
     * @param stateLocator
     * @param decorator
     */
    public AdvancedAjaxFallbackOrderByBorder(final String id, final String property,
            final ISortStateLocator stateLocator, final IAjaxCallDecorator decorator) {
        this(id, property, stateLocator, AjaxFallbackOrderByLink.DefaultCssProvider.getInstance(),
                decorator);
    }

    /**
     * Constructor
     * 
     * @param id
     * @param property
     * @param stateLocator
     * @param cssProvider
     * @param decorator
     */
    public AdvancedAjaxFallbackOrderByBorder(final String id, final String property,
            final ISortStateLocator stateLocator,
            final AjaxFallbackOrderByLink.ICssProvider cssProvider,
            final IAjaxCallDecorator decorator) {
        super(id);
        AjaxFallbackOrderByLink link = new AjaxFallbackOrderByLink("orderByLink", property,
                stateLocator, cssProvider, decorator) {
            private static final long serialVersionUID = 201111280930L;

            /**
             * {@inheritDoc}
             */
            @Override
            public void onInitialize() {
                super.onInitialize();
                add(new WebMarkupContainer("icon")
                        .add(new AttributeModifier("class", getIconCss())));
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected void onConfigure() {
                super.onConfigure();
                get("icon").add(new AttributeModifier("class", getIconCss()));
            }

            protected String getIconCss() {
                SortOrder sort = stateLocator.getSortState().getPropertySortOrder(property);
                switch (sort) {
                case ASCENDING:
                    return "icon-chevron-down";
                case DESCENDING:
                    return "icon-chevron-up";
                case NONE:
                    return "icon-retweet";
                default:
                    throw new IllegalStateException("Unknown " + SortOrder.class.getSimpleName()
                            + " value '" + sort + "'.");
                }
            }

            @Override
            protected void onSortChanged() {
                AdvancedAjaxFallbackOrderByBorder.this.onSortChanged();
            }

            @Override
            public void onClick(final AjaxRequestTarget target) {
                AdvancedAjaxFallbackOrderByBorder.this.onAjaxClick(target);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected SortOrder nextSortOrder(SortOrder order) {
                switch (order) {
                case ASCENDING:
                    return SortOrder.DESCENDING;
                case DESCENDING:
                    return SortOrder.NONE;
                case NONE:
                    return SortOrder.ASCENDING;
                default:
                    throw new IllegalStateException("Unknown " + SortOrder.class.getSimpleName()
                            + " value '" + order + "'.");
                }
            }

        };
        WebMarkupContainer container = new WebMarkupContainer("container");
        addToBorder(container);
        container.add(getBodyContainer());
        container.add(link);
    }

    /**
     * This method is a hook for subclasses to perform an action after sort has
     * changed.
     */
    protected void onSortChanged() {
        // noop
    }

    protected abstract void onAjaxClick(AjaxRequestTarget target);

}

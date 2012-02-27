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
package fr.peralta.mycellar.interfaces.client.web.components.shared.feedback;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.image.Image;

import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;

/**
 * @author speralta
 */
public class FormComponentFeedbackBorder extends Border {

    private static final long serialVersionUID = 201202220848L;

    /** Visible property cache. */
    private boolean visible;

    /** Are the messages coming from parent ? */
    private final boolean parent;

    /** Filtered components. */
    private final String[] filteredIds;

    /**
     * Error indicator that will be shown whenever there is an error-level
     * message for the collecting component.
     */
    private final class ErrorIndicator extends WebMarkupContainer {

        private static final long serialVersionUID = 201202220848L;

        private static final String ERROR_INDICATOR_IMG_COMPONENT_ID = "errorIndicatorImg";

        /**
         * Construct.
         * 
         * @param id
         *            component id
         */
        public ErrorIndicator(String id) {
            super(id);
            add(new Image(ERROR_INDICATOR_IMG_COMPONENT_ID, ImageReferences.getErrorImage()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onBeforeRender() {
            StringBuilder value = new StringBuilder();
            if (isVisible()) {
                FeedbackMessage message = Session.get().getFeedbackMessages()
                        .messages(getMessagesFilter()).get(0);
                message.markRendered();
                value.append(message.getMessage());
            }
            get(ERROR_INDICATOR_IMG_COMPONENT_ID).add(
                    new AttributeModifier("title", value.toString()));
            super.onBeforeRender();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isVisible() {
            return visible;
        }
    }

    /**
     * Constructor.
     * 
     * @param id
     *            See Component
     */
    public FormComponentFeedbackBorder(final String id) {
        this(id, false);
    }

    /**
     * Constructor.
     * 
     * @param id
     *            See Component
     * @param parent
     *            are the messages coming from parent ?
     */
    public FormComponentFeedbackBorder(final String id, boolean parent, String... filteredIds) {
        super(id);
        this.parent = parent;
        this.filteredIds = filteredIds;
        addToBorder(newErrorIndicator("errorIndicator"));
    }

    /**
     * Update the 'visible' flag to indicate the existence (or lack thereof) of
     * feedback messages.
     */
    @Override
    protected void onBeforeRender() {
        // Get the messages for the current page
        visible = Session.get().getFeedbackMessages().messages(getMessagesFilter()).size() != 0;
        super.onBeforeRender();
    }

    /**
     * @param id
     *            component id
     * @return Let subclass specify some other indicator
     */
    protected WebMarkupContainer newErrorIndicator(String id) {
        return new ErrorIndicator(id);
    }

    /**
     * @return Let subclass specify some other filter
     */
    protected IFeedbackMessageFilter getMessagesFilter() {
        if ((filteredIds != null) && (filteredIds.length > 0)) {
            return new FilteredContainerVisibleFeedbackMessageFilter(parent ? getParent() : this,
                    filteredIds);
        } else {
            return new ContainerFeedbackMessageFilter(parent ? getParent() : this);
        }
    }
}

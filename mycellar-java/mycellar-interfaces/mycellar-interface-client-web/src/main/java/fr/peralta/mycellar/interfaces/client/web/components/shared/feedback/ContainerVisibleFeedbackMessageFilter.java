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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.util.lang.Objects;

import fr.peralta.mycellar.interfaces.client.web.components.shared.SimpleComponent;

/**
 * @author speralta
 */
public class ContainerVisibleFeedbackMessageFilter implements IFeedbackMessageFilter {

    private static final long serialVersionUID = 201202221807L;

    private final MarkupContainer container;

    /**
     * Constructor
     * 
     * @param container
     *            The container that message reporters must be a child of
     */
    public ContainerVisibleFeedbackMessageFilter(MarkupContainer container) {
        if (container == null) {
            throw new IllegalArgumentException("container must be not null");
        }
        this.container = container;
    }

    /**
     * @see org.apache.wicket.feedback.IFeedbackMessageFilter#accept(org.apache.wicket.feedback.FeedbackMessage)
     */
    @Override
    public boolean accept(FeedbackMessage message) {
        if (message.isRendered()) {
            return false;
        }

        final Component reporter = message.getReporter();
        if (reporter instanceof SimpleComponent) {
            return (reporter != null)
                    && ((SimpleComponent<?, ?>) reporter).isContainerVisibleInHierarchy()
                    && (container.contains(reporter, true) || Objects.equal(container, reporter));
        } else {
            return (reporter != null) && reporter.isVisibleInHierarchy()
                    && (container.contains(reporter, true) || Objects.equal(container, reporter));
        }
    }

}

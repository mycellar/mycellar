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
 * 
 */
public class FilteredContainerVisibleFeedbackMessageFilter implements IFeedbackMessageFilter {

    private static final long serialVersionUID = 1L;

    private final MarkupContainer container;

    private final String[] filteredIds;

    /**
     * @param container
     *            The container that message reporters must be a child of
     * @param filteredIds
     *            The containers in container that message reporters must not be
     *            a child of
     */
    public FilteredContainerVisibleFeedbackMessageFilter(MarkupContainer container,
            String... filteredIds) {
        if (container == null) {
            throw new IllegalArgumentException("container must be not null");
        }
        this.container = container;
        this.filteredIds = filteredIds;
    }

    /**
     * @see org.apache.wicket.feedback.IFeedbackMessageFilter#accept(org.apache.wicket.feedback.FeedbackMessage)
     */
    @Override
    public boolean accept(FeedbackMessage message) {
        final Component reporter = message.getReporter();

        boolean result;

        if (reporter instanceof SimpleComponent) {
            result = (reporter != null)
                    && ((SimpleComponent<?>) reporter).isContainerVisibleInHierarchy()
                    && (container.contains(reporter, true) || Objects.equal(container, reporter));
        } else {
            result = (reporter != null) && reporter.isVisibleInHierarchy()
                    && (container.contains(reporter, true) || Objects.equal(container, reporter));
        }

        if (result) {
            for (String filteredId : filteredIds) {
                final MarkupContainer filtered = (MarkupContainer) container.get(filteredId);
                result = result
                        && !((filtered.contains(reporter, true) || Objects
                                .equal(filtered, reporter)));
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }

}

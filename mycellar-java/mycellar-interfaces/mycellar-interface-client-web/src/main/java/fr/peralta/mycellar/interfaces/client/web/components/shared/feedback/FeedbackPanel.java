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

import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * @author speralta
 */
public class FeedbackPanel extends org.apache.wicket.markup.html.panel.FeedbackPanel {

    private static final long serialVersionUID = 201202161735L;

    /**
     * @param id
     * @param filter
     */
    public FeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    /**
     * @param id
     */
    public FeedbackPanel(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return anyMessage();
    }

}

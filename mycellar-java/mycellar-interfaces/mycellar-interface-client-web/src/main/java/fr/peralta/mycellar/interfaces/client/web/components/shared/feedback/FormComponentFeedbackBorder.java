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

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author speralta
 */
public class FormComponentFeedbackBorder extends Border implements IFeedback {

    private static final long serialVersionUID = 201202220848L;

    /** Are the messages coming from parent ? */
    private final boolean parent;

    /** Filtered components. */
    private final String[] filteredIds;

    /**
     * Use id for label and form component id.
     * 
     * @param id
     *            See Component
     */
    public FormComponentFeedbackBorder(final String id) {
        this(id, new StringResourceModel(id, null), id);
    }

    /**
     * Constructor.
     * 
     * @param id
     *            See Component
     * @param label
     *            the label
     * @param forId
     *            id of the form component
     */
    public FormComponentFeedbackBorder(final String id, IModel<String> label, String forId) {
        this(id, label, forId, false);
    }

    /**
     * Constructor.
     * 
     * @param id
     *            See Component
     * @param label
     *            the label
     * @param forId
     *            id of the form component
     * @param parent
     *            are the messages coming from parent ?
     */
    public FormComponentFeedbackBorder(final String id, IModel<String> label, String forId,
            boolean parent, String... filteredIds) {
        super(id);
        this.parent = parent;
        this.filteredIds = filteredIds;
        addToBorder(new Label("label", label).add(new AttributeModifier("for", forId)));
        addToBorder(new Label("errorMsg"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBeforeRender() {
        Label errorMsg = (Label) get("errorMsg");
        List<FeedbackMessage> messages = Session.get().getFeedbackMessages()
                .messages(getMessagesFilter());
        FeedbackMessage messageToRender = null;
        for (FeedbackMessage message : messages) {
            if ((messageToRender == null) || (message.getLevel() > messageToRender.getLevel())) {
                messageToRender = message;
            }
        }
        if ((messageToRender != null) && (messageToRender.getMessage() != null)) {
            messageToRender.markRendered();
            errorMsg.setDefaultModel(new Model<String>(messageToRender.getMessage().toString()));
            errorMsg.setVisibilityAllowed(true);
            add(new AttributeModifier("class", "control-group "
                    + getLevel(messageToRender.getLevel())));
        } else {
            add(new AttributeModifier("class", "control-group"));
            errorMsg.setVisibilityAllowed(false);
        }
        super.onBeforeRender();
    }

    /**
     * @param level
     * @return
     */
    private String getLevel(int level) {
        if (level < FeedbackMessage.WARNING) {
            return "";
        } else if (level < FeedbackMessage.ERROR) {
            return "warning";
        } else {
            return "error";
        }
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

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
package fr.peralta.mycellar.interfaces.client.web.pages.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class ResetPasswordRequestPage extends HomeSuperPage {

    /**
     * @author speralta
     */
    private static class SubmitButton extends AjaxButton {
        private static final long serialVersionUID = 201206041553L;

        @SpringBean
        private UserServiceFacade userServiceFacade;

        /**
         * @param id
         */
        private SubmitButton(String id) {
            super(id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            String email = (String) form.getModelObject();
            userServiceFacade.resetPasswordRequest(email, RequestCycle.get().getUrlRenderer()
                    .renderFullUrl(Url.parse(urlFor(ResetPasswordPage.class, null).toString())));
            send(getParent(), Broadcast.BUBBLE, Action.SAVE);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
        }
    }

    /**
     * @author speralta
     */
    public class ResetPasswordRequestForm extends Form<String> {
        private static final long serialVersionUID = 201206041443L;

        /**
         * @param id
         */
        public ResetPasswordRequestForm(String id) {
            super(id, new Model<String>());
            setStatelessHint(true);
            add(new FormComponentFeedbackBorder("email")
                    .add(new EmailTextField("email", getModel()).setRequired(true)));
            add(new SubmitButton("submit"));
        }

    }

    private static final long serialVersionUID = 201117181723L;
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordRequestPage.class);

    private final ResetPasswordRequestForm form;
    private final WebMarkupContainer result;

    /**
     * @param parameters
     */
    public ResetPasswordRequestPage(PageParameters parameters) {
        super(parameters);
        hideLoginBarPanel();
        add((form = new ResetPasswordRequestForm("form")).setOutputMarkupId(true));
        add((result = new WebMarkupContainer("result")).setVisibilityAllowed(false)
                .setOutputMarkupPlaceholderTag(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SAVE:
                form.setVisibilityAllowed(false);
                result.setVisibilityAllowed(true);
                AjaxTool.ajaxReRender(form, result);
                event.stop();
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}

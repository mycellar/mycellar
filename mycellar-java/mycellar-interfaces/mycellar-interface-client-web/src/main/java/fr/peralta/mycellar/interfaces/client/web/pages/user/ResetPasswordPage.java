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

import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.ResetPasswordRequest;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.user.form.ChangePasswordForm;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.FormValidationHelper;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class ResetPasswordPage extends HomeSuperPage {

    private static final long serialVersionUID = 201117181723L;
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordPage.class);

    private static final String KEY_PARAMETER = "key";

    @SpringBean
    private UserServiceFacade userServiceFacade;

    private final ChangePasswordForm form;

    private final IModel<String> newPassword;

    /**
     * @param parameters
     */
    public ResetPasswordPage(PageParameters parameters) {
        super(parameters);
        setStatelessHint(true);
        hideLoginBarPanel();
        StringValue parameter = parameters.get(KEY_PARAMETER);
        String email = "";
        if ((parameter != null) && !parameter.isEmpty()) {
            ResetPasswordRequest request = userServiceFacade.getResetPasswordRequestByKey(parameter
                    .toString());
            if (request != null) {
                setDefaultModel(new Model<ResetPasswordRequest>(request));
                email = request.getUser().getEmail();
            }
        }
        newPassword = new Model<String>();
        add((form = new ChangePasswordForm("form", newPassword))
                .setVisibilityAllowed(getDefaultModelObject() != null));
        add(new Label("email", email).setVisibilityAllowed(getDefaultModelObject() != null));
        add(new WebMarkupContainer("error").add(
                new BookmarkablePageLink<Void>("goToResetRequest", ResetPasswordRequestPage.class))
                .setVisibilityAllowed(getDefaultModelObject() == null));
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
                if (event.getSource() == form) {
                    try {
                        ResetPasswordRequest request = (ResetPasswordRequest) getDefaultModelObject();
                        userServiceFacade.saveUserPassword(request.getUser(),
                                newPassword.getObject());
                        // remove reset request
                        userServiceFacade.deleteAllResetPasswordRequestsForUser(userServiceFacade
                                .getUserById(request.getUser().getId()));
                        setResponsePage(LoginPage.class);
                    } catch (BusinessException e) {
                        FormValidationHelper.error(form, e);
                    }
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}

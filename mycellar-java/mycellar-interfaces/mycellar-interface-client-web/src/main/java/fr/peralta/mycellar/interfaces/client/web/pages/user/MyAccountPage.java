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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.checks.ClassSecurityCheck;
import org.wicketstuff.security.components.SecureComponentHelper;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.user.form.ChangePasswordForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AdminPage;
import fr.peralta.mycellar.interfaces.client.web.pages.booking.BookingsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.client.web.shared.FormValidationHelper;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class MyAccountPage extends HomeSuperPage {

    private static final long serialVersionUID = 201203262223L;
    private static final Logger logger = LoggerFactory.getLogger(MyAccountPage.class);

    @SpringBean
    private UserServiceFacade userServiceFacade;

    private final ActionLink changePassword;
    private final ChangePasswordForm changePasswordForm;
    private final Label passwordChanged;

    private IModel<String> newPassword;

    /**
     * @param parameters
     */
    public MyAccountPage(PageParameters parameters) {
        super(parameters);
        User user = UserKey.getUserLoggedIn();
        add(new Label("noProfile", new StringResourceModel("noProfile", null))
                .setVisibilityAllowed(user.getProfile() == null));
        add(new Label("name", user.getLastname() + " " + user.getFirstname()));
        add(new Label("email", user.getEmail()).setRenderBodyOnly(true));
        add(changePassword = new ActionLink("changePasswordLink", Action.ADD));
        add(changePasswordForm = new ChangePasswordForm("changePasswordForm",
                newPassword = new Model<String>()));
        changePasswordForm.setVisibilityAllowed(false).setOutputMarkupPlaceholderTag(true);
        add((passwordChanged = new Label("passwordChanged", new StringResourceModel(
                "passwordChanged", null))).setVisibilityAllowed(false));
        WebMarkupContainer notValidBadge = new WebMarkupContainer("notValidBadge");
        notValidBadge.setVisibilityAllowed(user.getProfile() == null);
        add(notValidBadge);
        WebMarkupContainer simpleBadge = new WebMarkupContainer("simpleBadge");
        simpleBadge.setVisibilityAllowed(user.getProfile() != null);
        add(simpleBadge);
        WebMarkupContainer shoppingBadge = new WebMarkupContainer("shoppingBadge");
        SecureComponentHelper.setSecurityCheck(shoppingBadge, new ClassSecurityCheck(
                BookingsPage.class));
        add(shoppingBadge);
        WebMarkupContainer cellarBadge = new WebMarkupContainer("cellarBadge");
        SecureComponentHelper.setSecurityCheck(cellarBadge, new ClassSecurityCheck(
                CellarsPage.class));
        add(cellarBadge);
        WebMarkupContainer adminBadge = new WebMarkupContainer("adminBadge");
        SecureComponentHelper.setSecurityCheck(adminBadge, new ClassSecurityCheck(AdminPage.class));
        add(adminBadge);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachModels() {
        newPassword.detach();
        super.detachModels();
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
            case ADD:
                if (event.getSource() == changePassword) {
                    changePasswordForm.setVisibilityAllowed(true);
                    changePassword.setVisibilityAllowed(false);
                    AjaxTool.ajaxReRender(changePasswordForm, changePassword);
                    event.stop();
                }
                break;
            case SAVE:
                if (event.getSource() == changePasswordForm) {
                    try {
                        User user = UserKey.getUserLoggedIn();
                        userServiceFacade.saveUserPassword(user, newPassword.getObject());
                        // update user
                        UserKey.userLogsIn(userServiceFacade.getUserById(user.getId()));
                        changePasswordForm.setVisibilityAllowed(false);
                        passwordChanged.setVisibilityAllowed(true);
                    } catch (BusinessException e) {
                        FormValidationHelper.error(changePasswordForm, e);
                    }
                    AjaxTool.ajaxReRender(this);
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

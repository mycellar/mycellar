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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.user.edit.UserEditPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.HomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class EditUserPage extends HomeSuperPage {

    private static final long serialVersionUID = 201117181723L;

    private static final Logger logger = LoggerFactory.getLogger(EditUserPage.class);

    private static final String USER_ID_PARAMETER = "user";

    /**
     * @param userLoggedIn
     * @return
     */
    public static PageParameters getPageParameters(User userLoggedIn) {
        return new PageParameters().add(USER_ID_PARAMETER, userLoggedIn.getId());
    }

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param parameters
     */
    public EditUserPage(PageParameters parameters) {
        super(parameters);
        ObjectForm<User> objectForm = new ObjectForm<User>("form", getUser(parameters));
        objectForm.replace(new UserEditPanel(ObjectForm.EDIT_PANEL_COMPONENT_ID));
        add(objectForm);
    }

    private User getUser(PageParameters pageParameters) {
        User user = null;
        StringValue userIdParameter = pageParameters.get(USER_ID_PARAMETER);
        if ((userIdParameter != null) && !userIdParameter.isEmpty()) {
            user = userServiceFacade.getUserById(userIdParameter.toInteger());
        }
        if (user == null) {
            setResponsePage(HomePage.class);
        } else {

        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SAVE:
                User user = getUserFromSource(event.getSource());
                userServiceFacade.saveUser(user);
                setResponsePage(HomePage.class);
                break;
            case CANCEL:
                setResponsePage(HomePage.class);
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

    /**
     * @param source
     * @return
     */
    @SuppressWarnings("unchecked")
    private User getUserFromSource(IEventSource source) {
        if (source instanceof ObjectForm) {
            return ((ObjectForm<User>) source).getModelObject();
        } else {
            throw new WicketRuntimeException("Source is not an ObjectForm.");
        }
    }
}

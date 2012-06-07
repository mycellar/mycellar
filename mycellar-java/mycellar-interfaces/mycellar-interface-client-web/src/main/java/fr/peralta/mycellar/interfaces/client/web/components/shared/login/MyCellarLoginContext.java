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
package fr.peralta.mycellar.interfaces.client.web.components.shared.login;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.authentication.DefaultSubject;
import org.wicketstuff.security.hive.authentication.Subject;
import org.wicketstuff.security.hive.authentication.UsernamePasswordContext;

import fr.peralta.mycellar.domain.user.ProfileEnum;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.security.MyCellarPrincipal;
import fr.peralta.mycellar.interfaces.client.web.security.PrincipalNameEnum;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class MyCellarLoginContext extends UsernamePasswordContext {

    private static final Logger logger = LoggerFactory.getLogger(MyCellarLoginContext.class);

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param login
     * @param password
     */
    public MyCellarLoginContext(String login, String password) {
        super(login, password);
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Subject getSubject(String username, String password) throws LoginException {
        logger.debug("Attempt to log in {}", username);
        User user = userServiceFacade.authenticateUser(username, password);
        if (user != null) {
            logger.info("{} logs in.", user.getEmail());
            UserKey.userLogsIn(user);
            DefaultSubject subject = new DefaultSubject();
            if (user.getProfile() != null) {
                switch (user.getProfile()) {
                case ADMIN:
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.ADMIN));
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.CELLAR));
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.BOOKING));
                    break;
                case MYCELLAR:
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.CELLAR));
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.BOOKING));
                    break;
                case BOOKING:
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.BOOKING));
                    break;
                case CELLAR:
                    subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.CELLAR));
                    break;
                case BASIC:
                    break;
                default:
                    throw new IllegalStateException("Unknown " + ProfileEnum.class.getSimpleName()
                            + " value " + user.getProfile() + ".");
                }
            }
            subject.addPrincipal(new MyCellarPrincipal(PrincipalNameEnum.BASIC));
            return subject;
        } else {
            throw new LoginException("Failed to authenticate user.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyLogoff(Subject subject) {
        User user = UserKey.getUserLoggedIn();
        if (user != null) {
            logger.info("{} logs out.", user.getEmail());
            UserKey.userLogsOut();
        }
    }

}

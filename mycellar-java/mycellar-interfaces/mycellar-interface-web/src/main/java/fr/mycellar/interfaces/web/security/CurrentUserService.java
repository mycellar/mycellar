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
package fr.mycellar.interfaces.web.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.mycellar.domain.user.User;
import fr.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
@Named
@Singleton
public final class CurrentUserService {

    private UserServiceFacade userServiceFacade;

    /**
     * @return
     */
    public User getCurrentUser() {
        User user = null;
        String email = getCurrentUserEmail();
        if (StringUtils.isNotBlank(email)) {
            user = userServiceFacade.getUserByEmail(email);
        }
        return user;
    }

    /**
     * @return
     */
    public String getCurrentUserEmail() {
        String email = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if ((auth != null) && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                email = auth.getName();
            }
        }
        return email;
    }

    public boolean isCurrentUser(User user) {
        String email = getCurrentUserEmail();
        return (email != null) && (user != null) && email.equals(user.getEmail());
    }

    /**
     * @param userServiceFacade
     *            the userServiceFacade to set
     */
    @Inject
    public void setUserServiceFacade(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

}

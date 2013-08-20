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
package fr.mycellar.interfaces.web.services.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/")
public class SecurityWebService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityWebService.class);

    private AuthenticationManager authenticationManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("current-user")
    public UserDto getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if ((auth != null) && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                UserDto user = new UserDto();
                user.setEmail(auth.getName());
                return user;
            }
        }

        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public UserDto login(UserDto userDto) throws BusinessException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(authRequest);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            logger.debug("Authentication failed.", e);
            throw new BusinessException(BusinessError.OTHER_00002, e);
        }
        if ((auth != null) && auth.isAuthenticated()) {
            logger.debug("Authentication success: {}", auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDto user = new UserDto();
            user.setEmail(auth.getName());
            return user;
        }
        SecurityContextHolder.clearContext();
        throw new BusinessException(BusinessError.OTHER_00002);
    }

    @POST
    @Path("logout")
    public void logout(@Context HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);

        SecurityContextHolder.clearContext();
    }

    /**
     * @param authenticationManager
     *            the authenticationManager to set
     */
    @Inject
    @Named("myCellarAuthenticationManager")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}

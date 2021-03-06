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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.mycellar.configuration.SpringSecurityConfiguration;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.ProfileEnum;
import fr.mycellar.domain.user.User;
import fr.mycellar.interfaces.facades.user.UserServiceFacade;
import fr.mycellar.interfaces.web.security.CurrentUserService;
import fr.mycellar.interfaces.web.security.SecurityContextTokenRepository;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/")
public class SecurityWebService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityWebService.class);

    private AuthenticationManager authenticationManager;

    private SecurityContextTokenRepository securityContextTokenRepository;

    private CurrentUserService currentUserService;

    private UserServiceFacade userServiceFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("requestedMail")
    public String getMailFromRequestKey(@QueryParam("key") String key) throws BusinessException {
        return userServiceFacade.getEmailFromResetPasswordRequestByKey(key);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("sendPasswordResetMail")
    public void sendPasswordResetMail(String email, @Context HttpServletRequest httpServletRequest) {
        userServiceFacade.resetPasswordRequest(email, httpServletRequest.getServletContext().getRealPath("reset-password"));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("resetPassword")
    public UserDto resetPassword(ResetPasswordDto resetPasswordDto, @Context HttpServletResponse response) throws BusinessException {
        User user = userServiceFacade.resetPassword(resetPasswordDto.getKey(), resetPasswordDto.getPassword());
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(resetPasswordDto.getPassword());
        return login(userDto, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changePassword")
    public UserDto changePassword(ChangePasswordDto changePasswordDto, @Context HttpServletResponse response) throws BusinessException {
        User currentUser = currentUserService.getCurrentUser();
        User user = userServiceFacade.authenticateUser(currentUser.getEmail(), changePasswordDto.getOldPassword());
        userServiceFacade.saveUserPassword(user, changePasswordDto.getPassword());

        SecurityContextHolder.clearContext();

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(changePasswordDto.getPassword());
        return login(userDto, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changeEmail")
    public UserDto changeEmail(ChangeEmailDto changeEmailDto, @Context HttpServletResponse response) throws BusinessException {
        User currentUser = currentUserService.getCurrentUser();
        User user = userServiceFacade.authenticateUser(currentUser.getEmail(), changeEmailDto.getPassword());
        user.setEmail(changeEmailDto.getEmail());
        userServiceFacade.saveUserPassword(user, changeEmailDto.getPassword());

        SecurityContextHolder.clearContext();

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(changeEmailDto.getPassword());
        return login(userDto, response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("current-user")
    public UserDto getCurrentUser() {
        User user = currentUserService.getCurrentUser();
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getLastname() + " " + user.getFirstname());
            if (user.getProfile() != null) {
                userDto.setProfile(user.getProfile().toString());
            }
            return userDto;
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public UserDto login(UserDto userDto, @Context HttpServletResponse response) throws BusinessException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authRequest);
        logger.debug("Authentication success: {}", auth);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
        response.setHeader(SpringSecurityConfiguration.TOKEN_HEADER_NAME, securityContextTokenRepository.newToken(context).getKey());
        return getCurrentUser();
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("register")
    public UserDto register(User userToRegister, @Context HttpServletResponse response) throws BusinessException {
        User user = new User();
        user.setEmail(userToRegister.getEmail());
        user.setFirstname(userToRegister.getFirstname());
        user.setLastname(userToRegister.getLastname());
        user.setProfile(ProfileEnum.BOOKING);

        userServiceFacade.saveUserPassword(user, userToRegister.getPassword());

        UserDto userDto = new UserDto();
        userDto.setEmail(userToRegister.getEmail());
        userDto.setPassword(userToRegister.getPassword());
        return login(userDto, response);
    }

    @Inject
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Inject
    public void setSecurityContextTokenRepository(SecurityContextTokenRepository securityContextTokenRepository) {
        this.securityContextTokenRepository = securityContextTokenRepository;
    }

    @Inject
    public void setCurrentUserService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Inject
    public void setUserServiceFacade(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

}

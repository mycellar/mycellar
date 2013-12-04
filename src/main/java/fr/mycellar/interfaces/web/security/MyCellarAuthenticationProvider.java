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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.mycellar.domain.user.ProfileEnum;
import fr.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class MyCellarAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyCellarAuthenticationProvider.class);

    private UserServiceFacade userServiceFacade;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        fr.mycellar.domain.user.User user = userServiceFacade.authenticateUser(userDetails.getUsername(), (String) authentication.getCredentials());
        if (user == null) {
            throw new BadCredentialsException("Bad credentials for username '" + userDetails.getUsername() + "'.");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username is empty.");
        }
        logger.debug("Security verification for username '{}'.", username);

        fr.mycellar.domain.user.User user = userServiceFacade.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username '" + username + "' not found.");
        }
        return new User(user.getEmail(), user.getPassword(), getAuthoritiesFromProfile(user.getProfile()));
    }

    private List<GrantedAuthority> getAuthoritiesFromProfile(ProfileEnum profile) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (profile) {
        case ADMIN:
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_MONITORING"));
            authorities.add(new SimpleGrantedAuthority("ROLE_CELLAR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_BOOKING"));
            break;
        case MYCELLAR:
            authorities.add(new SimpleGrantedAuthority("ROLE_CELLAR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_BOOKING"));
            break;
        case BOOKING:
            authorities.add(new SimpleGrantedAuthority("ROLE_BOOKING"));
            break;
        case CELLAR:
            authorities.add(new SimpleGrantedAuthority("ROLE_CELLAR"));
            break;
        case BASIC:
            break;
        default:
            throw new IllegalStateException("Unknown " + ProfileEnum.class.getSimpleName() + " value '" + profile + "'.");
        }
        return authorities;
    }

    @Inject
    public void setUserServiceFacade(UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

}

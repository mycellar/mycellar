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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import fr.mycellar.configuration.SpringSecurityConfiguration;

/**
 * @author speralta
 */
@Singleton
@Named
public class SecurityContextTokenRepository implements SecurityContextRepository {

    // TODO purge old tokens
    private final Map<Token, SecurityContext> securityContexts = new HashMap<Token, SecurityContext>();

    @Inject
    private KeyBasedPersistenceTokenService keyBasedPersistenceTokenService;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        Object key = requestResponseHolder.getRequest().getHeader(SpringSecurityConfiguration.TOKEN_HEADER_NAME);
        if ((key != null) && (key instanceof String)) {
            try {
                Token token = keyBasedPersistenceTokenService.verifyToken((String) key);
                if (token != null) {
                    SecurityContext context = securityContexts.get(token);
                    if (context != null) {
                        return context;
                    }
                }
            } catch (Exception e) {

            }
        }
        return SecurityContextHolder.createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        Object key = response.getHeader(SpringSecurityConfiguration.TOKEN_HEADER_NAME);
        if ((key != null) && (key instanceof String)) {
            securityContexts.put(keyBasedPersistenceTokenService.verifyToken((String) key), context);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        Object key = request.getHeader(SpringSecurityConfiguration.TOKEN_HEADER_NAME);
        if ((key != null) && (key instanceof String)) {
            try {
                return keyBasedPersistenceTokenService.verifyToken((String) key) != null;
            } catch (Exception e) {
                // return false;
            }
        }
        return false;
    }

    public Token newToken(SecurityContext context) {
        Authentication auth = context.getAuthentication();
        if ((auth != null) && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return keyBasedPersistenceTokenService.allocateToken(context.getAuthentication().getName());
        }
        return null;
    }

    public void deleteToken(HttpServletRequest request) {
        securityContexts.remove(request.getHeader(SpringSecurityConfiguration.TOKEN_HEADER_NAME));
    }

}

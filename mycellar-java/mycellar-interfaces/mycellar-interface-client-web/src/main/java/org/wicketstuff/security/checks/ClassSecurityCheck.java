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
package org.wicketstuff.security.checks;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.security.WaspApplication;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Default instantiation check for any type of class. This is used by
 * {@link ClassAuthorizationStrategy} to test for instantiation rights. But you
 * can use it yourself to for any kind of action. Note that errorpages should
 * not be outfitted with a securitycheck such as this that checks for
 * instantiation.
 * 
 * HACK : do check event if user is not authenticated; if check failed and if
 * user is not authenticated, redirect to login page else go to access denied
 * page.
 * 
 * @author marrink
 * @author speralta
 * @see ClassAuthorizationStrategy
 */
public class ClassSecurityCheck extends AbstractSecurityCheck {
    private static final long serialVersionUID = 1L;

    private final Class<?> clazz;

    /**
     * Constructs a new securitycheck for a class
     * 
     * @param clazz
     *            the class to use in the check
     * @throws IllegalArgumentException
     *             if the clazz is null
     */
    public ClassSecurityCheck(Class<?> clazz) {
        this.clazz = clazz;
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }
    }

    /**
     * The class to check against.
     * 
     * @return Returns the class.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Checks if the user is authorized for the action. special permission is
     * given to the loginpage, which is always authorized. If the user is not
     * authenticated he is redirected to the login page. Redirects the
     * authorization check to the strategy if the user is authenticated.
     * 
     * @return true if the user is authenticated and authorized, false
     *         otherwise.
     * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
     * @see WaspApplication#getLoginPage()
     * @see WaspAuthorizationStrategy#isClassAuthorized(Class, WaspAction)
     * @throws RestartResponseAtInterceptPageException
     *             if the user is not authenticated.
     */
    @Override
    public boolean isActionAuthorized(WaspAction action) {
        if (getClazz() == getLoginPage()) {
            return true;
        }
        boolean checkResult = getStrategy().isClassAuthorized(getClazz(), action);
        if (!checkResult && !isAuthenticated()) {
            throw new RestartResponseAtInterceptPageException(getLoginPage());
        }
        return checkResult;
    }

    /**
     * Redirects to the
     * {@link WaspAuthorizationStrategy#isClassAuthenticated(Class)}.
     * 
     * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
     */
    @Override
    public boolean isAuthenticated() {
        return getStrategy().isClassAuthenticated(getClazz());
    }
}

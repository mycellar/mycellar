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

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Basic security check for components. Tries to authorize the component and
 * optionally its {@link ISecureModel} if it exists. Note that this check always
 * authenticates the user, this is only to make it easier to put a secure
 * component on a non secure page and have it redirect to the login. Usually the
 * secure page will have checked authentication already. Both
 * {@link ISecureModel} and this check need to authenticate / authorize the user
 * before an approval is given.
 * 
 * HACK : do check event if user is not authenticated; if check failed and if
 * user is not authenticated, redirect to login page else go to access denied
 * page.
 * 
 * @author speralta
 * @author marrink
 */
public class ComponentSecurityCheck extends AbstractSecurityCheck {

    private static final long serialVersionUID = 1L;

    private final Component component;

    private final boolean checkModel;

    /**
     * Constructs a ComponentSecurityCheck that never checks the model. Note
     * that the check still needs to be manually added to the component.
     * 
     * @param component
     *            the target component for this security check.
     * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
     * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
     */
    public ComponentSecurityCheck(Component component) {
        this(component, false);
    }

    /**
     * Constructs a ComponentSecurityCheck that optionally checks the model.
     * Note that the check still needs to be manually added to the component.
     * 
     * @param component
     *            the target component for this security check.
     * @param checkSecureModelIfExists
     *            forces the model to be checked after this check is fired
     * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
     * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
     */
    public ComponentSecurityCheck(Component component, boolean checkSecureModelIfExists) {
        super();
        checkModel = checkSecureModelIfExists;
        if (component == null) {
            throw new IllegalArgumentException("component must be specified.");
        }
        this.component = component;
    }

    /**
     * Checks if the user is authenticated for this component. if the model is
     * also checked both the model and the component need to be authenticated
     * before we return true.
     * 
     * @see ISecurityCheck#isAuthenticated()
     * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
     */
    @Override
    public boolean isAuthenticated() {
        boolean result = getStrategy().isComponentAuthenticated(getComponent());
        if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent())) {
            return ((ISecureModel<?>) getComponent().getDefaultModel())
                    .isAuthenticated(getComponent());
        }
        return result;
    }

    /**
     * Returns the target component for this securitycheck.
     * 
     * @return the component
     */
    protected final Component getComponent() {
        return component;
    }

    /**
     * Checks if the user is authorized for this component. if the model is also
     * checked both the model and the component need to be authorized before we
     * return true.
     * 
     * @return true if the component (and optionally the model) are authorized,
     *         false otherwise.
     * @see ISecurityCheck#isActionAuthorized(WaspAction)
     * @see WaspAuthorizationStrategy#isComponentAuthorized(Component,
     *      WaspAction)
     * @see WaspAuthorizationStrategy#isModelAuthorized(ISecureModel, Component,
     *      WaspAction)
     */
    @Override
    public boolean isActionAuthorized(WaspAction action) {
        boolean result = getStrategy().isComponentAuthorized(getComponent(), action);
        if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent())) {
            result = ((ISecureModel<?>) getComponent().getDefaultModel()).isAuthorized(
                    getComponent(), action);
        }
        if (!result && !isAuthenticated()) {
            throw new RestartResponseAtInterceptPageException(getLoginPage());
        }
        return result;
    }

    /**
     * Flags if we need to check the {@link ISecureModel} of a component if it
     * exists at all.
     * 
     * @return true if we must check the model, false otherwise.
     */
    protected final boolean checkSecureModel() {
        return checkModel;
    }

}

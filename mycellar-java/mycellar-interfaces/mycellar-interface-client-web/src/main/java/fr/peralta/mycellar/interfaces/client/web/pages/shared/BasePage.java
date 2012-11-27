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
package fr.peralta.mycellar.interfaces.client.web.pages.shared;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.security.components.SecureWebPage;

import fr.peralta.mycellar.interfaces.client.web.components.shared.login.LoginBarPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavPanel;
import fr.peralta.mycellar.interfaces.client.web.descriptors.DescriptorServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.resources.css.CssReferences;
import fr.peralta.mycellar.interfaces.client.web.resources.js.JavaScriptReferences;

/**
 * @author speralta
 */
public abstract class BasePage extends SecureWebPage {

    private static final long serialVersionUID = 201117181723L;

    @SpringBean
    private DescriptorServiceFacade descriptorServiceFacade;

    private final LoginBarPanel loginBarPanel;
    private final WebMarkupContainer loginCollapseButton;

    /**
     * @param parameters
     */
    public BasePage(PageParameters parameters) {
        super(parameters);
        add(new BookmarkablePageLink<Void>("homeLink", WebApplication.get().getHomePage()));
        add(new NavPanel("menu", getMenuClass(), descriptorServiceFacade.getMenuPages(), true));
        add(loginBarPanel = new LoginBarPanel("loginBarPanel"));
        add(loginCollapseButton = new WebMarkupContainer("loginCollapseButton"));
        add(new DebugBar("debug"));
    }

    public BasePage hideLoginBarPanel() {
        loginBarPanel.setVisibilityAllowed(false);
        loginCollapseButton.setVisibilityAllowed(false);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(CssReferences.getBootstrapCss()));
        response.render(CssHeaderItem.forReference(CssReferences.getBootstrapDatePickerCss()));
        response.render(CssHeaderItem.forReference(CssReferences.getMasterCss()));
        response.render(CssHeaderItem.forReference(CssReferences.getBootstrapResponsiveCss()));
        response.render(CssHeaderItem.forReference(CssReferences.getMasterResponsiveCss()));
        response.render(JavaScriptHeaderItem.forReference(JavaScriptReferences.getBootstrapJs()));
        response.render(JavaScriptHeaderItem.forReference(JavaScriptReferences
                .getBootstrapDatePickerJs()));
        response.render(JavaScriptHeaderItem.forReference(JavaScriptReferences.getMasterJs()));
    }

    protected Class<? extends BasePage> getMenuClass() {
        return this.getClass();
    }

    /**
     * @return the descriptorServiceFacade
     */
    protected final DescriptorServiceFacade getDescriptorServiceFacade() {
        return descriptorServiceFacade;
    }

}

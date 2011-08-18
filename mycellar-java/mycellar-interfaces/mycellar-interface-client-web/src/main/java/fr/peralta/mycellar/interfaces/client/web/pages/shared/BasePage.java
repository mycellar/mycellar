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
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import fr.peralta.mycellar.interfaces.client.web.MyCellarWebApplicationDescriptor;
import fr.peralta.mycellar.interfaces.client.web.components.shared.menu.MenuPanel;

/**
 * @author speralta
 */
public abstract class BasePage extends WebPage {

    private static final long serialVersionUID = 201117181723L;

    /**
     * @param parameters
     */
    public BasePage(PageParameters parameters) {
        super(parameters);
        add(new MenuPanel("menu", getMenuClass(), MyCellarWebApplicationDescriptor.get()
                .getMenuablePageDescriptors()));
        add(new DebugBar("debug"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new PackageResourceReference(BasePage.class, "master.css"));
    }

    /**
     * @return the class of the menu page (for selecting it in {@link MenuPanel}
     */
    protected abstract Class<? extends BasePage> getMenuClass();

}

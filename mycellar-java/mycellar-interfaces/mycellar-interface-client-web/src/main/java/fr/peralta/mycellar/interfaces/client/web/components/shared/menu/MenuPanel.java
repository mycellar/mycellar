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
package fr.peralta.mycellar.interfaces.client.web.components.shared.menu;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.odlabs.wiquery.core.IWiQueryPlugin;
import org.odlabs.wiquery.core.javascript.JsQuery;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.SecureComponentHelper;

import fr.peralta.mycellar.interfaces.client.web.pages.shared.BasePage;

/**
 * @author speralta
 */
public class MenuPanel extends Panel implements IWiQueryPlugin {

    private static final long serialVersionUID = 201011122248L;

    /**
     * @param id
     * @param current
     * @param subCurrent
     * @param descriptors
     */
    public MenuPanel(String id, final Class<? extends BasePage> current,
            final Class<? extends BasePage> subCurrent, List<MenuablePageDescriptor> descriptors) {
        super(id);
        add(new ListView<MenuablePageDescriptor>("menuEntry", descriptors) {
            private static final long serialVersionUID = 201011122248L;

            @Override
            protected void populateItem(final ListItem<MenuablePageDescriptor> listItem) {
                final SubMenuPanel subMenu = new SubMenuPanel("subMenu", subCurrent, listItem
                        .getModelObject().getSubPages());
                listItem.add(subMenu);
                final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link",
                        listItem.getModelObject().getPageClass());
                if (subMenu.isVisibilityAllowed()) {
                    link.add(new AttributeModifier("class", "dropdown-toggle"));
                }
                SecureComponentHelper.setSecurityCheck(link, new LinkSecurityCheck(link, listItem
                        .getModelObject().getPageClass()));
                if (current.equals(listItem.getModelObject().getSuperPageClass())) {
                    listItem.add(new AttributeModifier("class", "selected"));
                }
                link.add(new Label("label", listItem.getModelObject().getPageTitle()));
                listItem.add(link);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsStatement statement() {
        return new JsQuery(this).$().chain("dropdown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderJavaScriptReference(MenuPanelJavaScriptResourceReference.get());
    }

}

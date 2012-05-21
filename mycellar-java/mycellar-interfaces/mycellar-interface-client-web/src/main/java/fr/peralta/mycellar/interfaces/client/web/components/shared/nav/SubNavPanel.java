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
package fr.peralta.mycellar.interfaces.client.web.components.shared.nav;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.SecureComponentHelper;

import fr.peralta.mycellar.interfaces.client.web.pages.shared.BasePage;

/**
 * @author speralta
 */
public class SubNavPanel extends Panel {

    private static final long serialVersionUID = 201110130735L;

    /**
     * @param id
     * @param current
     * @param descriptors
     */
    public SubNavPanel(String id, final Class<? extends BasePage> current,
            List<NavPageDescriptor> descriptors) {
        super(id);
        add(new ListView<NavPageDescriptor>("subNavEntry", descriptors) {
            private static final long serialVersionUID = 201011122248L;

            @Override
            protected void populateItem(final ListItem<NavPageDescriptor> listItem) {
                final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link",
                        listItem.getModelObject().getPageClass());
                SecureComponentHelper.setSecurityCheck(link, new LinkSecurityCheck(link, listItem
                        .getModelObject().getPageClass()));
                if (current.equals(listItem.getModelObject().getSuperPageClass())) {
                    listItem.add(new AttributeAppender("class", "active").setSeparator(" "));
                }
                link.add(new Label("label", new StringResourceModel(listItem.getModelObject()
                        .getPageTitleKey(), this, null)));
                listItem.add(link);
            }
        });
    }
}

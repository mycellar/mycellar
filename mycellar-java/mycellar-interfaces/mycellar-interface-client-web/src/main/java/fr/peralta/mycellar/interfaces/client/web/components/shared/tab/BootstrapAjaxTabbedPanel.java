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
package fr.peralta.mycellar.interfaces.client.web.components.shared.tab;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.list.LoopItem;

/**
 * @author speralta
 */
public class BootstrapAjaxTabbedPanel<T extends ITab> extends AjaxTabbedPanel<T> {

    private static final long serialVersionUID = 201203021702L;

    /**
     * @param id
     * @param tabs
     */
    public BootstrapAjaxTabbedPanel(String id, List<T> tabs) {
        super(id, tabs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LoopItem newTabContainer(final int tabIndex) {
        return new LoopItem(tabIndex) {
            private static final long serialVersionUID = 201203021713L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                String cssClass = tag.getAttribute("class");
                if (cssClass == null) {
                    cssClass = "";
                }
                if (getIndex() == getSelectedTab()) {
                    cssClass += " active";
                }
                tag.put("class", cssClass.trim());
            }

            @Override
            public boolean isVisible() {
                return getTabs().get(tabIndex).isVisible();
            }
        };
    }

}

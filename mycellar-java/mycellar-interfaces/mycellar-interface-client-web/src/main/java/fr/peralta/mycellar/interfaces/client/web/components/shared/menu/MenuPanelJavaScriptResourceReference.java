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

import org.odlabs.wiquery.core.resources.WiQueryJavaScriptResourceReference;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteJavaScriptResourceReference;

/**
 * @author speralta
 */
public class MenuPanelJavaScriptResourceReference extends WiQueryJavaScriptResourceReference {

    private static final long serialVersionUID = 201110130911L;

    /**
     * Singleton instance.
     */
    private static MenuPanelJavaScriptResourceReference instance = new MenuPanelJavaScriptResourceReference();

    /**
     * Builds a new instance of {@link AutocompleteJavaScriptResourceReference}.
     */
    private MenuPanelJavaScriptResourceReference() {
        super(MenuPanelJavaScriptResourceReference.class, "dropdown.js");
    }

    /**
     * Returns the {@link AutocompleteJavaScriptResourceReference} instance.
     */
    public static MenuPanelJavaScriptResourceReference get() {
        return instance;
    }
}

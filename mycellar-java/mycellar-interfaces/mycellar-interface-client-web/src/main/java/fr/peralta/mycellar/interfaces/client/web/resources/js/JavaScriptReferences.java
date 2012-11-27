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
package fr.peralta.mycellar.interfaces.client.web.resources.js;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

/**
 * @author speralta
 */
public class JavaScriptReferences {

    public static JavaScriptResourceReference getBootstrapJs() {
        return new JQueryPluginResourceReference(JavaScriptReferences.class, "bootstrap.js");
    }

    public static JavaScriptResourceReference getBootstrapDatePickerJs() {
        return new BootstrapPluginsReference(JavaScriptReferences.class, "bootstrap-datepicker.js");
    }

    public static JavaScriptResourceReference getTypeaheadkeyJs() {
        return new BootstrapPluginsReference(JavaScriptReferences.class,
                "bootstrap-typeaheadkey.js");
    }

    public static JavaScriptResourceReference getMasterJs() {
        return new BootstrapPluginsReference(JavaScriptReferences.class, "master.js");
    }

    /**
     * Refuse instanciation.
     */
    public JavaScriptReferences() {
        throw new UnsupportedOperationException();
    }

}

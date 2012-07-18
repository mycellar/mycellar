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

/**
 * @author speralta
 */
public class JavaScriptReferences {

    public static JavaScriptResourceReference getBootstrapJs() {
        return getJavaScript("bootstrap.min.js");
    }

    public static JavaScriptResourceReference getBootstrapDatePickerJs() {
        return getJavaScript("bootstrap-datepicker.js");
    }

    public static JavaScriptResourceReference getJqueryJs() {
        return getJavaScript("jquery.min.js");
    }

    public static JavaScriptResourceReference getMasterJs() {
        return getJavaScript("master.js");
    }

    private static JavaScriptResourceReference getJavaScript(String filename) {
        return new JavaScriptResourceReference(JavaScriptReferences.class, filename);
    }

    /**
     * Refuse instanciation.
     */
    public JavaScriptReferences() {
        throw new UnsupportedOperationException();
    }

}

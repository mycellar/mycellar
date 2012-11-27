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
package fr.peralta.mycellar.interfaces.client.web.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * @author speralta
 */
public class CssReferences {

    public static CssResourceReference getBootstrapCss() {
        return getCss("bootstrap.css");
    }

    public static CssResourceReference getBootstrapDatePickerCss() {
        return getCss("bootstrap-datepicker.css");
    }

    public static CssResourceReference getMasterCss() {
        return getCss("master.css");
    }

    public static CssResourceReference getBootstrapResponsiveCss() {
        return getCss("bootstrap-responsive.css");
    }

    public static CssResourceReference getMasterResponsiveCss() {
        return getCss("master-responsive.css");
    }

    private static CssResourceReference getCss(String filename) {
        return new CssResourceReference(CssReferences.class, filename);
    }

    /**
     * Refuse instanciation.
     */
    private CssReferences() {
        throw new UnsupportedOperationException();
    }

}

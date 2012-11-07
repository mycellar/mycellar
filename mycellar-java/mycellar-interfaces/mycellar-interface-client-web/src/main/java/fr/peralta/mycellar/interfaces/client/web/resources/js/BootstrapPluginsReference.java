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

import java.util.Arrays;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author speralta
 */
public class BootstrapPluginsReference extends JavaScriptResourceReference {

    private static final long serialVersionUID = 201211051629L;

    /**
     * @param scope
     * @param name
     */
    public BootstrapPluginsReference(Class<?> scope, String name) {
        super(scope, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        return Arrays.asList(JavaScriptHeaderItem.forReference(JavaScriptReferences
                .getBootstrapJs()));
    }

}

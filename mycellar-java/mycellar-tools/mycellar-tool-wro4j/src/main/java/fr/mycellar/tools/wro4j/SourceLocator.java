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
package fr.mycellar.tools.wro4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ro.isdc.wro.model.resource.locator.wildcard.WildcardUriLocatorSupport;

/**
 * @author speralta
 */
public class SourceLocator extends WildcardUriLocatorSupport {

    private final String sourcePath;

    /**
     * @param sourcePath
     */
    public SourceLocator(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream locate(String uri) throws IOException {
        if (getWildcardStreamLocator().hasWildcard(uri)) {
            return getWildcardStreamLocator().locateStream(uri, new File(sourcePath + uri.substring(1, uri.lastIndexOf("/"))));
        }

        return new FileInputStream(sourcePath + uri.substring(1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(String uri) {
        return uri.startsWith("./");
    }

}

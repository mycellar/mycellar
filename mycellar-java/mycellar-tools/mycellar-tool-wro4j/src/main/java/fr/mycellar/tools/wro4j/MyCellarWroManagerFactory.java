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

import ro.isdc.wro.manager.factory.standalone.DefaultStandaloneContextAwareManagerFactory;
import ro.isdc.wro.manager.factory.standalone.StandaloneContext;
import ro.isdc.wro.model.resource.locator.ClasspathUriLocator;
import ro.isdc.wro.model.resource.locator.ServletContextUriLocator;
import ro.isdc.wro.model.resource.locator.UrlUriLocator;
import ro.isdc.wro.model.resource.locator.factory.SimpleUriLocatorFactory;
import ro.isdc.wro.model.resource.locator.factory.UriLocatorFactory;

/**
 * @author speralta
 */
public class MyCellarWroManagerFactory extends DefaultStandaloneContextAwareManagerFactory {

    private String sourcePath;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(StandaloneContext standaloneContext) {
        super.initialize(standaloneContext);
        sourcePath = standaloneContext.getWroFile().getParent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UriLocatorFactory newUriLocatorFactory() {
        return new SimpleUriLocatorFactory() //
                .addUriLocator(new ServletContextUriLocator()) //
                .addUriLocator(new ClasspathUriLocator()) //
                .addUriLocator(new UrlUriLocator()) //
                .addUriLocator(new SourceLocator(sourcePath));
    }

}

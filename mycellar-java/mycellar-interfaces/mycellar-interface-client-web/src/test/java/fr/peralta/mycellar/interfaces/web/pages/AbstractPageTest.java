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
package fr.peralta.mycellar.interfaces.web.pages;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

/**
 * @author speralta
 */
public abstract class AbstractPageTest {

    private WicketTester wicketTester;

    private ApplicationContextMock applicationContext;

    @Before
    public void setUp() {
        applicationContext = new ApplicationContextMock();
        MockApplication mockApplication = new MockApplication();
        mockApplication.getComponentInstantiationListeners().add(
                new SpringComponentInjector(mockApplication, applicationContext));
        wicketTester = new WicketTester(mockApplication);
    }

    /**
     * @return the wicketTester
     */
    public WicketTester getWicketTester() {
        return wicketTester;
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContextMock getApplicationContext() {
        return applicationContext;
    }

}

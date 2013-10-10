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
package fr.mycellar.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author speralta
 * 
 * @param <O>
 * @param <INPUT>
 * @param <EXPECTED>
 */
public abstract class AbstractSimpleTest<O, INPUT, EXPECTED> extends
        AbstractTest<INPUT, EXPECTED, TestValue<INPUT, EXPECTED>> {

    private O objectToTest;

    @Before
    public final void setUp() throws Exception {
        objectToTest = createObjectToTest();
        init(objectToTest);
    }

    protected void init(O objectToTest) {

    }

    protected abstract O createObjectToTest();

    /**
     * {@inheritDoc}
     */
    @Override
    protected final EXPECTED execute(INPUT input, EXPECTED expected) {
        return execute(objectToTest, input, expected);
    }

    protected abstract EXPECTED execute(O objectToTest, INPUT input, EXPECTED expected);

    @Test
    public void checkPackage() {
        assertThat(objectToTest.getClass().getPackage(), is(equalTo(this.getClass().getPackage())));
    }

    /**
     * @return the objectToTest
     */
    protected O getObjectToTest() {
        return objectToTest;
    }

}

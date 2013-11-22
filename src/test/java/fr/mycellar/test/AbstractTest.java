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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author speralta
 */
public abstract class AbstractTest<INPUT, EXPECTED, TV extends TestValue<INPUT, EXPECTED>> {

    private final List<TV> testValues = new ArrayList<TV>();

    @Test
    public void test() {
        fillTestValues(testValues);
        for (TV testValue : testValues) {
            EXPECTED result = execute(testValue.getInput(), testValue.getExpected());
            doAssertion(result, testValue.getExpected());
        }
    }

    protected abstract void fillTestValues(List<TV> testValues);

    protected abstract EXPECTED execute(INPUT input, EXPECTED expected);

    protected abstract void doAssertion(EXPECTED result, EXPECTED expected);

}

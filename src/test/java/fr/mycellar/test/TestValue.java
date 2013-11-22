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

/**
 * @author speralta
 * 
 * @param <INPUT>
 * @param <EXPECTED>
 */
public class TestValue<INPUT, EXPECTED> {

    private final INPUT input;

    private final EXPECTED expected;

    /**
     * @param input
     * @param expected
     */
    public TestValue(INPUT input, EXPECTED expected) {
        this.input = input;
        this.expected = expected;
    }

    /**
     * @return the input
     */
    public INPUT getInput() {
        return input;
    }

    /**
     * @return the expected
     */
    public EXPECTED getExpected() {
        return expected;
    }

}

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
package fr.peralta.mycellar.interfaces.facades.position.mappers;

import static fr.peralta.mycellar.domain.DomainMatchers.hasSameProperties;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.position.Position;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 */
public class PositionMapperTest
        extends
        AbstractMapperTest<fr.peralta.mycellar.interfaces.facades.position.Position, Position, PositionMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Position> matches(Position expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.interfaces.facades.position.Position> getFromClass() {
        return fr.peralta.mycellar.interfaces.facades.position.Position.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Position> getToClass() {
        return Position.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PositionMapper createObjectToTest() {
        return new PositionMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.interfaces.facades.position.Position, Position>> testValues) {
        fr.peralta.mycellar.interfaces.facades.position.Position input = new fr.peralta.mycellar.interfaces.facades.position.Position();
        input.setLatitude(1f);
        input.setLongitude(2f);

        Position expected = new Position(1f, 2f);

        testValues
                .add(new TestValue<fr.peralta.mycellar.interfaces.facades.position.Position, Position>(
                        input, expected));
    }
}

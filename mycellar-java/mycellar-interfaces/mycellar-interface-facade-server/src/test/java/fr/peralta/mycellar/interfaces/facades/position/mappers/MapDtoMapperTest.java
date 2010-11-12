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

import static fr.peralta.mycellar.interfaces.facades.FacadeMatchers.hasSameProperties;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.interfaces.facades.image.Image;
import fr.peralta.mycellar.interfaces.facades.position.Map;
import fr.peralta.mycellar.interfaces.facades.position.Position;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 * 
 */
public class MapDtoMapperTest extends
        AbstractMapperTest<fr.peralta.mycellar.domain.position.Map, Map, MapDtoMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Map> matches(Map expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.position.Map> getFromClass() {
        return fr.peralta.mycellar.domain.position.Map.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Map> getToClass() {
        return Map.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MapDtoMapper createObjectToTest() {
        return new MapDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.domain.position.Map, Map>> testValues) {
        fr.peralta.mycellar.domain.position.Map input = new fr.peralta.mycellar.domain.position.Map(
                new fr.peralta.mycellar.domain.position.Position(0, 0),
                new fr.peralta.mycellar.domain.image.Image(null, null, 0, 0, null));

        Map expected = new Map();
        expected.setPosition(new Position());
        expected.setImage(new Image());

        testValues
                .add(new TestValue<fr.peralta.mycellar.domain.position.Map, Map>(input, expected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mock(fr.peralta.mycellar.domain.position.Map input, Map expected) {
        given(getMapperServiceFacade().map(input.getPosition(), Position.class)).willReturn(
                expected.getPosition());
        given(getMapperServiceFacade().map(input.getImage(), Image.class)).willReturn(
                expected.getImage());
    }

}

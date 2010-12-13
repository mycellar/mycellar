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
package fr.peralta.mycellar.interfaces.facades.wine.mappers;

import static fr.peralta.mycellar.interfaces.facades.FacadeMatchers.hasSameProperties;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.interfaces.facades.position.Map;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.interfaces.facades.wine.Appellation;
import fr.peralta.mycellar.interfaces.facades.wine.Region;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 */
public class AppellationDtoMapperTest
        extends
        AbstractMapperTest<fr.peralta.mycellar.domain.wine.Appellation, Appellation, AppellationDtoMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Appellation> matches(Appellation expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.wine.Appellation> getFromClass() {
        return fr.peralta.mycellar.domain.wine.Appellation.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Appellation> getToClass() {
        return Appellation.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AppellationDtoMapper createObjectToTest() {
        return new AppellationDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.domain.wine.Appellation, Appellation>> testValues) {
        fr.peralta.mycellar.domain.wine.Appellation input = new fr.peralta.mycellar.domain.wine.Appellation(
                "name", new fr.peralta.mycellar.domain.wine.Region(null, null, null, null),
                new fr.peralta.mycellar.domain.position.Map(null, null), "description");

        Appellation expected = new Appellation();
        expected.setName("name");
        expected.setDescription("description");
        expected.setRegion(new Region());
        expected.setMap(new Map());

        testValues.add(new TestValue<fr.peralta.mycellar.domain.wine.Appellation, Appellation>(
                input, expected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mock(fr.peralta.mycellar.domain.wine.Appellation input, Appellation expected) {
        given(getMapperServiceFacade().map(input.getMap(), Map.class))
                .willReturn(expected.getMap());
        given(getMapperServiceFacade().map(input.getRegion(), Region.class)).willReturn(
                expected.getRegion());
    }

}

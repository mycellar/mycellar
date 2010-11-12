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

import static fr.peralta.mycellar.domain.DomainMatchers.hasSameProperties;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.position.Map;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 */
public class CountryMapperTest
        extends
        AbstractMapperTest<fr.peralta.mycellar.interfaces.facades.wine.Country, Country, CountryMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Country> matches(Country expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.interfaces.facades.wine.Country> getFromClass() {
        return fr.peralta.mycellar.interfaces.facades.wine.Country.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Country> getToClass() {
        return Country.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CountryMapper createObjectToTest() {
        return new CountryMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.interfaces.facades.wine.Country, Country>> testValues) {
        Country expected = new Country("name", new Map(null, null), "description");
        fr.peralta.mycellar.interfaces.facades.wine.Country input = new fr.peralta.mycellar.interfaces.facades.wine.Country();
        input.setName("name");
        input.setDescription("description");
        input.setMap(new fr.peralta.mycellar.interfaces.facades.position.Map());

        testValues.add(new TestValue<fr.peralta.mycellar.interfaces.facades.wine.Country, Country>(
                input, expected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mock(fr.peralta.mycellar.interfaces.facades.wine.Country input, Country expected) {
        given(getMapperServiceFacade().map(input.getMap(), Map.class))
                .willReturn(expected.getMap());
    }

}

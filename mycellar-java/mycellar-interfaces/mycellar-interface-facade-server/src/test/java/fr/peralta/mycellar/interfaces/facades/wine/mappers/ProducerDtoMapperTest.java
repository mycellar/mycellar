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

import fr.peralta.mycellar.interfaces.facades.position.Address;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.interfaces.facades.wine.Producer;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 */
public class ProducerDtoMapperTest extends
        AbstractMapperTest<fr.peralta.mycellar.domain.wine.Producer, Producer, ProducerDtoMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Producer> matches(Producer expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.wine.Producer> getFromClass() {
        return fr.peralta.mycellar.domain.wine.Producer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Producer> getToClass() {
        return Producer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProducerDtoMapper createObjectToTest() {
        return new ProducerDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.domain.wine.Producer, Producer>> testValues) {
        fr.peralta.mycellar.domain.wine.Producer input = new fr.peralta.mycellar.domain.wine.Producer(
                "name", "website", "description", new fr.peralta.mycellar.domain.position.Address(
                        null, null, null, null, null, null));

        Producer expected = new Producer();
        expected.setName("name");
        expected.setDescription("description");
        expected.setWebsiteUrl("website");
        expected.setAddress(new Address());

        testValues.add(new TestValue<fr.peralta.mycellar.domain.wine.Producer, Producer>(input,
                expected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mock(fr.peralta.mycellar.domain.wine.Producer input, Producer expected) {
        given(getMapperServiceFacade().map(input.getAddress(), Address.class)).willReturn(
                expected.getAddress());
    }

}

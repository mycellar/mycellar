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
package fr.peralta.mycellar.interfaces.facades.shared.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;
import fr.peralta.mycellar.test.AbstractSimpleTest;

/**
 * @author speralta
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMapperTest<FROM, TO, T extends AbstractMapper<FROM, TO>> extends
        AbstractSimpleTest<T, FROM, TO> {

    @Mock
    private MapperServiceFacade mapperServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAssertion(TO result, TO expected) {
        assertThat(result, matches(expected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TO execute(T objectToTest, FROM input, TO expected) {
        mock(input, expected);
        return objectToTest.map(input);
    }

    /**
     * @return the mapperServiceFacade
     */
    protected MapperServiceFacade getMapperServiceFacade() {
        return mapperServiceFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init(T objectToTest) {
        objectToTest.setMapperServiceFacade(mapperServiceFacade);
    }

    protected abstract Matcher<? super TO> matches(TO expected);

    protected void mock(FROM input, TO expected) {

    }

    protected abstract Class<FROM> getFromClass();

    protected abstract Class<TO> getToClass();

    @Test
    public void shouldInitializeMapperServiceFacade() {
        getObjectToTest().initialize();
        verify(mapperServiceFacade).registerMapper(getObjectToTest(), getFromClass(), getToClass());
    }
}

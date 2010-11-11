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

import static fr.peralta.mycellar.interfaces.facades.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

import fr.peralta.mycellar.domain.image.Image;
import fr.peralta.mycellar.domain.position.Map;
import fr.peralta.mycellar.domain.position.Position;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;

/**
 * @author speralta
 */
public class CountryMapperTest {

    private CountryMapper countryMapper;

    private MapperServiceFacade mapperServiceFacade;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        countryMapper = new CountryMapper();
        mapperServiceFacade = mock(MapperServiceFacade.class);
        countryMapper.setMapperServiceFacade(mapperServiceFacade);
    }

    /**
     * Test method for
     * {@link fr.peralta.mycellar.interfaces.facades.wine.mappers.CountryMapper#map(fr.peralta.mycellar.interfaces.facades.wine.dto.Country)}
     * .
     */
    @Test
    public void testMap() {
        Country expected = new Country("name", new Map(new Position(1f, 1f), new Image("imageName",
                "jpg", 10, 10, new byte[] { 2, 3, 4 })), "description");
        fr.peralta.mycellar.interfaces.facades.wine.dto.Country input = new fr.peralta.mycellar.interfaces.facades.wine.dto.Country();
        input.setName("name");
        input.setDescription("description");
        input.setMap(new fr.peralta.mycellar.interfaces.facades.position.dto.Map());

        given(mapperServiceFacade.map(input.getMap(), Map.class)).willReturn(expected.getMap());

        assertThat(countryMapper.map(input), hasSameProperties(expected));
    }

    /**
     * Test method for
     * {@link fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper#initialize()}
     * .
     */
    @Test
    public void testInitialize() {
        countryMapper.initialize();
        verify(mapperServiceFacade).registerMapper(countryMapper,
                fr.peralta.mycellar.interfaces.facades.wine.dto.Country.class, Country.class);
    }
}

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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.peralta.mycellar.domain.image.Image;
import fr.peralta.mycellar.domain.position.Map;
import fr.peralta.mycellar.domain.position.Position;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;

/**
 * @author speralta
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryDtoMapperTest {

    private CountryDtoMapper countryDtoMapper;

    @Mock
    private MapperServiceFacade mapperServiceFacade;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        countryDtoMapper = new CountryDtoMapper();
        countryDtoMapper.setMapperServiceFacade(mapperServiceFacade);
    }

    /**
     * Test method for
     * {@link fr.peralta.mycellar.interfaces.facades.wine.mappers.CountryDtoMapper#map(fr.peralta.mycellar.domain.wine.Country)}
     * .
     */
    @Test
    public void testMap() {
        Country input = new Country("name", new Map(new Position(1f, 1f), new Image("imageName",
                "jpg", 10, 10, new byte[] { 2, 3, 4 })), "description");

        fr.peralta.mycellar.interfaces.facades.wine.Country expected = new fr.peralta.mycellar.interfaces.facades.wine.Country();
        expected.setName("name");
        expected.setDescription("description");
        fr.peralta.mycellar.interfaces.facades.position.Map expectedMap = new fr.peralta.mycellar.interfaces.facades.position.Map();
        fr.peralta.mycellar.interfaces.facades.image.Image expectedImage = new fr.peralta.mycellar.interfaces.facades.image.Image();
        expectedImage.setContent(new byte[] { 2, 3, 4 });
        expectedImage.setContentType("jpg");
        expectedImage.setName("imageName");
        expectedMap.setImage(expectedImage);
        fr.peralta.mycellar.interfaces.facades.position.Position expectedPosition = new fr.peralta.mycellar.interfaces.facades.position.Position();
        expectedPosition.setLatitude(1f);
        expectedPosition.setLongitude(1f);
        expectedMap.setPosition(expectedPosition);
        expected.setMap(expectedMap);

        given(
                mapperServiceFacade.map(input.getMap(),
                        fr.peralta.mycellar.interfaces.facades.position.Map.class)).willReturn(
                expected.getMap());

        fr.peralta.mycellar.interfaces.facades.wine.Country result = countryDtoMapper.map(input);

        assertThat(result, hasSameProperties(expected));
    }

    /**
     * Test method for
     * {@link fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper#initialize()}
     * .
     */
    @Test
    public void testInitialize() {
        countryDtoMapper.initialize();
        verify(mapperServiceFacade).registerMapper(countryDtoMapper, Country.class,
                fr.peralta.mycellar.interfaces.facades.wine.Country.class);
    }
}

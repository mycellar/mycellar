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
package fr.peralta.mycellar.interfaces.facades.image.mappers;

import static fr.peralta.mycellar.interfaces.facades.FacadeMatchers.hasSameProperties;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.FieldUtils;
import fr.peralta.mycellar.interfaces.facades.image.Image;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 * 
 */
public class ImageDtoMapperTest extends
        AbstractMapperTest<fr.peralta.mycellar.domain.image.Image, Image, ImageDtoMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super Image> matches(Image expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.image.Image> getFromClass() {
        return fr.peralta.mycellar.domain.image.Image.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Image> getToClass() {
        return Image.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ImageDtoMapper createObjectToTest() {
        return new ImageDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.domain.image.Image, Image>> testValues) {
        fr.peralta.mycellar.domain.image.Image input = new fr.peralta.mycellar.domain.image.Image(
                "name", "jpg", 10, 10, new byte[] { 1, 2, 3 });
        FieldUtils.setId(input, 1);
        FieldUtils.setVersion(input, 1);

        Image expected = new Image();
        expected.setContent(new byte[] { 1, 2, 3 });
        expected.setName("name");
        expected.setContentType("jpg");
        expected.setHeight(10);
        expected.setWidth(10);
        expected.setId(1);
        expected.setVersion(1);

        testValues
                .add(new TestValue<fr.peralta.mycellar.domain.image.Image, Image>(input, expected));
    }

}

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

import static fr.peralta.mycellar.domain.DomainMatchers.hasSameProperties;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.image.Image;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 * 
 */
public class ImageMapperTest extends
        AbstractMapperTest<fr.peralta.mycellar.interfaces.facades.image.Image, Image, ImageMapper> {

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
    protected Class<fr.peralta.mycellar.interfaces.facades.image.Image> getFromClass() {
        return fr.peralta.mycellar.interfaces.facades.image.Image.class;
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
    protected ImageMapper createObjectToTest() {
        return new ImageMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.interfaces.facades.image.Image, Image>> testValues) {
        fr.peralta.mycellar.interfaces.facades.image.Image input = new fr.peralta.mycellar.interfaces.facades.image.Image();
        input.setContent(new byte[] { 1, 2, 3 });
        input.setName("name");
        input.setContentType("jpg");
        input.setHeight(10);
        input.setWidth(10);
        input.setId(1);
        input.setVersion(1);

        Image expected = new Image("name", "jpg", 10, 10, new byte[] { 1, 2, 3 });

        testValues.add(new TestValue<fr.peralta.mycellar.interfaces.facades.image.Image, Image>(
                input, expected));
    }

}

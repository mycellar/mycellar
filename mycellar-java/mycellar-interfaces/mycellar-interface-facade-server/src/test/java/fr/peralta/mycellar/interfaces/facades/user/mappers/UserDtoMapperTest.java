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
package fr.peralta.mycellar.interfaces.facades.user.mappers;

import static fr.peralta.mycellar.interfaces.facades.FacadeMatchers.hasSameProperties;

import java.util.List;

import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.FieldUtils;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapperTest;
import fr.peralta.mycellar.interfaces.facades.user.User;
import fr.peralta.mycellar.test.TestValue;

/**
 * @author speralta
 * 
 */
public class UserDtoMapperTest extends
        AbstractMapperTest<fr.peralta.mycellar.domain.user.User, User, UserDtoMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Matcher<? super User> matches(User expected) {
        return hasSameProperties(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.user.User> getFromClass() {
        return fr.peralta.mycellar.domain.user.User.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<User> getToClass() {
        return User.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UserDtoMapper createObjectToTest() {
        return new UserDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillTestValues(
            List<TestValue<fr.peralta.mycellar.domain.user.User, User>> testValues) {
        fr.peralta.mycellar.domain.user.User input = new fr.peralta.mycellar.domain.user.User(
                "email", "password", "firstname", "lastname");
        FieldUtils.setId(input, 1);
        FieldUtils.setVersion(input, 1);

        User expected = new User();
        expected.setEmail("email");
        expected.setFirstname("firstname");
        expected.setId(1);
        expected.setLastname("lastname");
        expected.setPassword("password");
        expected.setVersion(1);

        testValues.add(new TestValue<fr.peralta.mycellar.domain.user.User, User>(input, expected));
    }

}

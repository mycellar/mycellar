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
package fr.peralta.mycellar.interfaces.facades;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import fr.peralta.mycellar.interfaces.facades.image.Image;
import fr.peralta.mycellar.interfaces.facades.image.ImageHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.Map;
import fr.peralta.mycellar.interfaces.facades.position.MapHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.Position;
import fr.peralta.mycellar.interfaces.facades.position.PositionHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.user.User;
import fr.peralta.mycellar.interfaces.facades.user.UserHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.wine.Country;
import fr.peralta.mycellar.interfaces.facades.wine.CountryHasSameProperties;
import fr.peralta.mycellar.test.matchers.MatcherHelper;

/**
 * @author speralta
 */
public class FacadeMatchers {

    @Factory
    public static Matcher<? super User> hasSameProperties(User user) {
        return MatcherHelper.hasSameProperties(user, UserHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Map> hasSameProperties(Map map) {
        return MatcherHelper.hasSameProperties(map, MapHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Position> hasSameProperties(Position position) {
        return MatcherHelper.hasSameProperties(position, PositionHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Image> hasSameProperties(Image image) {
        return MatcherHelper.hasSameProperties(image, ImageHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Country> hasSameProperties(Country country) {
        return MatcherHelper.hasSameProperties(country, CountryHasSameProperties.class);
    }

    /**
     * Refuse instanciation.
     */
    private FacadeMatchers() {

    }

}

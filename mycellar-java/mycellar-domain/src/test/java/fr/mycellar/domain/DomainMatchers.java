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
package fr.mycellar.domain;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import fr.mycellar.domain.image.Image;
import fr.mycellar.domain.image.ImageHasSameProperties;
import fr.mycellar.domain.position.Map;
import fr.mycellar.domain.position.MapHasSameProperties;
import fr.mycellar.domain.position.Position;
import fr.mycellar.domain.position.PositionHasSameProperties;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.user.UserHasSameProperties;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.AppellationHasSameProperties;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.CountryHasSameProperties;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.ProducerHasSameProperties;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.RegionHasSameProperties;
import fr.mycellar.test.matchers.MatcherHelper;

/**
 * @author speralta
 */
public class DomainMatchers {

    @Factory
    public static Matcher<? super Appellation> hasSameProperties(Appellation appellation) {
        return MatcherHelper.hasSameProperties(appellation, AppellationHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Country> hasSameProperties(Country country) {
        return MatcherHelper.hasSameProperties(country, CountryHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Image> hasSameProperties(Image image) {
        return MatcherHelper.hasSameProperties(image, ImageHasSameProperties.class);
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
    public static Matcher<? super Producer> hasSameProperties(Producer producer) {
        return MatcherHelper.hasSameProperties(producer, ProducerHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Region> hasSameProperties(Region region) {
        return MatcherHelper.hasSameProperties(region, RegionHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super User> hasSameProperties(User user) {
        return MatcherHelper.hasSameProperties(user, UserHasSameProperties.class);
    }

    /**
     * Refuse instanciation.
     */
    private DomainMatchers() {

    }

}

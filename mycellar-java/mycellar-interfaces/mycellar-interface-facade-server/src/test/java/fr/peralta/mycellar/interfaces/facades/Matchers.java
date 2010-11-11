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

import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.InvocationTargetException;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import fr.peralta.mycellar.domain.image.Image;
import fr.peralta.mycellar.domain.position.Map;
import fr.peralta.mycellar.domain.position.Position;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.interfaces.facades.image.ImageDtoHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.image.ImageHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.MapDtoHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.MapHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.PositionDtoHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.position.PositionHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.wine.CountryDtoHasSameProperties;
import fr.peralta.mycellar.interfaces.facades.wine.CountryHasSameProperties;

/**
 * @author speralta
 */
public class Matchers {

    private static <T> Matcher<? super T> hasSameProperties(T t,
            Class<? extends PropertiesMatcher<T>> matcherClass) throws RuntimeException {
        if (t != null) {
            Exception exception;
            try {
                return matcherClass.getConstructor(t.getClass()).newInstance(t);
            } catch (IllegalArgumentException e) {
                exception = e;
            } catch (SecurityException e) {
                exception = e;
            } catch (InstantiationException e) {
                exception = e;
            } catch (IllegalAccessException e) {
                exception = e;
            } catch (InvocationTargetException e) {
                exception = e;
            } catch (NoSuchMethodException e) {
                exception = e;
            }
            throw new RuntimeException("Matcher creation error : " + exception.getMessage(),
                    exception);
        } else {
            return is(nullValue());
        }
    }

    @Factory
    public static Matcher<? super Map> hasSameProperties(Map map) {
        return hasSameProperties(map, MapHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super fr.peralta.mycellar.interfaces.facades.position.dto.Map> hasSameProperties(
            fr.peralta.mycellar.interfaces.facades.position.dto.Map map) {
        return hasSameProperties(map, MapDtoHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Position> hasSameProperties(Position position) {
        return hasSameProperties(position, PositionHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super fr.peralta.mycellar.interfaces.facades.position.dto.Position> hasSameProperties(
            fr.peralta.mycellar.interfaces.facades.position.dto.Position position) {
        return hasSameProperties(position, PositionDtoHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Image> hasSameProperties(Image image) {
        return hasSameProperties(image, ImageHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super fr.peralta.mycellar.interfaces.facades.image.dto.Image> hasSameProperties(
            fr.peralta.mycellar.interfaces.facades.image.dto.Image image) {
        return hasSameProperties(image, ImageDtoHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super Country> hasSameProperties(Country country) {
        return hasSameProperties(country, CountryHasSameProperties.class);
    }

    @Factory
    public static Matcher<? super fr.peralta.mycellar.interfaces.facades.wine.dto.Country> hasSameProperties(
            fr.peralta.mycellar.interfaces.facades.wine.dto.Country country) {
        return hasSameProperties(country, CountryDtoHasSameProperties.class);
    }

    /**
     * Refuse instanciation.
     */
    private Matchers() {

    }

}

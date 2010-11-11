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
package fr.peralta.mycellar.test.matchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.InvocationTargetException;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * @author speralta
 */
public final class MatcherHelper {

    /**
     * @param name
     * @param matcher
     * @param item
     * @param mismatchDescription
     * @param firstMismatch
     */
    public static void reportMismatch(String name, Matcher<?> matcher, Object item,
            Description mismatchDescription, boolean firstMismatch) {
        if (!firstMismatch) {
            mismatchDescription.appendText(", ");
        }
        mismatchDescription.appendText(name).appendText(" ");
        matcher.describeMismatch(item, mismatchDescription);
    }

    /**
     * @param <T>
     * @param t
     * @param matcherClass
     * @return
     * @throws RuntimeException
     */
    public static <T> Matcher<? super T> hasSameProperties(T t,
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

    /**
     * Refuse instanciation.
     */
    private MatcherHelper() {

    }

}

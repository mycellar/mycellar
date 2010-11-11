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

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hibernate.util.ReflectHelper;

/**
 * @author speralta
 */
public abstract class PropertiesMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Map<String, Matcher<?>> matchers = new HashMap<String, Matcher<?>>();

    protected final void addProperty(String property, Matcher<?> matcher) {
        matchers.put(property, matcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void describeTo(Description description) {
        boolean first = true;
        description.appendText("has {");
        for (String property : matchers.keySet()) {
            if (!first) {
                description.appendText(", ");
            } else {
                first = false;
            }
            description.appendText(property).appendText(" ")
                    .appendDescriptionOf(matchers.get(property));
        }
        description.appendText("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final boolean matchesSafely(T item, Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("had {");
        for (String property : matchers.keySet()) {
            Matcher<?> matcher = matchers.get(property);
            Object subItem = ReflectHelper.getGetter(item.getClass(), property).get(item);
            if (!matcher.matches(subItem)) {
                MatcherHelper.reportMismatch(property, matcher, subItem, mismatchDescription,
                        matches);
                matches = false;
            }
        }
        mismatchDescription.appendText("}");
        return matches;
    }
}

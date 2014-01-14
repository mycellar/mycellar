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
package fr.mycellar.test.matchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author speralta
 */
public abstract class PropertiesMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Map<String, Matcher<?>> matchers = new HashMap<String, Matcher<?>>();

    protected final void addProperty(String property, Matcher<?> matcher) {
        matchers.put(property, matcher);
    }

    protected final <G> void addNullableProperty(String property, G value, Class<? extends PropertiesMatcher<G>> matcherClass) {
        if (value != null) {
            try {
                addProperty(property, ConstructorUtils.getAccessibleConstructor(matcherClass, value.getClass()).newInstance(value));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            addProperty(property, is(nullValue()));
        }
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
            description.appendText(property).appendText(" ").appendDescriptionOf(matchers.get(property));
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
            Object subItem;
            try {
                subItem = PropertyUtils.getProperty(item, property);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot get " + property + " from " + item.getClass() + " : " + e.getMessage(), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot get " + property + " from " + item.getClass() + " : " + e.getMessage(), e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get " + property + " from " + item.getClass() + " : " + e.getMessage(), e);
            }
            if (!matcher.matches(subItem)) {
                if (!matches) {
                    mismatchDescription.appendText(", ");
                }
                mismatchDescription.appendText(property).appendText(" ");
                matcher.describeMismatch(subItem, mismatchDescription);
                matches = false;
            }
        }
        mismatchDescription.appendText("}");
        return matches;
    }

    public PropertiesMatcher<T> without(String property) {
        matchers.remove(property);
        return this;
    }
}

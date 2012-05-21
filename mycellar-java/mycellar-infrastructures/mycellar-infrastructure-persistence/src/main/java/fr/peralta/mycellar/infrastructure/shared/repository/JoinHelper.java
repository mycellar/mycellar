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
package fr.peralta.mycellar.infrastructure.shared.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * @author speralta
 */
public final class JoinHelper {

    public static <X, Y> Join<X, Y> getJoinSet(From<?, ?> from, String attribute) {
        Join<X, Y> join = getExistingJoin(from, attribute, null);
        if (join == null) {
            join = from.joinSet(attribute);
        }
        return join;
    }

    public static <X, Y> Join<X, Y> getJoinSet(From<?, ?> from, String attribute, JoinType joinType) {
        Join<X, Y> join = getExistingJoin(from, attribute, joinType);
        if (join == null) {
            join = from.joinSet(attribute, joinType);
        }
        return join;
    }

    public static <X, Y> Join<X, Y> getJoin(From<?, ?> from, String attribute) {
        Join<X, Y> join = getExistingJoin(from, attribute, null);
        if (join == null) {
            join = from.join(attribute);
        }
        return join;
    }

    public static <X, Y> Join<X, Y> getJoin(From<?, ?> from, String attribute, JoinType joinType) {
        Join<X, Y> join = getExistingJoin(from, attribute, joinType);
        if (join == null) {
            join = from.join(attribute, joinType);
        }
        return join;
    }

    @SuppressWarnings("unchecked")
    private static <X, Y> Join<X, Y> getExistingJoin(From<?, ?> from, String attribute,
            JoinType joinType) {
        for (Join<?, ?> join : from.getJoins()) {
            if (join.getAttribute().getName().equals(attribute)
                    && ((joinType == null) || (joinType == join.getJoinType()))) {
                return (Join<X, Y>) join;
            }
        }
        return null;
    }

    /**
     * Refuse instantiation.
     */
    private JoinHelper() {
        throw new IllegalStateException(JoinHelper.class.getSimpleName()
                + " must not be instantiated.");
    }

}

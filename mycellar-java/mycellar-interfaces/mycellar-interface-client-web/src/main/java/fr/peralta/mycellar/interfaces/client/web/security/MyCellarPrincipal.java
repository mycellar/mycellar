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
package fr.peralta.mycellar.interfaces.client.web.security;

import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.security.hive.authentication.Subject;
import org.wicketstuff.security.hive.authorization.Principal;

/**
 * @author speralta
 */
public class MyCellarPrincipal implements Principal {

    private static final long serialVersionUID = 201108241003L;

    private final PrincipalNameEnum name;

    /**
     * @param name
     */
    public MyCellarPrincipal(PrincipalNameEnum name) {
        this.name = name;
    }

    public MyCellarPrincipal(String name) {
        PrincipalNameEnum enumName = PrincipalNameEnum.get(name);
        if (enumName == null) {
            throw new WicketRuntimeException("Unknown principal name " + name + ".");
        }
        this.name = enumName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MyCellarPrincipal other = (MyCellarPrincipal) obj;
        if (name != other.name) {
            return false;
        }
        return true;
    }

}

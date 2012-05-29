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
package fr.peralta.mycellar.interfaces.client.web.renderers.user;

import org.springframework.stereotype.Component;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;

/**
 * @author speralta
 */
@Component
public class UserRenderer extends AbstractRenderer<User> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(User object) {
        StringBuilder result = new StringBuilder();
        if (object != null) {
            result.append(object.getLastname()).append(" ").append(object.getFirstname())
                    .append(DEFAULT_SEP).append(object.getEmail());
        } else {
            result.append(NULL_OBJECT);
        }
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<User> getRenderedClass() {
        return User.class;
    }

}
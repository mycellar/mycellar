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
package fr.peralta.mycellar.interfaces.client.web.components.user.autocomplete;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.AbstractAutoCompleteAjaxComponent;
import fr.peralta.mycellar.interfaces.facades.user.UserServiceFacade;

/**
 * @author speralta
 */
public class UserAutoCompleteAjaxComponent extends AbstractAutoCompleteAjaxComponent<User> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private UserServiceFacade userServiceFacade;

    /**
     * @param id
     */
    public UserAutoCompleteAjaxComponent(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getValues(String term) {
        return userServiceFacade.getUsersLike(term);
    }

}

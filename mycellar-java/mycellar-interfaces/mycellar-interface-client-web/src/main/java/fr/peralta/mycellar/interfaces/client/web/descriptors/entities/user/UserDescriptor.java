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
package fr.peralta.mycellar.interfaces.client.web.descriptors.entities.user;

import org.springframework.stereotype.Service;

import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.interfaces.client.web.descriptors.entities.shared.IEntityDescriptor;
import fr.peralta.mycellar.interfaces.client.web.descriptors.shared.AbstractDescriptor;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.user.UsersPage;

/**
 * @author speralta
 */
@Service
public class UserDescriptor extends AbstractDescriptor implements
        IEntityDescriptor<User, UsersPage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<UsersPage> getListPageClass() {
        return UsersPage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitleKey() {
        return "users";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeaderKey() {
        return "userHeader";
    }

}

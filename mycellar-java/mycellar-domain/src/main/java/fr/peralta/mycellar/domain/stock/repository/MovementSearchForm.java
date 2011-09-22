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
package fr.peralta.mycellar.domain.stock.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
public class MovementSearchForm implements Serializable {

    private static final long serialVersionUID = 201109222043L;

    private final User user;
    private final List<Cellar> cellars = new ArrayList<Cellar>();

    /**
     * @param user
     */
    public MovementSearchForm(User user) {
        this.user = user;
    }

    /**
     * @return the cellars
     */
    public List<Cellar> getCellars() {
        return cellars;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

}

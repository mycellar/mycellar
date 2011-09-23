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
package fr.peralta.mycellar.domain.wine.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;

/**
 * @author speralta
 */
public class AppellationSearchForm implements Serializable {

    private static final long serialVersionUID = 201109212136L;

    private final User user;
    private final Set<Cellar> cellars = new HashSet<Cellar>();
    private final List<Region> regions = new ArrayList<Region>();
    private final List<Country> countries = new ArrayList<Country>();

    /**
     * Default constructor.
     */
    public AppellationSearchForm() {
        user = null;
    }

    /**
     * @param user
     */
    public AppellationSearchForm(User user) {
        this.user = user;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the cellars
     */
    public Set<Cellar> getCellars() {
        return cellars;
    }

    /**
     * @return the regions
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

}

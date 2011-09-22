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
import java.util.List;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */
public class WineSearchForm implements Serializable {

    private static final long serialVersionUID = 201109211800L;

    private final List<Country> countries = new ArrayList<Country>();
    private final List<Region> regions = new ArrayList<Region>();
    private final List<Appellation> appellations = new ArrayList<Appellation>();
    private final List<Producer> producers = new ArrayList<Producer>();
    private final List<WineColorEnum> colors = new ArrayList<WineColorEnum>();
    private final List<WineTypeEnum> types = new ArrayList<WineTypeEnum>();
    private final List<Integer> vintages = new ArrayList<Integer>();

    /**
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * @return the regions
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * @return the appellations
     */
    public List<Appellation> getAppellations() {
        return appellations;
    }

    /**
     * @return the producers
     */
    public List<Producer> getProducers() {
        return producers;
    }

    /**
     * @return the colors
     */
    public List<WineColorEnum> getColors() {
        return colors;
    }

    /**
     * @return the types
     */
    public List<WineTypeEnum> getTypes() {
        return types;
    }

    /**
     * @return the vintages
     */
    public List<Integer> getVintages() {
        return vintages;
    }

}

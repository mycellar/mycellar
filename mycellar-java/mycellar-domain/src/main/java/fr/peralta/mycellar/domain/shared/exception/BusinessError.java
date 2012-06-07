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
package fr.peralta.mycellar.domain.shared.exception;

/**
 * @author speralta
 */
public enum BusinessError {
    /*
     * Appellation
     */
    APPELLATION_00001("name"),

    /*
     * Bottle
     */
    BOTTLE_00001("name"),

    /*
     * Cellar
     */
    CELLAR_00001("name"),

    /*
     * CellarShare
     */
    CELLAR_SHARE_00001("email"),

    /*
     * Country
     */
    COUNTRY_00001("name"),

    /*
     * Format
     */
    FORMAT_00001("name"),

    /*
     * Producer
     */
    PRODUCER_00001("name"),

    /*
     * Region
     */
    REGION_00001("name"),

    /*
     * User
     */
    USER_00001("email"), USER_00002("email"),

    /*
     * Wine
     */
    WINE_00001("name");

    private final String property;

    /**
     * @param property
     */
    private BusinessError(String property) {
        this.property = property;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return getClass().getSimpleName() + "." + name();
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

}

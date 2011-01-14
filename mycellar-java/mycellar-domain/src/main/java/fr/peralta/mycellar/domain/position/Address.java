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
package fr.peralta.mycellar.domain.position;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

import fr.peralta.mycellar.domain.wine.Country;

/**
 * @author bperalta
 * 
 */
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 201012140830L;

    @Column(name = "ADDRESS_LINE_1")
    private final String line1;

    @Column(name = "ADDRESS_LINE_2")
    private final String line2;

    @Column(name = "ADDRESS_ZIP_CODE")
    private final String zipCode;

    @Column(name = "ADDRESS_CITY")
    private final String city;

    @JoinColumn(name = "COUNTRY")
    private final Country country;

    @Embedded
    private final Position position;

    /**
     * @param line1
     * @param line2
     * @param zipCode
     * @param city
     * @param country
     * @param position
     */
    public Address(String line1, String line2, String zipCode, String city, Country country,
            Position position) {
        this.line1 = line1;
        this.line2 = line2;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.position = position;
    }

    /**
     * @return the line1
     */
    public String getLine1() {
        return line1;
    }

    /**
     * @return the line2
     */
    public String getLine2() {
        return line2;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

}

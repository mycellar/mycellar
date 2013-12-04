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
package fr.mycellar.domain.position;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import fr.mycellar.domain.wine.Country;

/**
 * @author bperalta
 * 
 */
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 201012140830L;

    @Column(name = "ADDRESS_CITY")
    private String city;

    @ManyToOne
    @JoinColumn(name = "COUNTRY")
    private Country country;

    @Column(name = "ADDRESS_LINE_1")
    private String line1;

    @Column(name = "ADDRESS_LINE_2")
    private String line2;

    @Embedded
    private Position position;

    @Column(name = "ADDRESS_ZIP_CODE")
    private String zipCode;

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
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}

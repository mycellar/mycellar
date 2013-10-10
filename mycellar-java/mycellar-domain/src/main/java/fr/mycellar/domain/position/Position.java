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

/**
 * @author bertrand
 * 
 */
@Embeddable
public class Position implements Serializable {

    private static final long serialVersionUID = 201011071645L;

    @Column(name = "LATITUDE")
    private float latitude;

    @Column(name = "LONGITUDE")
    private float longitude;

    /**
     * @return latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @return longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}

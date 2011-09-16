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
package fr.peralta.mycellar.domain.stock;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

/**
 * @author speralta
 */
@Entity
public class Input extends Movement<Input> {

    private static final long serialVersionUID = 201010311742L;

    @Column(name = "ARRIVAL")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate arrival;

    @Column(name = "CHARGES")
    private float charges;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "SOURCE")
    private String source;

    /**
     * @return the arrival
     */
    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * @return the charges
     */
    public float getCharges() {
        return charges;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param arrival
     *            the arrival to set
     */
    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    /**
     * @param charges
     *            the charges to set
     */
    public void setCharges(float charges) {
        this.charges = charges;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Input other) {
        return ObjectUtils.equals(getArrival(), other.getArrival())
                && ObjectUtils.equals(getCellar(), other.getCellar())
                && ObjectUtils.equals(getCharges(), other.getCharges())
                && ObjectUtils.equals(getNumber(), other.getNumber())
                && ObjectUtils.equals(getPrice(), other.getPrice())
                && ObjectUtils.equals(getSource(), other.getSource())
                && ObjectUtils.equals(getBottle(), other.getBottle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getArrival(), getCellar(), getBottle() };
    }

}

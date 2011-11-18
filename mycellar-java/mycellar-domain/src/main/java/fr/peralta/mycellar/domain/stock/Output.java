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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author bperalta
 */
@Entity
@DiscriminatorValue("O")
public class Output extends Movement<Output> {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "PRICE")
    private float price;

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Output other) {
        return ObjectUtils.equals(getDate(), other.getDate())
                && ObjectUtils.equals(getCellar(), other.getCellar())
                && ObjectUtils.equals(getNumber(), other.getNumber())
                && ObjectUtils.equals(getPrice(), other.getPrice())
                && ObjectUtils.equals(getDestination(), other.getDestination())
                && ObjectUtils.equals(getBottle(), other.getBottle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getDate(), getCellar(), getBottle() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ToStringBuilder toStringBuilder() {
        return super.toStringBuilder().append("destination", destination).append("price", price);
    }
}

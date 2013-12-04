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
package fr.mycellar.domain.stock;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
@Entity
@DiscriminatorValue("I")
public class Input extends Movement {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "CHARGES")
    private float charges;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "SOURCE")
    private String source;

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

    public void setCharges(float charges) {
        this.charges = charges;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Input input = (Input) other;
        return ObjectUtils.equals(getDate(), input.getDate()) && ObjectUtils.equals(getCellar(), input.getCellar()) && ObjectUtils.equals(getCharges(), input.getCharges())
                && ObjectUtils.equals(getNumber(), input.getNumber()) && ObjectUtils.equals(getPrice(), input.getPrice()) && ObjectUtils.equals(getSource(), input.getSource())
                && ObjectUtils.equals(getBottle(), input.getBottle());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getDate(), getCellar(), getBottle() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("charges", charges).append("price", price).append("source", source).build();
    }

}

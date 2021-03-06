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

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author bperalta
 */
@Entity
@DiscriminatorValue("O")
public class Output extends Movement {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "PRICE")
    private float price;

    public String getDestination() {
        return destination;
    }

    public float getPrice() {
        return price;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Output output = (Output) other;
        return Objects.equals(getDate(), output.getDate()) && Objects.equals(getCellar(), output.getCellar()) && Objects.equals(getNumber(), output.getNumber())
                && Objects.equals(getPrice(), output.getPrice()) && Objects.equals(getDestination(), output.getDestination()) && Objects.equals(getBottle(), output.getBottle());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getDate(), getCellar(), getBottle() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("destination", destination).append("price", price).build();
    }
}

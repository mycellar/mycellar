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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "INPUT")
@SequenceGenerator(name = "INPUT_ID_GENERATOR", allocationSize = 1)
public class Input extends IdentifiedEntity<Input> {

    private static final long serialVersionUID = 201010311742L;

    @Column(name = "ARRIVAL")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private final LocalDate arrival;

    @ManyToOne
    @JoinColumn(name = "WINE", nullable = false)
    private final Bottle bottle;

    @Column(name = "NUMBER")
    private final int number;

    @Column(name = "PRICE")
    private final float price;

    @Column(name = "SOURCE")
    private final String source;

    @Column(name = "CHARGES")
    private final float charges;

    @Id
    @GeneratedValue(generator = "INPUT_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param arrival
     * @param bottle
     * @param number
     * @param price
     * @param source
     * @param charges
     */
    public Input(LocalDate arrival, Bottle bottle, int number, float price,
            String source, float charges) {
        this.arrival = arrival;
        this.bottle = bottle;
        this.number = number;
        this.price = price;
        this.source = source;
        this.charges = charges;
    }

    /**
     * @return the arrival
     */
    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * @return the bottle
     */
    public Bottle getBottle() {
        return bottle;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
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
     * @return the charges
     */
    public float getCharges() {
        return charges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getArrival(), getSource() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Input other) {
        return ObjectUtils.equals(getArrival(), other.getArrival())
                && ObjectUtils.equals(getCharges(), other.getCharges())
                && ObjectUtils.equals(getNumber(), other.getNumber())
                && ObjectUtils.equals(getPrice(), other.getPrice())
                && ObjectUtils.equals(getSource(), other.getSource())
                && ObjectUtils.equals(getBottle(), other.getBottle());
    }

}

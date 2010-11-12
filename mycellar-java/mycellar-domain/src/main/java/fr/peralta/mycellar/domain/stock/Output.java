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
 * @author bperalta
 */
@Entity
@Table(name = "OUTPUT")
@SequenceGenerator(name = "OUTPUT_ID_GENERATOR", allocationSize = 1)
public class Output extends IdentifiedEntity<Output> {

    private static final long serialVersionUID = 201011111800L;

    @Column(name = "OUTPUT")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate output;

    @ManyToOne
    @JoinColumn(name = "CELLAR", nullable = false)
    private Cellar cellar;

    @ManyToOne
    @JoinColumn(name = "WINE", nullable = false)
    private Bottle bottle;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "DESTINATION")
    private String destination;

    @Id
    @GeneratedValue(generator = "OUTPUT_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param output
     * @param cellar
     * @param bottle
     * @param number
     * @param price
     * @param destination
     */
    public Output(LocalDate output, Cellar cellar, Bottle bottle, int number, float price,
            String destination) {
        this.output = output;
        this.cellar = cellar;
        this.bottle = bottle;
        this.number = number;
        this.price = price;
        this.destination = destination;
    }

    /**
     * 
     */
    Output() {
    }

    /**
     * @return the output
     */
    public LocalDate getOutput() {
        return output;
    }

    /**
     * @return the cellar
     */
    public Cellar getCellar() {
        return cellar;
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
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Output other) {
        return ObjectUtils.equals(getOutput(), other.getOutput())
                && ObjectUtils.equals(getCellar(), other.getCellar())
                && ObjectUtils.equals(getBottle(), other.getBottle())
                && ObjectUtils.equals(getNumber(), other.getNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getOutput() };
    }
}

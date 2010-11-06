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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOTTLE", uniqueConstraints = @UniqueConstraint(columnNames = {
        "WINE", "FORMAT" }))
@SequenceGenerator(name = "BOTTLE_ID_GENERATOR", allocationSize = 1)
public class Bottle extends IdentifiedEntity<Bottle> {

    private static final long serialVersionUID = 201010311742L;

    @ManyToOne
    @JoinColumn(name = "WINE", nullable = false)
    private Wine wine;

    @ManyToOne
    @JoinColumn(name = "FORMAT", nullable = false)
    private Format format;

    @Id
    @GeneratedValue(generator = "BOTTLE_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param wine
     * @param format
     */
    public Bottle(Wine wine, Format format) {
        this.wine = wine;
        this.format = format;
    }

    /**
     * Needed by hibernate.
     */
    Bottle() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the wine
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * @return the format
     */
    public Format getFormat() {
        return format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getFormat(), getWine() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Bottle other) {
        return ObjectUtils.equals(getFormat(), other.getFormat())
                && ObjectUtils.equals(getWine(), other.getWine());
    }

}

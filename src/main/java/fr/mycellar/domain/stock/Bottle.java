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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOTTLE", uniqueConstraints = @UniqueConstraint(columnNames = { "WINE", "FORMAT" }))
@SequenceGenerator(name = "BOTTLE_ID_GENERATOR", allocationSize = 1)
public class Bottle extends IdentifiedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "bottle")
    @XmlTransient
    private final Set<Stock> stocks = new HashSet<Stock>();

    @ManyToOne
    @JoinColumn(name = "FORMAT", nullable = false)
    private Format format;

    @Id
    @GeneratedValue(generator = "BOTTLE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "WINE", nullable = false)
    private Wine wine;

    /**
     * @return the format
     */
    public Format getFormat() {
        return format;
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
     * @param format
     *            the format to set
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * @param wine
     *            the wine to set
     */
    public void setWine(Wine wine) {
        this.wine = wine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Bottle bottle = (Bottle) other;
        return ObjectUtils.equals(getFormat(), bottle.getFormat()) && ObjectUtils.equals(getWine(), bottle.getWine());
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
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("format", format).append("wine", wine).build();
    }

}

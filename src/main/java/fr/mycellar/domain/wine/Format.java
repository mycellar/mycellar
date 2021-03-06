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
package fr.mycellar.domain.wine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;
import fr.mycellar.domain.stock.Stock;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "FORMAT", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME", "CAPACITY" }))
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false, unique = false))
@SequenceGenerator(name = "FORMAT_ID_GENERATOR", allocationSize = 1)
public class Format extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "CAPACITY")
    @Getter
    @Setter
    private float capacity;

    @Id
    @GeneratedValue(generator = "FORMAT_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @OneToMany(mappedBy = "bottle.format")
    @XmlTransient
    private final Set<BookingBottle> bookingBottles = new HashSet<BookingBottle>();

    @OneToMany(mappedBy = "bottle.format")
    @XmlTransient
    private final Set<Stock> stocks = new HashSet<Stock>();

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Format format = (Format) other;
        return Objects.equals(getName(), format.getName());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("capacity", capacity).build();
    }

}

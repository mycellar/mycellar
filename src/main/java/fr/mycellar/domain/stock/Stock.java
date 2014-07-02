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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author bperalta
 */
@Entity
@Table(name = "STOCK", uniqueConstraints = @UniqueConstraint(columnNames = { "WINE", "FORMAT", "CELLAR" }))
@SequenceGenerator(name = "STOCK_ID_GENERATOR", allocationSize = 1)
public class Stock extends IdentifiedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Embedded
    @Valid
    @Getter
    @Setter
    private Bottle bottle;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "CELLAR", nullable = false)
    @Getter
    @Setter
    private Cellar cellar;

    @Id
    @GeneratedValue(generator = "STOCK_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "QUANTITY", nullable = false)
    @Min(1)
    @Getter
    @Setter
    private Integer quantity;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Stock stock = (Stock) other;
        return Objects.equals(getBottle(), stock.getBottle());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getBottle() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("bottle", bottle).append("cellar", cellar).append("quantity", quantity).build();
    }

}

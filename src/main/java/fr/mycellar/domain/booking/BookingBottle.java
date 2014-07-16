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
package fr.mycellar.domain.booking;

import java.util.Objects;

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
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.ValidationPattern;
import fr.mycellar.domain.stock.Bottle;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOOKING_BOTTLE", uniqueConstraints = { @UniqueConstraint(columnNames = { "FORMAT", "WINE", "BOOKING_EVENT" }), @UniqueConstraint(columnNames = { "BOOKING_EVENT", "POSITION" }) })
@SequenceGenerator(name = "BOOKING_BOTTLE_ID_GENERATOR", allocationSize = 1)
public class BookingBottle extends IdentifiedEntity {

    private static final long serialVersionUID = 201205220734L;

    @Id
    @GeneratedValue(generator = "BOOKING_BOTTLE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Valid
    @ManyToOne
    @JoinColumn(name = "BOOKING_EVENT")
    @JsonBackReference("bookingEvent-bottles")
    @Getter
    @Setter
    private BookingEvent bookingEvent;

    @Valid
    @Embedded
    @Getter
    @Setter
    private Bottle bottle;

    @Column(name = "PRICE", precision = 2, length = 6, nullable = false)
    @Getter
    @Setter
    private Float price;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "URL", nullable = false)
    @Getter
    @Setter
    private String url;

    @Column(name = "MAX", nullable = false)
    @Getter
    @Setter
    private Integer max;

    @Column(name = "POSITION", nullable = false)
    @Getter
    @Setter
    private Integer position;

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        BookingBottle bookingBottle = (BookingBottle) other;
        return Objects.equals(getBookingEvent(), bookingBottle.getBookingEvent()) && Objects.equals(getBottle(), bookingBottle.getBottle());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getBookingEvent(), getBottle() };
    }

}

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
    private Integer id;

    @Valid
    @ManyToOne
    @JoinColumn(name = "BOOKING_EVENT")
    @JsonBackReference("bookingEvent-bottles")
    private BookingEvent bookingEvent;

    @Valid
    @Embedded
    private Bottle bottle;

    @Column(name = "PRICE", precision = 2, length = 6, nullable = false)
    private Float price;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "MAX", nullable = false)
    private Integer max;

    @Column(name = "POSITION", nullable = false)
    private Integer position;

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return the bottle
     */
    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * @return the bookingEvent
     */
    public BookingEvent getBookingEvent() {
        return bookingEvent;
    }

    public void setBookingEvent(BookingEvent bookingEvent) {
        this.bookingEvent = bookingEvent;
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

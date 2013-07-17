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
package fr.peralta.mycellar.domain.booking;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;
import fr.peralta.mycellar.domain.stock.Bottle;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOOKING_BOTTLE", uniqueConstraints = { @UniqueConstraint(columnNames = { "BOTTLE", "BOOKING_EVENT" }), @UniqueConstraint(columnNames = { "BOOKING_EVENT", "POSITION" }) })
@SequenceGenerator(name = "BOOKING_BOTTLE_ID_GENERATOR", allocationSize = 1)
public class BookingBottle extends IdentifiedEntity {

    private static final long serialVersionUID = 201205220734L;

    @Id
    @GeneratedValue(generator = "BOOKING_BOTTLE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Valid
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "BOOKING_EVENT")
    @JsonBackReference("bookingEvent-bottles")
    private BookingEvent bookingEvent;

    @Valid
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "BOTTLE")
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return the bottle
     */
    public Bottle getBottle() {
        return bottle;
    }

    /**
     * @param bottle
     *            the bottle to set
     */
    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * @return the bookingEvent
     */
    public BookingEvent getBookingEvent() {
        return bookingEvent;
    }

    /**
     * @param bookingEvent
     *            the bookingEvent to set
     */
    public void setBookingEvent(BookingEvent bookingEvent) {
        this.bookingEvent = bookingEvent;
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        BookingBottle bookingBottle = (BookingBottle) other;
        return ObjectUtils.equals(getBookingEvent(), bookingBottle.getBookingEvent()) && ObjectUtils.equals(getBottle(), bookingBottle.getBottle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getBookingEvent(), getBottle() };
    }

}

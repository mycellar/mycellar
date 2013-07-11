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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOOKING", uniqueConstraints = @UniqueConstraint(columnNames = { "CUSTOMER", "BOOKING_EVENT" }))
@SequenceGenerator(name = "BOOKING_ID_GENERATOR", allocationSize = 1)
public class Booking extends IdentifiedEntity {

    private static final long serialVersionUID = 201205220734L;

    @Id
    @GeneratedValue(generator = "BOOKING_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER", nullable = false)
    private User customer;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "BOOKING_EVENT", nullable = false)
    private BookingEvent bookingEvent;

    // WARNING : DO NOT SET FETCHTYPE TO EAGER, hibernate bug with eager map
    // association (insert uninitialized entities in hashmap (so wrong hash) and
    // initialize them after)
    @ElementCollection
    @JoinTable(name = "BOOKING_QUANTITIES", joinColumns = @JoinColumn(name = "BOOKING"))
    @Column(name = "QUANTITY", nullable = false)
    @MapKeyJoinColumn(name = "BOOKING_BOTTLE")
    @XmlTransient
    private final Map<BookingBottle, Integer> quantities = new HashMap<BookingBottle, Integer>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(User user) {
        customer = user;
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
     * @return the quantities
     */
    @XmlTransient
    public Map<BookingBottle, Integer> getQuantities() {
        return quantities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Booking booking = (Booking) other;
        return ObjectUtils.equals(getCustomer(), booking.getCustomer()) && ObjectUtils.equals(getBookingEvent(), booking.getBookingEvent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getCustomer(), getBookingEvent() };
    }

}

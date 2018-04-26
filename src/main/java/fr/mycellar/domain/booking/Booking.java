/*
 * Copyright 2018, MyCellar
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import fr.mycellar.domain.shared.AbstractAuditingEntity;
import fr.mycellar.domain.user.User;

/**
 * @author speralta
 */
@Entity
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = { "customer", "booking_event" }))
public class Booking extends AbstractAuditingEntity {

	private static final long serialVersionUID = 201205220734L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer", nullable = false)
	private User customer;

	@ManyToOne
	@JoinColumn(name = "booking_event", nullable = false)
	private BookingEvent bookingEvent;

	// WARNING : DO NOT SET FETCHTYPE TO EAGER, hibernate bug with eager map
	// association (insert uninitialized entities in hashmap (so wrong hash) and
	// initialize them after)
	@ElementCollection
	@JoinTable(name = "booking_quantities", joinColumns = @JoinColumn(name = "booking"))
	@Column(name = "quantity", nullable = false)
	@MapKeyJoinColumn(name = "booking_bottle")
	private final Map<BookingBottle, Integer> quantities = new HashMap<>();

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public BookingEvent getBookingEvent() {
		return bookingEvent;
	}

	public void setBookingEvent(BookingEvent bookingEvent) {
		this.bookingEvent = bookingEvent;
	}

	public Long getId() {
		return id;
	}

	public Map<BookingBottle, Integer> getQuantities() {
		return quantities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Booking booking = (Booking) o;
		if (booking.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), booking.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Booking{id=" + getId() + ", customer='" + getCustomer() + ", bookingEvent='" + getBookingEvent() + "'}";
	}
}

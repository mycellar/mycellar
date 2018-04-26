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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.joda.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fr.mycellar.domain.booking.comparator.BookingBottlePositionComparator;
import fr.mycellar.domain.shared.AbstractAuditingEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "booking_event", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "start", "end" }))
public class BookingEvent extends AbstractAuditingEntity {

	public static class BookingBottleSet extends TreeSet<BookingBottle> {

		private static final long serialVersionUID = 201307221610L;

		public BookingBottleSet() {
			super(new BookingBottlePositionComparator());
		}

	}

	private static final long serialVersionUID = 201804261402L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Valid
	@OneToMany(mappedBy = "bookingEvent", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST }, fetch = FetchType.EAGER, orphanRemoval = true)
	@OrderBy("position")
	@JsonManagedReference("bookingEvent-bottles")
	@JsonDeserialize(as = BookingBottleSet.class)
	private final Set<BookingBottle> bottles = new BookingBottleSet();

	@Column(name = "start", nullable = false)
	private Instant start;

	@Column(name = "end", nullable = false)
	private Instant end;

	@JsonIgnore
	@OneToMany(mappedBy = "bookingEvent")
	private final Set<Booking> bookings = new HashSet<Booking>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public Instant getEnd() {
		return end;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

	public Long getId() {
		return id;
	}

	public Set<BookingBottle> getBottles() {
		return bottles;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BookingEvent appellation = (BookingEvent) o;
		if (appellation.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), appellation.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "BookingEvent{id=" + getId() + ", name='" + getName() + ", start='" + getStart() + ", end='" + getEnd()
				+ "'}";
	}
}

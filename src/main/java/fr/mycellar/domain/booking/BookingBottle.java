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

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;

import fr.mycellar.domain.shared.AbstractAuditingEntity;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@Entity
@Table(name = "booking_bottle", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "format", "wine", "booking_event" }),
		@UniqueConstraint(columnNames = { "booking_event", "position" }) })
public class BookingBottle extends AbstractAuditingEntity {

	private static final long serialVersionUID = 201804261330L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@Valid
	@ManyToOne
	@JoinColumn(name = "booking_event")
	@JsonBackReference("bookingEvent-bottles")
	private BookingEvent bookingEvent;

	@Valid
	@ManyToOne
	@JoinColumn(name = "format")
	private Format format;

	@Valid
	@ManyToOne
	@JoinColumn(name = "wine")
	private Wine wine;

	@Column(name = "price", precision = 2, length = 6, nullable = false)
	private Float price;

	@Column(name = "max", nullable = false)
	private Integer max;

	@Column(name = "position", nullable = false)
	private Integer position;

	public BookingEvent getBookingEvent() {
		return bookingEvent;
	}

	public void setBookingEvent(BookingEvent bookingEvent) {
		this.bookingEvent = bookingEvent;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Wine getWine() {
		return wine;
	}

	public void setWine(Wine wine) {
		this.wine = wine;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BookingBottle bookingBottle = (BookingBottle) o;
		if (bookingBottle.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), bookingBottle.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "BookingBottle{id=" + getId() + ", position='" + getPosition() + "'}";
	}

}

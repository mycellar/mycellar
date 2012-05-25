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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.NamedEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "BOOKING_EVENT", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME",
        "START", "END" }))
@SequenceGenerator(name = "BOOKING_EVENT_ID_GENERATOR", allocationSize = 1)
public class BookingEvent extends NamedEntity {

    private static final long serialVersionUID = 201205220734L;

    @SuppressWarnings("unused")
    @OneToMany(mappedBy = "bookingEvent")
    private final Set<Booking> bookings = new HashSet<Booking>();

    @Id
    @GeneratedValue(generator = "BOOKING_EVENT_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Valid
    @OneToMany(mappedBy = "bookingEvent", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<BookingBottle> bottles = new HashSet<BookingBottle>();

    @Column(name = "START", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate start;

    @Column(name = "END", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate end;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the start
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * @return the bottles
     */
    public Set<BookingBottle> getBottles() {
        return bottles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        BookingEvent bookingEvent = (BookingEvent) other;
        return ObjectUtils.equals(getStart(), bookingEvent.getStart())
                && ObjectUtils.equals(getEnd(), bookingEvent.getEnd())
                && ObjectUtils.equals(getName(), bookingEvent.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getStart(), getEnd() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ToStringBuilder toStringBuilder() {
        return super.toStringBuilder().append("start", start).append("end", end);
    }

}

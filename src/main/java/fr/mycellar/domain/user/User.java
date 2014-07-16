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
package fr.mycellar.domain.user;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.stock.Cellar;

/**
 * @author bperalta
 */
@Entity
@Indexed
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = { "EMAIL" }))
@SequenceGenerator(name = "USER_ID_GENERATOR", allocationSize = 1)
public class User extends IdentifiedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "owner")
    @XmlTransient
    private final Set<Cellar> cellars = new HashSet<Cellar>();

    @OneToMany(mappedBy = "customer")
    @XmlTransient
    private final Set<Booking> bookings = new HashSet<Booking>();

    @Column(name = "EMAIL", nullable = false)
    @Field
    @Getter
    @Setter
    private String email;

    @Column(name = "FIRSTNAME", nullable = false)
    @Field
    @Getter
    @Setter
    private String firstname;

    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Column(name = "LASTNAME", nullable = false)
    @Field
    @Getter
    @Setter
    private String lastname;

    @Column(name = "PASSWORD", nullable = false, length = 40)
    @XmlTransient
    @Getter
    @Setter
    private String password;

    @Column(name = "PROFILE")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ProfileEnum profile;

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        User user = (User) other;
        return Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getEmail() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("email", email).append("firstname", firstname).append("lastname", lastname).append("profile", profile).build();
    }

}

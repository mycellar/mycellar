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
    private String email;

    @Column(name = "FIRSTNAME", nullable = false)
    @Field
    private String firstname;

    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "LASTNAME", nullable = false)
    @Field
    private String lastname;

    @Column(name = "PASSWORD", nullable = false, length = 40)
    @XmlTransient
    private String password;

    @Column(name = "PROFILE")
    @Enumerated(EnumType.STRING)
    private ProfileEnum profile;

    /**
     * @return the profile
     */
    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

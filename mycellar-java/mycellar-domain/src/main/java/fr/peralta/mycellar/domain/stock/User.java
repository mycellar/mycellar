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
package fr.peralta.mycellar.domain.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author bperalta
 */
@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = { "EMAIL" }))
@SequenceGenerator(name = "EMAIL_ID_GENERATOR", allocationSize = 1)
public class User extends IdentifiedEntity<User> {

    private static final long serialVersionUID = 201011111720L;

    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;
    
    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     */
    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Needed by hibernate.
     */
    User() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(User other) {
        return ObjectUtils.equals(getEmail(), other.getEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getEmail() };
    }

}

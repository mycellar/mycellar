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
package fr.peralta.mycellar.domain.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.stock.Cellar;

/**
 * @author bperalta
 */
@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = { "EMAIL" }))
@SequenceGenerator(name = "USER_ID_GENERATOR", allocationSize = 1)
public class User extends IdentifiedEntity<User> {

    private static final long serialVersionUID = 201011121629L;

    @OneToMany(mappedBy = "owner")
    private final Set<Cellar> cellars = new HashSet<Cellar>();

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "PASSWORD", nullable = false, length = 40)
    private String password;

    /**
     * @return the cellars
     */
    public Set<Cellar> getCellars() {
        return Collections.unmodifiableSet(cellars);
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

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

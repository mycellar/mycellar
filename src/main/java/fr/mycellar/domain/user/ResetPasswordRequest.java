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

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "RESET_PASSWORD_REQUEST")
@SequenceGenerator(name = "RESET_PASSWORD_REQUEST_ID_GENERATOR", allocationSize = 1)
public class ResetPasswordRequest extends IdentifiedEntity {

    private static final long serialVersionUID = 201206010941L;

    @Id
    @GeneratedValue(generator = "RESET_PASSWORD_REQUEST_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "DATE_TIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    @Column(name = "REQUEST_KEY", length = 32, unique = true)
    private String key;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        ResetPasswordRequest resetPasswordRequest = (ResetPasswordRequest) other;
        return Objects.equals(key, resetPasswordRequest.getKey());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getKey() };
    }

}

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
package fr.mycellar.domain.contact;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.joda.time.LocalDate;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "CONTACT", uniqueConstraints = @UniqueConstraint(columnNames = { "PRODUCER", "CURRENT" }))
@SequenceGenerator(name = "CONTACT_ID_GENERATOR", allocationSize = 1)
public class Contact extends IdentifiedEntity {

    private static final long serialVersionUID = 201206210738L;

    @Id
    @GeneratedValue(generator = "CONTACT_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @IndexedEmbedded
    @Valid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PRODUCER", nullable = false)
    @Getter
    @Setter
    private Producer producer;

    @Column(name = "TEXT", length = 10000)
    @Field
    @Getter
    @Setter
    private String text;

    @Column(name = "CURRENT", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Getter
    @Setter
    private LocalDate current;

    @Column(name = "NEXT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Getter
    @Setter
    private LocalDate next;

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Contact contact = (Contact) other;
        return Objects.equals(producer, contact.producer) && Objects.equals(current, contact.current);
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { producer, current };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("next", next).append("producer", producer).append("text", text).build();
    }

}

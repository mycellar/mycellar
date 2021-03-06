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
package fr.mycellar.domain.wine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;

import fr.mycellar.domain.position.Address;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;
import fr.mycellar.domain.shared.ValidationPattern;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "PRODUCER")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "PRODUCER_ID_GENERATOR", allocationSize = 1)
public class Producer extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Embedded
    @Getter
    @Setter
    private Address address;

    @Column(name = "DESCRIPTION", length = 10000)
    @Getter
    @Setter
    private String description;

    @Column(name = "CONTACT_INFORMATION", length = 10000)
    @Getter
    @Setter
    private String contactInformation;

    @Id
    @GeneratedValue(generator = "PRODUCER_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Column(name = "PHONE")
    @Getter
    @Setter
    private String phone;

    @Column(name = "FAX")
    @Getter
    @Setter
    private String fax;

    @Column(name = "EMAIL")
    @Getter
    @Setter
    private String email;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "WEBSITE_URL")
    @Getter
    @Setter
    private String websiteUrl;

    @OneToMany(mappedBy = "producer")
    @XmlTransient
    private final Set<Wine> wines = new HashSet<Wine>();

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Producer producer = (Producer) other;
        return Objects.equals(getName(), producer.getName());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("address", address).append("description", description).append("websiteUrl", websiteUrl).build();
    }

}

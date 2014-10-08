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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import fr.mycellar.domain.position.Map;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "APPELLATION", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME", "REGION", "COUNTRY" }))
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "APPELLATION_ID_GENERATOR", allocationSize = 1)
public class Appellation extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    private String description;

    @Id
    @GeneratedValue(generator = "APPELLATION_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Embedded
    @Getter
    @Setter
    private Map map;

    @IndexedEmbedded
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "REGION", nullable = true)
    @Getter
    @Setter
    private Region region;

    @IndexedEmbedded
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "COUNTRY", nullable = true)
    @Getter
    @Setter
    private Country country;

    @OneToMany(mappedBy = "appellation")
    @XmlTransient
    private final Set<Wine> wines = new HashSet<Wine>();

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Appellation appellation = (Appellation) other;
        return Objects.equals(getName(), appellation.getName()) && Objects.equals(getRegion(), appellation.getRegion());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("description", description).append("map", map).append("region", region).build();
    }

}

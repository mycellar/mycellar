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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.bridge.builtin.IntegerBridge;

import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;
import fr.mycellar.domain.shared.ValidationPattern;
import fr.mycellar.domain.stock.Stock;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "WINE", uniqueConstraints = @UniqueConstraint(columnNames = { "APPELLATION", "COLOR", "TYPE", "NAME", "VINTAGE", "PRODUCER" }))
@SequenceGenerator(name = "WINE_ID_GENERATOR", allocationSize = 1)
public class Wine extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "bottle.wine")
    @XmlTransient
    private final Set<BookingBottle> bookingBottles = new HashSet<BookingBottle>();

    @OneToMany(mappedBy = "bottle.wine")
    @XmlTransient
    private final Set<Stock> stocks = new HashSet<Stock>();

    @IndexedEmbedded
    @Valid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "APPELLATION", nullable = false)
    @Getter
    @Setter
    private Appellation appellation;

    @Column(name = "COLOR", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Getter
    @Setter
    private WineColorEnum color;

    @ElementCollection
    @JoinTable(name = "WINE_VARIETAL", joinColumns = @JoinColumn(name = "WINE"))
    @Column(name = "PERCENT")
    @MapKeyJoinColumn(name = "VARIETAL")
    @XmlTransient
    private final Map<Varietal, Integer> composition = new HashMap<Varietal, Integer>();

    @Column(name = "DESCRIPTION")
    @Getter
    @Setter
    private String description;

    @Id
    @GeneratedValue(generator = "WINE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "PHOTO_URL")
    @Getter
    @Setter
    private String photoUrl;

    @IndexedEmbedded
    @Valid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PRODUCER", nullable = false)
    @Getter
    @Setter
    private Producer producer;

    @Column(name = "RANKING")
    @Getter
    @Setter
    private String ranking;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Getter
    @Setter
    private WineTypeEnum type;

    @Field(bridge = @FieldBridge(impl = IntegerBridge.class))
    @Column(name = "VINTAGE")
    @Getter
    @Setter
    private Integer vintage;

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Wine wine = (Wine) other;
        return Objects.equals(getName(), wine.getName()) && Objects.equals(getType(), wine.getType()) && Objects.equals(getVintage(), wine.getVintage()) && Objects.equals(getColor(), wine.getColor())
                && Objects.equals(getAppellation(), wine.getAppellation()) && Objects.equals(getProducer(), wine.getProducer());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getVintage(), getColor(), getType() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("appellation", appellation).append("description", description).append("photoUrl", photoUrl).append("producer", producer)
                .append("ranking", ranking).append("type", type).append("vintage", vintage).build();
    }
}

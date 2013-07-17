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
package fr.peralta.mycellar.domain.wine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;
import fr.peralta.mycellar.domain.stock.Bottle;

/**
 * @author speralta
 */
@Entity
@Indexed
@Table(name = "WINE")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = true))
@SequenceGenerator(name = "WINE_ID_GENERATOR", allocationSize = 1)
public class Wine extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "wine")
    @XmlTransient
    private final Set<Bottle> bottles = new HashSet<Bottle>();

    @Valid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "APPELLATION")
    private Appellation appellation;

    @Column(name = "COLOR", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private WineColorEnum color;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "WINE_VARIETAL", joinColumns = @JoinColumn(name = "WINE"))
    @Column(name = "PERCENT")
    @MapKeyJoinColumn(name = "VARIETAL")
    @XmlTransient
    private final Map<Varietal, Integer> composition = new HashMap<Varietal, Integer>();

    @Column(name = "DESCRIPTION")
    private String description;

    @Id
    @GeneratedValue(generator = "WINE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    @Valid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PRODUCER")
    private Producer producer;

    @Column(name = "RANKING")
    private String ranking;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private WineTypeEnum type;

    @Column(name = "VINTAGE")
    private Integer vintage;

    /**
     * @return the appellation
     */
    public Appellation getAppellation() {
        return appellation;
    }

    /**
     * @return the color
     */
    public WineColorEnum getColor() {
        return color;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @return the producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @return the type
     */
    public WineTypeEnum getType() {
        return type;
    }

    /**
     * @return the vintage
     */
    public Integer getVintage() {
        return vintage;
    }

    /**
     * @param appellation
     *            the appellation to set
     */
    public void setAppellation(Appellation appellation) {
        this.appellation = appellation;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(WineColorEnum color) {
        this.color = color;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param photoUrl
     *            the photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * @param producer
     *            the producer to set
     */
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    /**
     * @param ranking
     *            the ranking to set
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(WineTypeEnum type) {
        this.type = type;
    }

    /**
     * @param vintage
     *            the vintage to set
     */
    public void setVintage(Integer vintage) {
        this.vintage = vintage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Wine wine = (Wine) other;
        return ObjectUtils.equals(getName(), wine.getName()) && ObjectUtils.equals(getType(), wine.getType()) && ObjectUtils.equals(getVintage(), wine.getVintage())
                && ObjectUtils.equals(getColor(), wine.getColor()) && ObjectUtils.equals(getAppellation(), wine.getAppellation()) && ObjectUtils.equals(getProducer(), wine.getProducer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getVintage(), getColor(), getType() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("description", description).append("photoUrl", photoUrl).append("producer", producer).append("ranking", ranking)
                .append("type", type).append("vintage", vintage).build();
    }
}

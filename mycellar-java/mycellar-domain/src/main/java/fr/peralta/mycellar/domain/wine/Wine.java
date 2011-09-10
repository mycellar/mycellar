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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;

/**
 * @author speralta
 */
@Entity
@Table(name = "WINE")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = true))
@SequenceGenerator(name = "WINE_ID_GENERATOR", allocationSize = 1)
public class Wine extends NamedEntity<Wine> {

    private static final long serialVersionUID = 201011121600L;

    @Valid
    @ManyToOne
    @JoinColumn(name = "APPELLATION")
    private Appellation appellation;

    @Column(name = "COLOR")
    @Enumerated(EnumType.ORDINAL)
    private WineColorEnum color;

    @ElementCollection
    @JoinTable(name = "WINE_VARIETAL", joinColumns = @JoinColumn(name = "WINE"))
    @Column(name = "PERCENT")
    @MapKeyJoinColumn(name = "VARIETAL")
    private final Map<Varietal, Integer> composition = new HashMap<Varietal, Integer>();

    @Column(name = "DESCRIPTION")
    private String description;

    @Id
    @GeneratedValue(generator = "WINE_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    @Valid
    @ManyToOne
    @JoinColumn(name = "PRODUCER")
    private Producer producer;

    @Column(name = "RANKING")
    private String ranking;

    @Column(name = "TYPE")
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
     * @return the composition
     */
    public Map<Varietal, Integer> getComposition() {
        return Collections.unmodifiableMap(composition);
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
    protected boolean dataEquals(Wine other) {
        return ObjectUtils.equals(getName(), other.getName())
                && ObjectUtils.equals(getType(), other.getType())
                && ObjectUtils.equals(getVintage(), other.getVintage())
                && ObjectUtils.equals(getColor(), other.getColor())
                && ObjectUtils.equals(getAppellation(), other.getAppellation())
                && ObjectUtils.equals(getProducer(), other.getProducer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getVintage(), getColor(), getType() };
    }
}

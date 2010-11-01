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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "WINE_ID_GENERATOR", allocationSize = 1)
public class Wine extends NamedEntity<Wine> {

    private static final long serialVersionUID = 201010311742L;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "COLOR")
    @Enumerated(EnumType.STRING)
    private WineColorEnum color;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private WineTypeEnum type;

    @Column(name = "RANKING")
    private String ranking;

    @Column(name = "VINTAGE")
    private int vintage;

    @Valid
    @ManyToOne
    @JoinColumn(name = "APPELLATION", nullable = false)
    private Appellation appellation;

    @Valid
    @ManyToOne
    @JoinColumn(name = "PRODUCER", nullable = false)
    private Producer producer;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    private final Map<Varietal, Integer> composition = new HashMap<Varietal, Integer>();

    @Id
    @GeneratedValue(generator = "PRODUCER_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param name
     * @param description
     * @param address
     * @param position
     * @param color
     * @param type
     * @param ranking
     * @param vintage
     * @param appellation
     * @param producer
     * @param photoUrl
     */
    public Wine(String name, String description, String address,
            String position, WineColorEnum color, WineTypeEnum type,
            String ranking, int vintage, Appellation appellation,
            Producer producer, String photoUrl) {
        super(name);
        this.description = description;
        this.address = address;
        this.position = position;
        this.color = color;
        this.type = type;
        this.ranking = ranking;
        this.vintage = vintage;
        this.appellation = appellation;
        this.producer = producer;
        this.photoUrl = photoUrl;
    }

    /**
     * Needed by hibernate.
     */
    Wine() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the color
     */
    public WineColorEnum getColor() {
        return color;
    }

    /**
     * @return the type
     */
    public WineTypeEnum getType() {
        return type;
    }

    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @return the vintage
     */
    public int getVintage() {
        return vintage;
    }

    /**
     * @return the appellation
     */
    public Appellation getAppellation() {
        return appellation;
    }

    /**
     * @return the producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @return the composition
     */
    public Map<Varietal, Integer> getComposition() {
        return Collections.unmodifiableMap(composition);
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
    protected boolean dataEquals(Wine other) {
        return ObjectUtils.equals(getName(), other.getName())
                && ObjectUtils.equals(getType(), other.getType())
                && ObjectUtils.equals(getVintage(), other.getVintage())
                && ObjectUtils.equals(getColor(), other.getColor())
                && ObjectUtils.equals(getAppellation(), other.getAppellation())
                && ObjectUtils.equals(getProducer(), other.getProducer());
    }
}

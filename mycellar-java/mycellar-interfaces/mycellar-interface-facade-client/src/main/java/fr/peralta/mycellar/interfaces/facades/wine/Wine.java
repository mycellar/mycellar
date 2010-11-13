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
package fr.peralta.mycellar.interfaces.facades.wine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bperalta
 * 
 */
public class Wine implements Serializable {
    private static final long serialVersionUID = 201011121600L;

    private String description;
    private WineColorEnum color;
    private WineTypeEnum type;
    private String ranking;
    private int vintage;
    private Appellation appellation;
    private Producer producer;
    private String photoUrl;
    private final Map<Varietal, Integer> composition = new HashMap<Varietal, Integer>();
    private Integer id;
    private String name;

    /**
     * Initialise par défaut les objets Appellation et Producer.
     */
    public Wine() {
        appellation = new Appellation();
        producer = new Producer();
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the color
     */
    public WineColorEnum getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(WineColorEnum color) {
        this.color = color;
    }

    /**
     * @return the type
     */
    public WineTypeEnum getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(WineTypeEnum type) {
        this.type = type;
    }

    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @param ranking
     *            the ranking to set
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    /**
     * @return the vintage
     */
    public int getVintage() {
        return vintage;
    }

    /**
     * @param vintage
     *            the vintage to set
     */
    public void setVintage(int vintage) {
        this.vintage = vintage;
    }

    /**
     * @return the appellation
     */
    public Appellation getAppellation() {
        return appellation;
    }

    /**
     * @param appellation
     *            the appellation to set
     */
    public void setAppellation(Appellation appellation) {
        this.appellation = appellation;
    }

    /**
     * @return the producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * @param producer
     *            the producer to set
     */
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    /**
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param photoUrl
     *            the photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the composition
     */
    public Map<Varietal, Integer> getComposition() {
        return composition;
    }
}

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

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.position.Map;
import fr.peralta.mycellar.domain.shared.NamedEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "APPELLATION", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME", "REGION" }))
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "APPELLATION_ID_GENERATOR", allocationSize = 1)
public class Appellation extends NamedEntity<Appellation> {

    private static final long serialVersionUID = 201011071628L;

    @Embedded
    private Map map;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "REGION", nullable = false)
    private Region region;

    @Id
    @GeneratedValue(generator = "APPELLATION_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param name
     * @param region
     * @param map
     * @param description
     */
    public Appellation(String name, Region region, Map map, String description) {
        super(name);
        this.region = region;
        this.map = map;
        this.description = description;
    }

    /**
     * Needed by hibernate.
     */
    Appellation() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the region
     */
    public Region getRegion() {
        return region;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Appellation other) {
        return ObjectUtils.equals(getName(), other.getName())
                && ObjectUtils.equals(getRegion(), other.getRegion());
    }

}

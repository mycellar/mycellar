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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;

/**
 * @author speralta
 */
@Entity
@Table(name = "COUNTRY")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false, unique = true))
@SequenceGenerator(name = "COUNTRY_ID_GENERATOR", allocationSize = 1)
public class Country extends NamedEntity<Country> {

    private static final long serialVersionUID = 201010311742L;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "MAP_URL")
    private String mapUrl;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "country")
    private final Set<Region> regions = new HashSet<Region>();

    @Id
    @GeneratedValue(generator = "COUNTRY_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param name
     * @param mapUrl
     * @param description
     */
    public Country(String name, String mapUrl, String description) {
        super(name);
        this.mapUrl = mapUrl;
        this.description = description;
    }

    /**
     * Needed by hibernate.
     */
    Country() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the mapUrl
     */
    public String getMapUrl() {
        return mapUrl;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the regions
     */
    public Set<Region> getRegions() {
        return Collections.unmodifiableSet(regions);
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
    protected boolean dataEquals(Country other) {
        return ObjectUtils.equals(getName(), other.getName());
    }

}

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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.NamedEntity;

/**
 * Varietal (EN) = Cépage (FR)
 * 
 * @author speralta
 */
@Entity
@Table(name = "VARIETAL")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false, unique = true))
@SequenceGenerator(name = "VARIETAL_ID_GENERATOR", allocationSize = 1)
public class Varietal extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FLESH")
    @Enumerated(EnumType.STRING)
    private VarietalColorEnum flesh;

    @Id
    @GeneratedValue(generator = "VARIETAL_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "SKIN")
    @Enumerated(EnumType.STRING)
    private VarietalColorEnum skin;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the flesh
     */
    public VarietalColorEnum getFlesh() {
        return flesh;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the skin
     */
    public VarietalColorEnum getSkin() {
        return skin;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param flesh
     *            the flesh to set
     */
    public void setFlesh(VarietalColorEnum flesh) {
        this.flesh = flesh;
    }

    /**
     * @param skin
     *            the skin to set
     */
    public void setSkin(VarietalColorEnum skin) {
        this.skin = skin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Varietal varietal = (Varietal) other;
        return ObjectUtils.equals(getName(), varietal.getName());
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
    protected ToStringBuilder toStringBuilder() {
        return super.toStringBuilder().append("description", description).append("flesh", flesh)
                .append("skin", skin);
    }

}

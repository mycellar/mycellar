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
package fr.peralta.mycellar.domain.stock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author bperalta
 * 
 */
@Entity
@Table(name = "CELLAR")
@SequenceGenerator(name = "CELLAR_ID_GENERATOR", allocationSize = 1)
public class Cellar extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "cellar")
    private final Set<Stock> stocks = new HashSet<Stock>();

    @Id
    @GeneratedValue(generator = "CELLAR_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "OWNER", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "cellar")
    private final Set<CellarShare> shares = new HashSet<CellarShare>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * @return the shares
     */
    public Set<CellarShare> getShares() {
        return Collections.unmodifiableSet(shares);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Cellar cellar = (Cellar) other;
        return ObjectUtils.equals(getName(), cellar.getName())
                && ObjectUtils.equals(getOwner(), cellar.getOwner());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getOwner() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ToStringBuilder toStringBuilder() {
        return super.toStringBuilder().append("owner", owner);
    }

}

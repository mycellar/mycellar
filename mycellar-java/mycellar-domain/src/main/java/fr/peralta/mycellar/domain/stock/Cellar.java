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

import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.user.User;

/**
 * @author bperalta
 * 
 */
@Entity
@Table(name = "CELLAR")
@SequenceGenerator(name = "CELLAR_ID_GENERATOR", allocationSize = 1)
public class Cellar extends NamedEntity<Cellar> {

    private static final long serialVersionUID = 201011111734L;

    @OneToMany(mappedBy = "cellar")
    private final Set<Stock> stocks = new HashSet<Stock>();

    @Id
    @GeneratedValue(generator = "CELLAR_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "OWNER", nullable = false)
    private User owner;

    /**
     * @return the stocks
     */
    public Set<Stock> getStocks() {
        return Collections.unmodifiableSet(stocks);
    }

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
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Cellar other) {
        return ObjectUtils.equals(getName(), other.getName())
                && ObjectUtils.equals(getOwner(), other.getOwner());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getOwner() };
    }

}

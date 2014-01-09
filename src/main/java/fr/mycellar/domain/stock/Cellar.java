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
package fr.mycellar.domain.stock;

import java.util.HashSet;
import java.util.Objects;
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
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;
import fr.mycellar.domain.user.User;

/**
 * @author bperalta
 */
@Entity
@Indexed
@Table(name = "CELLAR")
@SequenceGenerator(name = "CELLAR_ID_GENERATOR", allocationSize = 1)
public class Cellar extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @OneToMany(mappedBy = "cellar")
    @XmlTransient
    private final Set<Stock> stocks = new HashSet<Stock>();

    @Id
    @GeneratedValue(generator = "CELLAR_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "OWNER", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "cellar")
    @XmlTransient
    private final Set<CellarShare> shares = new HashSet<CellarShare>();

    @Override
    public Integer getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Cellar cellar = (Cellar) other;
        return Objects.equals(getName(), cellar.getName()) && Objects.equals(getOwner(), cellar.getOwner());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName(), getOwner() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("owner", owner).build();
    }

}

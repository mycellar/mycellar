/*
 * Copyright 2018, MyCellar
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
package fr.mycellar.domain.wine;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import fr.mycellar.domain.shared.AbstractAuditingEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "wine", uniqueConstraints = @UniqueConstraint(columnNames = { "appellation", "color", "type", "name",
		"vintage", "producer" }))
public class Wine extends AbstractAuditingEntity {

	private static final long serialVersionUID = 201804261330L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@Valid
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "APPELLATION", nullable = false)
	private Appellation appellation;

	@Column(name = "COLOR", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private WineColorEnum color;

	@Valid
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PRODUCER", nullable = false)
	private Producer producer;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private WineTypeEnum type;

	@Column(name = "VINTAGE", nullable = true)
	private Integer vintage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Appellation getAppellation() {
		return appellation;
	}

	public void setAppellation(Appellation appellation) {
		this.appellation = appellation;
	}

	public WineColorEnum getColor() {
		return color;
	}

	public void setColor(WineColorEnum color) {
		this.color = color;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public WineTypeEnum getType() {
		return type;
	}

	public void setType(WineTypeEnum type) {
		this.type = type;
	}

	public Integer getVintage() {
		return vintage;
	}

	public void setVintage(Integer vintage) {
		this.vintage = vintage;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Wine wine = (Wine) o;
		if (wine.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), wine.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Wine{id=" + getId() + ", name='" + getName() + ", producer='" + getProducer() + ", vintage='"
				+ getVintage() + "'}";
	}

}

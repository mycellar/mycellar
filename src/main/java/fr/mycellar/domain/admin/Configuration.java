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
package fr.mycellar.domain.admin;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author speralta
 */
@Entity
@Table(name = "CONFIGURATION")
@SequenceGenerator(name = "CONFIGURATION_ID_GENERATOR", allocationSize = 1)
public class Configuration extends IdentifiedEntity {

    private static final long serialVersionUID = 201211281628L;

    @Id
    @GeneratedValue(generator = "CONFIGURATION_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONFIG_KEY", unique = true, nullable = false)
    @Getter
    @Setter
    private ConfigurationKeyEnum key;

    @Column(name = "CONFIG_VALUE", length = 2000)
    @Getter
    @Setter
    private String value;

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Configuration configuration = (Configuration) other;
        return Objects.equals(getKey(), configuration.getKey());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getKey() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("key", key).append("value", value).build();
    }

}

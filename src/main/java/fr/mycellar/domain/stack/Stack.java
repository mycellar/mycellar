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
package fr.mycellar.domain.stack;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "STACK")
@SequenceGenerator(name = "STACK_ID_GENERATOR", allocationSize = 1)
public class Stack extends IdentifiedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Id
    @GeneratedValue(generator = "STACK_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    @Getter
    private Integer id;

    @Lob
    @Column(name = "STACK")
    @Getter
    private String stack;

    @Column(name = "STACK_COUNT")
    @Getter
    @Setter
    private long count = 1;

    @Column(name = "STACK_HASH", unique = true)
    @Getter
    @Setter
    private int hashCode;

    /**
     * Count + 1.
     */
    public void increaseCount() {
        count++;
    }

    public void setStack(String stack) {
        this.stack = stack;
        hashCode = stack.hashCode();
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Stack stack = (Stack) other;
        return Objects.equals(getStack(), stack.getStack());
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getStack() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("count", count).append("hashCode", hashCode).build();
    }

}

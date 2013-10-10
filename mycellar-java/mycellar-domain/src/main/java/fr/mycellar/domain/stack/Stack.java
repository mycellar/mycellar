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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
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
    private Integer id;

    @Lob
    @Column(name = "STACK")
    private String stack;

    @Column(name = "STACK_COUNT")
    private long count = 1;

    @Column(name = "STACK_HASH", unique = true)
    private int hashCode;

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * Count + 1.
     */
    public void increaseCount() {
        count++;
    }

    /**
     * @return the stack
     */
    public String getStack() {
        return stack;
    }

    /**
     * @param stack
     *            the stack to set
     */
    public void setStack(String stack) {
        this.stack = stack;
        hashCode = stack.hashCode();
    }

    /**
     * @return the hashCode
     */
    public int getHashCode() {
        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Stack stack = (Stack) other;
        return ObjectUtils.equals(getStack(), stack.getStack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getStack() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("count", count).append("hashCode", hashCode).build();
    }

}

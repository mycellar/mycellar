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

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
@Embeddable
public class Bottle {

    @ManyToOne
    @JoinColumn(name = "FORMAT", nullable = false)
    private Format format;

    @ManyToOne
    @JoinColumn(name = "WINE", nullable = false)
    private Wine wine;

    public Format getFormat() {
        return format;
    }

    public Wine getWine() {
        return wine;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((format == null) ? 0 : format.hashCode());
        result = (prime * result) + ((wine == null) ? 0 : wine.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Bottle other = (Bottle) obj;
        if (format == null) {
            if (other.format != null) {
                return false;
            }
        } else if (!format.equals(other.format)) {
            return false;
        }
        if (wine == null) {
            if (other.wine != null) {
                return false;
            }
        } else if (!wine.equals(other.wine)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("format", format).append("wine", wine).build();
    }

}

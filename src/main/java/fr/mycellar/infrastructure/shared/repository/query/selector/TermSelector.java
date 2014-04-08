/*
 * Copyright 2013, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.query.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.mycellar.infrastructure.shared.repository.query.Path;

public class TermSelector<FROM> implements SingleSelector<FROM, String, TermSelector<FROM>> {
    private static final long serialVersionUID = 201308010800L;
    private final Path<FROM, String> path;
    private List<String> selected = new ArrayList<>();
    private boolean orMode = true;
    private Integer searchSimilarity = 2;

    public TermSelector(SingularAttribute<? super FROM, String> attribute) {
        path = new Path<FROM, String>(attribute);
    }

    public TermSelector(Path<FROM, String> path) {
        this.path = path;
    }

    private TermSelector(TermSelector<FROM> toCopy) {
        path = toCopy.path;
        selected = new ArrayList<>(toCopy.selected);
        orMode = toCopy.orMode;
        searchSimilarity = toCopy.searchSimilarity;
    }

    @Override
    public TermSelector<FROM> copy() {
        return new TermSelector<FROM>(this);
    }

    public SingularAttribute<?, ?> getAttribute() {
        return path != null ? (SingularAttribute<?, ?>) path.getAttributes().get(0) : null;
    }

    public Integer getSearchSimilarity() {
        return searchSimilarity;
    }

    public void setSearchSimilarity(Integer searchSimilarity) {
        this.searchSimilarity = searchSimilarity;
    }

    public boolean isOrMode() {
        return orMode;
    }

    public void setOrMode(boolean orMode) {
        this.orMode = orMode;
    }

    public TermSelector<FROM> or() {
        setOrMode(true);
        return this;
    }

    public TermSelector<FROM> and() {
        setOrMode(false);
        return this;
    }

    /**
     * Get the possible candidates for property.
     */
    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = Arrays.asList(selected);
    }

    /**
     * Set the possible candidates for property.
     */
    public void setSelected(List<String> selected) {
        this.selected = selected;
    }

    public TermSelector<FROM> selected(String... selected) {
        setSelected(Arrays.asList(selected));
        return this;
    }

    public boolean isNotEmpty() {
        if ((selected == null) || selected.isEmpty()) {
            return false;
        }
        for (String word : selected) {
            if (StringUtils.isNotBlank(word)) {
                return true;
            }
        }
        return false;
    }

    public void clearSelected() {
        if (selected != null) {
            selected.clear();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
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

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.SearchMode;

/**
 * Used to construct OR predicate for a property value. In other words you can
 * search all entities E having a given property set to one of the selected
 * values.
 */
public class PropertySelector<E, F> implements SingleSelector<E, F, PropertySelector<E, F>> {

    private static final long serialVersionUID = 201308010800L;

    private final Path<E, F> path;
    private List<F> selected = new ArrayList<>();
    private SearchMode searchMode = SearchMode.ANYWHERE;
    private Boolean notIncludingNull;
    private boolean orMode = true;
    private boolean caseSensitive = true;
    private boolean notMode = false;

    @SafeVarargs
    public PropertySelector(Path<E, F> path, F... values) {
        this.path = path;
        selected = new ArrayList<>(Arrays.asList(values));
    }

    private PropertySelector(PropertySelector<E, F> toCopy) {
        path = toCopy.path;
        selected = new ArrayList<>(toCopy.selected);
        searchMode = toCopy.searchMode;
        notIncludingNull = toCopy.notIncludingNull;
        orMode = toCopy.orMode;
        caseSensitive = toCopy.caseSensitive;
        notMode = toCopy.notMode;
    }

    @Override
    public PropertySelector<E, F> copy() {
        return new PropertySelector<E, F>(this);
    }

    public PropertySelector(String path, Class<E> from) {
        this.path = new Path<E, F>(path, from);
    }

    public List<Attribute<?, ?>> getAttributes() {
        return path.getAttributes();
    }

    public boolean isNotIncludingNullSet() {
        return notIncludingNull != null;
    }

    public Boolean isNotIncludingNull() {
        return notIncludingNull;
    }

    public PropertySelector<E, F> withoutNull() {
        this.notIncludingNull = true;
        return this;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public PropertySelector<E, F> caseSensitive() {
        this.caseSensitive = true;
        return this;
    }

    public PropertySelector<E, F> caseInsensitive() {
        this.caseSensitive = false;
        return this;
    }

    /**
     * Get the possible candidates for property.
     */
    public List<F> getSelected() {
        return selected;
    }

    public PropertySelector<E, F> add(F object) {
        this.selected.add(object);
        return this;
    }

    /**
     * Set the possible candidates for property.
     */
    public void setSelected(List<F> selected) {
        this.selected = selected;
    }

    @SuppressWarnings("unchecked")
    public PropertySelector<E, F> selected(F... selected) {
        setSelected(Arrays.asList(selected));
        return this;
    }

    public boolean isNotEmpty() {
        return (selected != null) && !selected.isEmpty();
    }

    public void clearSelected() {
        if (selected != null) {
            selected.clear();
        }
    }

    public void setValue(F value) {
        this.selected = Arrays.asList(value);
    }

    public F getValue() {
        return isNotEmpty() ? selected.get(0) : null;
    }

    public SearchMode getSearchMode() {
        return searchMode;
    }

    /**
     * In case, the field's type is a String, you can set a searchMode to use.
     * It is null by default.
     */
    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = searchMode;
    }

    public PropertySelector<E, F> searchMode(SearchMode searchMode) {
        setSearchMode(searchMode);
        return this;
    }

    public boolean isOrMode() {
        return orMode;
    }

    public void setOrMode(boolean orMode) {
        this.orMode = orMode;
    }

    public PropertySelector<E, F> orMode(boolean orMode) {
        setOrMode(orMode);
        return this;
    }

    public boolean isNotMode() {
        return notMode;
    }

    public void setNotMode(boolean notMode) {
        this.notMode = notMode;
    }

    public PropertySelector<E, F> notMode(boolean notMode) {
        setNotMode(notMode);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
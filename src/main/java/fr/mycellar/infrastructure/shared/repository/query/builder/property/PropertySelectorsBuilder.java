/*
 * Copyright 2014, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.query.builder.property;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelectors;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.AbstractBuilder;

/**
 * @author speralta
 */
public class PropertySelectorsBuilder<FROM> extends AbstractBuilder<FROM> {

    private final PropertySelectors<FROM> propertySelectors;

    public PropertySelectorsBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        propertySelectors = new PropertySelectors<>();
    }

    public PropertySelectorsBuilder(SearchBuilder<FROM> searchParameters, PropertySelectors<FROM> propertySelectors) {
        super(searchParameters);
        this.propertySelectors = new PropertySelectors<FROM>(propertySelectors);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(Path<FROM, TO> path) {
        return new PropertySelectorBuilder<FROM, FROM, TO>(this, path);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(SingularAttribute<? super FROM, TO> attribute) {
        return new PropertySelectorBuilder<>(this, attribute);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new PropertySelectorBuilder<>(this, attribute);
    }

    public <TO> PropertySelectorsBuilder<FROM> property(PropertySelector<FROM, TO> propertySelector) {
        propertySelectors.property(propertySelector);
        return this;
    }

    public DisjunctionPropertySelectorsBuilder<FROM, SearchBuilder<FROM>> disjunction() {
        DisjunctionPropertySelectorsBuilder<FROM, SearchBuilder<FROM>> disjunction = new DisjunctionPropertySelectorsBuilder<FROM, SearchBuilder<FROM>>(toSearchParameters(), toSearchParameters());
        propertySelectors.add(disjunction.getPropertySelectors().or());
        return disjunction;
    }

    public PropertySelectors<FROM> getPropertySelectors() {
        return propertySelectors;
    }

    <TO> SearchBuilder<FROM> add(Path<FROM, TO> path, @SuppressWarnings("unchecked") TO... values) {
        propertySelectors.property(new PropertySelector<>(path, values));
        return toSearchParameters();
    }
}
